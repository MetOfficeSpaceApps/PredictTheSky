/**
 * Created with IntelliJ IDEA.
 * User: angela.relle
 * Date: 21/04/12
 * Time: 19:52
 * To change this template use File | Settings | File Templates.
 */

var predictTheSkyUrl = "http://www.adamretter.org.uk/spaceapps/space.xql?lat=latitude&lng=longitude&format=json&jsonp=?&nextClear=true";

function getNextEventAndWeather(latitude, longitude) {
    var urlForThisLocation = predictTheSkyUrl.replace("latitude", latitude).replace("longitude", longitude);
    $.ajax({
        url: urlForThisLocation,
        dataType: "json",
        type: 'GET',
        success: function (data, textStatus, jqXHR) {
            var obj = jQuery.parseJSON(jqXHR.responseText);
            var eventTitle = data.event.title;
            var eventStartTime = new Date(Date.parse(data.event.start.time));
            var eventEndTime = new Date(Date.parse(data.event.end.time));
            var eventDateString = eventStartTime.toDateString();
            var eventStartString = eventStartTime.toTimeString();
            var eventEndString =   eventEndTime.toTimeString()
            $("#nextEventDiv").html("Next event: " + eventTitle + " - " + eventDateString
                + " from " + eventStartString + " to " + eventEndString);

        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.statusText);
            alert(thrownError);
        }
     });
}
