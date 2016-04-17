/**
 * Created by Eigenaar on 17/04/2016.
 */
$(window).load(function(){
    var type = "state";
    var waitingForStatus = false;
    var currentServerStatus

    var toggleSwitch = $("[name='chkServerToggle']");
    toggleSwitch.bootstrapSwitch(type, "false");
    toggleSwitch.bootstrapSwitch('disabled',false)
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
});