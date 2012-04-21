/**
 * Created with IntelliJ IDEA.
 * User: angela.relle
 * Date: 21/04/12
 * Time: 19:52
 * To change this template use File | Settings | File Templates.
 */

var predictTheSkysUrl = "http://www.adamretter.org.uk/spaceapps/space.xql?lat=latitude&lng=longitude&format=json";

function getNextEventAndWeather(latitude, longitude) {
    var urlForThisLocation = predictTheSkysUrl.replace("latitude", latitude).replace("longitude", longitude);
    $.ajax({
        url: urlForThisLocation,
        type: 'GET',
        success: function (data, textStatus, jqXHR) {
            var obj = jQuery.parseJSON(jqXHR.responseText);
            alert("data returned");
        },
        error: function (data, textStatus, jqXHR) {
            alert("Problem returning data") }
    });

    $.getJSON(urlForThisLocation, function(result) {
        alert("Got event data back");
    } )
}
