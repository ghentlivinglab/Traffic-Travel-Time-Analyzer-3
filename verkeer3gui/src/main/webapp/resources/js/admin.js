/**
 * Created by Eigenaar on 17/04/2016.
 */
$(window).load(function(){
    var type = "state";
    var waitingForStatus = false;
    var currentServerStatus;

    // --------------- Start/stop the scrape-server ---------------

    var toggleSwitch = $("#chkServerToggle");
    toggleSwitch.bootstrapSwitch(type, "false");
    toggleSwitch.bootstrapSwitch('disabled',false);

    getServerStatus();

    function getServerStatus(){
        if(!waitingForStatus) {
            waitingForStatus = true;
            $.getJSON("json/admin/status", function (data) {
                toggleSwitch.bootstrapSwitch(type, data);
                currentServerStatus = data;
            }).always(function(){
                toggleSwitch.bootstrapSwitch('disabled', false)
                waitingForStatus = false;
                setTimeout(getServerStatus, 5000);
            });
        }
    }

    toggleSwitch.on('switchChange.bootstrapSwitch', function(event, state) {
        if(!waitingForStatus) {
            waitingForStatus = true;
            toggleSwitch.bootstrapSwitch('disabled',true)
            $.post("json/admin/status", {start: !currentServerStatus}, function (data) {
                if(data == "true")
                    currentServerStatus = !currentServerStatus;
            }).always(function() {
                waitingForStatus = false
            });
        }
    });

    // --------------- Update optimal travel times ---------------

    var disableSwitch = $("#chkDisable");

    // disable()-functionaliteit (want ik heb een haat voor $.prop(...))
    jQuery.fn.extend({
        disable: function() {
            return this.each(function() {
                this.disabled = true;
            });
        }, enable: function() {
            return this.each(function() {
                this.disabled = false;
            });
        }
    });

    $("#btnOptimalScrape").click(function(){
        if(confirm("Bent u zeker? Dit zal wijzigingen maken in de databank!")) {
            $("#btnOptimalScrape").disable();
            $.getJSON("json/admin/updateoptimaltraveltimes", {replace: $("#chkDisable").is(':checked')}, function () {
                adminAlert("Gelukt:", "de taak is toegevoegd en wordt zo snel mogelijk uitgevoerd", "success");
            }).error(function () {
                adminAlert("Mislukt:", "kon de server niet bereiken!", "danger");
            }).always(function () {
                $("#btnOptimalScrape").enable();
            });
        }
    });


    // --------------- Get waypoints from Coyote ---------------

    $("#btnGetTrajects").click(function() {
        $("#btnGetTrajects").disable();
        $.getJSON("json/admin/gettrajects", function () {
            adminAlert("Gelukt:", "de taak is toegevoegd en wordt zo snel mogelijk uitgevoerd", "success");
        }).error(function () {
            adminAlert("Mislukt:", "kon de server niet bereiken!", "danger");
        }).always(function () {
            $("#btnGetTrajects").enable();
        });
    });


    // Supporting function that displays a temporary alert //

    function adminAlert(title, text, type){
        var scraperAlert = $("#scraperAlert");
        scraperAlert.html("<strong>" + title + "</strong> " + text);
        scraperAlert.addClass("alert-" + type);
        scraperAlert.animate({
            top: 150
        }, 400, function(){
            setTimeout(function(){
                scraperAlert.animate({
                    top: -60
                }, 400, function(){
                    scraperAlert.removeClass("alert-" + type);
                });
            }, 4000);
        });
    }


});