/**
 * Created by jelle on 23.02.16.
 */

$( window ).load(function() {
    $("#vertragingen td span").each(function(d){
        var reistijd = parseInt($(this).attr("d-reistijd"));
        var optreistijd = parseInt($(this).attr("d-optimale-reistijd"));
        if(reistijd == "null")
            $(this).html("LEEG");
        else{
            var diff = reistijd - optreistijd;
            var diffMin = Math.floor(diff/60);
            var diffSec = diff - (diffMin * 60);
            var textVal = "";
            if(diff < 0){
                textVal += "-";
                diffMin += 1;
            }
            else
                textVal += "+";
            textVal += " " + Math.abs(diffMin) + "m" + " " + diffSec + "s";
            $(this).html(textVal);

            var hardBound = 180; //5 minuten
            var softBound = 20; //20 seconden

            if(diff >= hardBound) {
                $(this).css("background-color", "#FF0000");
            }else if(diff <= softBound) {
                $(this).css("background-color", "#00DD00");
            }else{
                var hbDiff = hardBound - diff;
                hbDiff /= (hardBound - softBound);

                var green = Math.min(hbDiff*2, 1) * 255;
                var red = Math.min((1 - hbDiff)*2, 1) * 255;

                $(this).css("background-color", "rgb(" + Math.round(red) + "," + Math.round(green) + ",0)");
                if(hbDiff < 0.8 && hbDiff > 0.3)
                    $(this).css("color", "#333");
            }

        }
    })
});