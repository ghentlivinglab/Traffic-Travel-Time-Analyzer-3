var start_latitude,start_longitude,end_latitude,end_longitude;
var ideale_reistijd,afstand;
var route;
var map = L.map('map').setView([51.106596, 3.740759],11);
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
    addRoute();
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

var changedWaypoints = function (result) {
    console.log("Waypoints changed!")
    console.log(result);
};

function addRoute()
{
    route = L.Routing.control({
        waypoints: [
            L.latLng(start_latitude,start_longitude),
            L.latLng(end_latitude,end_longitude)
        ],
        routeLine: function(route) {
            var line = L.Routing.line(route, {
                addWaypoints: false,
                extendToWaypoints: false,
                routeWhileDragging: true,
                autoRoute: true,
                useZoomParameter: false,
                draggableWaypoints: true
            });
            start_latitude = route.waypoints[0].latLng.lat;
            start_longitude = route.waypoints[0].latLng.lng;
            end_latitude = route.waypoints[1].latLng.lat;
            end_longitude = route.waypoints[1].latLng.lng;
            wijzigFormulier();
            return line;
        },
        routeWhileDragging: true,
        fitSelectedRoutes: 'smart',
        show:false
    }).addTo(map);
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