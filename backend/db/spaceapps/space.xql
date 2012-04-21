xquery version "3.0";

(:~
: Simple hack-up for combining UHA API for satelites with
: The Met Office DataPoint API for Weather
:
: @date 2012-04-21
: @author Adam Retter <adam.retter@googlemail.com>
:)

import module namespace datetime = "http://exist-db.org/xquery/datetime";
import module namespace http = "http://expath.org/ns/http-client";
import module namespace request = "http://exist-db.org/xquery/request";

declare namespace met = "http://met-office";
declare namespace uhaapi = "http://uhaapi";
declare namespace space = "http://spaceapps";

declare variable $met:api-key := "43d21b62-90a1-435f-8c56-534e204a3b74";
declare variable $space:spaceapps-collection := "/db/spaceapps";

declare function uhaapi:get-satelite($usspacecom-id, $lat, $lng) {
    let $url := concat("http://api.uhaapi.com/satellites/", $usspacecom-id, "/passes?lat=", $lat, "&amp;lng=", $lng) return
        http:send-request(
            <http:request method="get" href="{$url}">
                <http:header name="Accept" value="application/xml"/>
            </http:request>
        )[2]
};

declare function met:get-weather($api-key, $lat, $lng) {
    let $url := concat("http://partner.metoffice.gov.uk/public/val/wxfcs/all/xml/nearestlatlon?res=3hourly&amp;lat=", $lat, "&amp;lon=", $lng, "&amp;key=", $api-key) return
        http:send-request(
            <http:request method="get" href="{$url}">
                <http:header name="Accept" value="application/xml"/>
            </http:request>
        )[2]
};

declare function space:minutes-past-midnight($dateTime as xs:dateTime?) {
    
    let $midnight-time := "00:00:00Z" cast as xs:time return
    let $midnight := dateTime(datetime:date-from-dateTime($dateTime), $midnight-time) return
    
       (($dateTime - $midnight) div xs:dayTimeDuration("PT1M")) cast as xs:integer
};

declare function space:simplify-weather-for-event($met-weather, $start as xs:dateTime) {

    let $start-minutes-past-midnight := space:minutes-past-midnight($start) return

    let $date := datetime:date-from-dateTime($start) return
        let $before := $met-weather//Period[xs:date(@val) eq $date]/Rep[xs:integer(text()) le $start-minutes-past-midnight] return
            let $weather-at-start := $before[count($before)] return
                <weather>
                    <simple>
                        { doc(concat($space:spaceapps-collection, "/met/simpleWeatherTypes.xml"))//simpleWeatherType[metOfficeWeatherTypeCode = $weather-at-start/@W]/description }
                    </simple>
                    <metOffice>
                        { doc(concat($space:spaceapps-collection, "/met/weatherTypes.xml"))//weatherType[code eq $weather-at-start/@W]/Description }
                        <chanceOfRain unit="%">{ string($weather-at-start/@Pp) }</chanceOfRain>
                        <temperature unit="C">{ string($weather-at-start/@T) }</temperature>
                        <feelsLikeTemperature unit="C">{ string($weather-at-start/@F) }</feelsLikeTemperature>
                    </metOffice>
                </weather>
};


let $lat := request:get-parameter("lat", "50.7218"),
$lng := request:get-parameter("lng", "-3.5336"),
$format := request:get-parameter("format", "xml"),

$null := util:declare-option("exist:serialize", concat("method=", $format)) return

    let $sattelites := 
        <satelites>
            <satelite id="25544">International Space Station</satelite>
            <satelite id="20580">Hubble Space Telescope</satelite>
        </satelites>
    return
    
        let $met-weather := met:get-weather($met:api-key, $lat, $lng),
        $sattelites-xml :=
            for $sattelite-id in $sattelites/satelite/@id return
                uhaapi:get-satelite($sattelite-id, $lat, $lng)
        return
            <events>
                <location continent="{lower-case($met-weather//Location/@continent)}" country="{lower-case($met-weather//Location/@country)}" name="{lower-case($met-weather//Location/@name)}" lon="{$met-weather//Location/@lon}" lat="{$met-weather//Location/@lat}"/>
                {
                    for $pass in $sattelites-xml//pass
                    order by xs:dateTime($pass/start/time) return
                        <event>
                            <title>{$sattelites/satelite[@id eq $pass/parent::satellite_passes/@id]/text()}</title>
                            {
                                $pass/start,
                                $pass/end,
                                space:simplify-weather-for-event($met-weather, xs:dateTime($pass/start/time))
                            }
                        </event>
                }
            </events>