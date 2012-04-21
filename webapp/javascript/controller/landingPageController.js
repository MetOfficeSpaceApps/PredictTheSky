/**
 * @author: angela.relle
 */

$(document).ready(function(){
    $("#findEventsButton").click(function () {
           getNextEventAndWeather(latitude, longitude);
    });
    $("#findEventsButton").hide();
    getLocation(latitude, longitude);


});
