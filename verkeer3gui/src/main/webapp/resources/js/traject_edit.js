var start_latitude,start_longitude,end_latitude,end_longitude;
var ideale_reistijd,afstand, id;
var route;
var map = L.map('map').setView([51.106596, 3.740759],11);
var resultArray;
L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png',{
    attribution: '&copy; OpenStreetMap contributors'
}).addTo(map);



$(document).ready(function() {
    //Load initial routing
    laadRoute();
});

function laadRoute()
{
    getCoordinatesFromForm();
    getWaypoints();
}

function getCoordinatesFromForm()
{
    start_latitude = $("#edit-traject input:text[id=start_latitude]").val();
    start_longitude = $("#edit-traject input:text[id=start_longitude]").val();
    end_latitude = $("#edit-traject input:text[id=end_latitude]").val();
    end_longitude = $("#edit-traject input:text[id=end_longitude]").val();
    afstand = $("#edit-traject input:text[id=lengte]").val();
    ideale_reistijd = $("#edit-traject input:text[id=ideale_reistijd]").val();


    //console.log("From form: start_latitude="+start_latitude+",start_longitude="+start_longitude+",end_latitude="+end_latitude+",end_longitude="+end_longitude);
}

function getWaypoints(){
    id = $("#id").val();
    $.get("waypoints",
        function(data){
            var waypoints = JSON.parse(data);
            addRoute(convertWaypoints(waypoints));
        }
    )
}

function convertWaypoints(waypoints){
    resultArray = [];
    resultArray[0] = new L.Routing.Waypoint(L.latLng(start_latitude,start_longitude),0);
    for(var i = 0;i<waypoints.length;i++)
    {
        resultArray[i+1]= new L.Routing.Waypoint(L.latLng(waypoints[i].latitude,waypoints[i].longitude),i+1);
    }
    resultArray[waypoints.length+1] =new L.Routing.Waypoint(L.latLng(end_latitude,end_longitude),waypoints.length+1);
    return resultArray;
}

var changedWaypoints = function (result) {
    console.log("Waypoints changed!");
    console.log(result);
};

function addRoute(wegpunten)
{
    route = L.Routing.control({
        waypoints: wegpunten,
        routeLine: function(route) {
            var line = L.Routing.line(route, {
                addWaypoints: true,
                extendToWaypoints: false,
                routeWhileDragging: true,
                autoRoute: true,
                useZoomParameter: false,
                draggableWaypoints: true
            });
            start_latitude = route.waypoints[0].latLng.lat;
            start_longitude = route.waypoints[0].latLng.lng;
            end_latitude = route.waypoints[route.waypoints.length-1].latLng.lat;
            end_longitude = route.waypoints[route.waypoints.length-1].latLng.lng;
            wijzigFormulier();
            return line;
        },
        routeWhileDragging: true,
        fitSelectedRoutes: 'smart',
        show:false
    }).addTo(map);

    route.getPlan().on('waypointschanged', function(e) {
        var plan= route.getPlan();
        resultArray =  plan.getWaypoints();
        updateWaypointsNamen(resultArray);
    });
}

function wijzigFormulier()
{
    afstand = parseInt(route._routes[0].summary.totalDistance);
    ideale_reistijd = parseInt(route._routes[0].summary.totalTime);

    $("#edit-traject input:text[id=start_latitude]").val(start_latitude);
    $("#edit-traject input:text[id=start_longitude]").val(start_longitude);
    $("#edit-traject input:text[id=end_latitude]").val(end_latitude);
    $("#edit-traject input:text[id=end_longitude]").val(end_longitude);
    $("#edit-traject input[id=optimale_reistijd]").val(ideale_reistijd);
    $("#edit-traject input[id=lengte]").val(afstand);
}

function deleteWaypoint(id){
    alert(resultArray.length);
    resultArray.splice(id,1);
    alert(resultArray.length);
    var plan = route.getPlan();
    updateWaypointsNamen(resultArray);
    plan.setWaypoints(resultArray);
}

function updateWaypointsNamen(array){
    for(var i = 0;i<array.length;i++)
    {
        array[i].name = i+"";
    }
    start_latitude = array[0].latitude;
    start_longitude = array[0].longitude;
    end_latitude = array[array.length-1].latitude;
    end_longitude = array[array.length-1].longitude;
    wijzigFormulier();
}

function formSubmit(){
    var backendWaypoints = new Array(resultArray.length-2);
    //i start bij 1 om startlat uit waypoints te houden en length -2 om endlat uit waypoints te houden(en zero based offset)
    for(var i = 1; i<=backendWaypoints.length;i++){
        var obj={};
        obj.volgnummer = i;
        obj.latitude = resultArray[i].latLng.lat;
        obj.longitude = resultArray[i].latLng.lng;
        backendWaypoints[i-1] = obj;
    }
    $("#edit-traject input[id=wayPoints]").val(JSON.stringify(backendWaypoints));
    $( "#edit-traject" ).submit();
}
