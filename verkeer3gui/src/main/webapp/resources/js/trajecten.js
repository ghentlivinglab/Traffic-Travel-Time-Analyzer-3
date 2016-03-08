var trajecten = {};
var map = L.map('map').setView([51.106596, 3.740759],11);
L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
}).addTo(map);

function getTraject(id)
{
    $.getJSON( "json/trajecten/"+id, function( data ) {
        $.each( data, function( key, val ) {
            if(!trajecten[val["traject"]["id"]])
                trajecten[val["traject"]["id"]]=[];
            trajecten[val["traject"]["id"]].push({latitude: val["latitude"], longitude: val["longitude"]});
    })});

}

$("a.view-traject-onmap").click(function () {
    id =  $(this).data("id");
    console.log("Clicked on view traject on map with id"+id);
    getTraject(id);
});