/**
 * Created with IntelliJ IDEA.
 * User: angela.relle
 * Date: 21/04/12
 * Time: 15:19
  */

var latitude;
var longitude;
var currentPosition;

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(locateSuccess, locateFail);
    } else {
        alert("Geolocation not supported");
    }
}

function locateSuccess(position) {
    currentPosition = position;
    latitude = position.coords.latitude;
    longitude = position.coords.longitude;
    // Now try to get information about where we're to
    onLocationSet(latitude, longitude);
}

function findLocation(locationSearchString) {
    var locationSearchUrl = "http://nominatim.openstreetmap.org/search?format=json&q=searchString&countrycodes=gb";
    var urlForThisSearchString = locationSearchUrl.replace("searchString", locationSearchString);
    $.getJSON(urlForThisSearchString, function(result) {
        // TO-DO - this just accepts the first hit at the moment, would be better to present the list of matches to
        // the user
        latitude = result[0].lat;
        longitude = result[0].lon;
        $("#citySearchDiv").hide();
        $("#locationDiv").show();
        onLocationSet(latitude, longitude);

    });

}

function getStreetAddress(latitude, longitude) {
    var reverseGeocodeUrl = "http://nominatim.openstreetmap.org/reverse?format=json&lat=latitude&lon=longitude&zoom=18&addressdetails=1"
    var urlForThisPosition = reverseGeocodeUrl.replace("latitude", latitude).replace("longitude", longitude);
    $.getJSON(urlForThisPosition, function(result) {
        var city = result.address.city;
        var locationMessage = "I don't know where you are";
        if (city && city != "") {
                locationMessage = city;
        } else {
            var country = result.address.country;
            if (country && country != "") {
                locationMessage = country;
            }
        }
        $("#cityLocationDiv").html(locationMessage);
    });
}

function locateFail(geoPositionError) {

    var defaultMessage = ", defaulting to Exeter"

    switch (geoPositionError.code) {
        case 0: // UNKNOWN_ERROR
            alert('An unknown error occurred, sorry' + defaultMessage);
            break;
        case 1: // PERMISSION_DENIED
            alert('Permission to use Geolocation was denied' + defaultMessage );
            break;
        case 2: // POSITION_UNAVAILABLE
            alert('Couldn\'t find you...' + defaultMessage);
            break;
        case 3: // TIMEOUT
            alert('The Geolocation request took too long and timed out' + defaultMessage);
            break;
        default:

    }
    latitude = 50.727;
    longitude = -3.4749;
    onLocationSet(latitude, longitude);
}

function onLocationSet(latitude, longitude) {
    getStreetAddress(latitude, longitude)
    $("#nextEventDiv").html("Getting next event information...")
    getNextEventAndWeather(latitude, longitude);
}

