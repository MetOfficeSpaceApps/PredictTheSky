/**
 * Created with IntelliJ IDEA.
 * User: angela.relle
 * Date: 21/04/12
 * Time: 19:52
 * To change this template use File | Settings | File Templates.
 */

var predictTheSkyUrl = "http://www.adamretter.org.uk/spaceapps/space.xql?lat=latitude&lng=longitude&format=json&jsonp=?";

function getNextEventAndWeather(latitude, longitude) {
    var urlForThisLocation = predictTheSkyUrl.replace("latitude", latitude).replace("longitude", longitude);
    $.ajax({
        url: urlForThisLocation,
        dataType: "json",
        type: 'GET',
        success: function (data, textStatus, jqXHR) {
            var obj = jQuery.parseJSON(jqXHR.responseText);
            alert("data returned");
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.statusText);
            alert(thrownError);
        }
     });
}
