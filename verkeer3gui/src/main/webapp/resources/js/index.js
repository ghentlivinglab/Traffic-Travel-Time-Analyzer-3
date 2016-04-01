/**
 * Created by jelle on 23.02.16.
 */

$( window ).load(function() {
    $("#vertragingen td span").each(function(d) {
        var reistijd = parseInt($(this).data("reistijd"));
        var optreistijd = parseInt($(this).data("optimale-reistijd"));
        if (optreistijd < 0) {
            $(this).html("<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span> Geen metingen");
            $(this).css("background-color", "#fcf8e3");
            $(this).css("color", "#8a6d3b");
            $(this).css("border", "1px solid #faebcc");
        } else if (reistijd == 0){
            $(this).html("<span class=\"glyphicon glyphicon-warning-sign\" aria-hidden=\"true\"></span> Laatste meting is leeg");
            $(this).css("background-color", "#fcf8e3");
            $(this).css("color", "#8a6d3b");
            $(this).css("border", "1px solid #faebcc");
        }else{
            var diff = reistijd - optreistijd;
            var diffMin = Math.floor(diff / 60);
            var diffSec = diff - (diffMin * 60);
            var textVal = "";
            if(optreistijd < 0)
                textVal = "<span class=\"glyphicon glyphicon-warning-sign\" aria-hidden=\"true\"></span> ";
            if (diff < 0) {
                $(this).css("background-color", "#00DD00");
                textVal = "+ 0m";
            } else {
                textVal += "+";
                textVal += " " + Math.abs(diffMin) + "m";
                if(diffSec > 0)
                    textVal += " " + diffSec + "s"

                var hardBound = 180; //5 minuten
                var softBound = 20; //20 seconden

                if (diff >= hardBound) {
                    $(this).css("background-color", "#FF0000");
                } else if (diff <= softBound) {
                    $(this).css("background-color", "#00DD00");
                } else {
                    var hbDiff = hardBound - diff;
                    hbDiff /= (hardBound - softBound);

                    var green = Math.min(hbDiff * 2, 1) * 255;
                    var red = Math.min((1 - hbDiff) * 2, 1) * 255;

                    $(this).css("background-color", "rgb(" + Math.round(red) + "," + Math.round(green) + ",0)");
                    if (hbDiff < 0.9 && hbDiff > 0.3)
                        $(this).css("color", "#333");
                }
            }
            $(this).html(textVal);
        }
    })
});