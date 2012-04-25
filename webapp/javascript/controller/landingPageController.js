/**
 * @author: angela.relle
 */

$(document).ready(function(){
    $("#citySearchDiv").hide();
    getLocation(latitude, longitude);
    $("#changeLocationButton").click(function() {
        $("#locationDiv").hide();
        $("#citySearchDiv").show();
    });

    $("#citySearchInput").keypress(function() {
        if ($("#citySearchInput").val() != "") {
            $("#citySearchButton").removeAttr("disabled");
        } else {
            $("#citySearchButton").attr("disabled", "disabled");
        }

    });

    $("#citySearchButton").click(function() {
        findLocation($("#citySearchInput").val());
    });
});

