var items = [];
var map = L.map('map').setView([51.106596, 3.740759],11);
L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
}).addTo(map);
$.getJSON( "json/trajecten", function( data ) {
    $.each( data, function( key, val ) {
        var control = L.Routing.control({
            waypoints:[
                L.latLng(val.start_latitude,val.start_longitude),
                L.latLng(val.end_latitude,val.end_longitude)
            ],
            routeLine: function(route) {
                var line = L.Routing.line(route, {
                    addWaypoints: false,
                    extendToWaypoints: false,
                    routeWhileDragging: false,
                    autoRoute: true,
                    useZoomParameter: false,
                    draggableWaypoints: false,
                    addWaypoints: false
                });
                line.on('click',function(e) {
                    console.log(e);
                });
                console.log(line);
                return line;
            },
            routeWhileDragging: false,
            autoRoute: true,
            useZoomParameter: false,
            draggableWaypoints: false,
            show:false,
            addWaypoints:false
        }).addTo(map);
    });
});


function toonTraject(traject)
{
    console.log("click!");
    var html_traject = "<ul>" +
                        "<li>"+traject.letter+"</li>"+
                        "<li>"+traject.naam+"</li>"+
                        "</ul>";
    $("#map-comments").html(html_traject);

}

