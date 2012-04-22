/**
 * Created with IntelliJ IDEA.
 * User: angela.relle
 * Date: 21/04/12
 * Time: 15:19
 * To change this template use File | Settings | File Templates.
 */

var latitude;
var longitude;

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(locateSuccess, locateFail);
    } else {
        alert("Geolocation not supported");
    }
}

function locateSuccess(position) {
    latitude = position.coords.latitude;
    longitude = position.coords.longitude;
    // Now try to get information about where we're to
    getStreetAddress(latitude, longitude);
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
        $("#findEventsButton").show();
        $("#locationDiv").html(locationMessage);
    });
}

function locateFail(geoPositionError) {
    switch (geoPositionError.code) {
        case 0: // UNKNOWN_ERROR
            alert('An unknown error occurred, sorry');
            break;
        case 1: // PERMISSION_DENIED
            alert('Permission to use Geolocation was denied');
            break;
        case 2: // POSITION_UNAVAILABLE
            alert('Couldn\'t find you...');
            break;
        case 3: // TIMEOUT
            alert('The Geolocation request took too long and timed out');
            break;
        default:

    }
    latitude = 50.727;
    longitude = -3.4749;
    getStreetAddress(latitude, longitude);
}

