var trajecten = {};
var wpts = {};
var opts = {
    color: 'blue',
    weight: 10,
    opacity: 1
};
var map = L.map('map').setView([51.106596, 3.740759],11);
L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png',{
    attribution: '&copy; OpenStreetMap contributors'
}).addTo(map);

function addPointToArray(id, latitude, longitude) {
    if(wpts[id]==undefined)
    {
       wpts[id]=[[parseFloat(latitude),parseFloat(longitude)]];
    }
    else{
        wpts[id].push([parseFloat(latitude),parseFloat(longitude)]);
    }
}
function getTraject(id)
{
    $.getJSON( "json/trajecten/"+id, function( data ) {
        $.each( data, function( key, val ) {
            addPointToArray(id,val["latitude"],val["longitude"]);
        });
        updateLeaflet();
    });
}

$('body').on('click','#view-traject-onmap',function () {
    id =  $(this).data("id");
    $(this).removeClass("btn-success");
    $(this).addClass("btn-danger");
    $(this).attr("id","delete-traject-onmap");
    $(this).html("Verberg");
    getTraject(id);
});

$('body').on('click','#delete-traject-onmap',function () {
    id =  $(this).data("id");
    $(this).removeClass("btn-danger");
    $(this).addClass("btn-success");
    $(this).attr("id","view-traject-onmap");
    $(this).html("Toon");
    removeTraject(id);
});
function removeTraject(id)
{
    delete wpts[id];
    updateLeaflet();
}
function updateLeaflet()
{
    clearMap();
    $.each(wpts, function(key,val){
        trajecten[key] = new L.polyline(val,opts).addTo(map);
    });
}

function clearMap() {
    for(i in map._layers) {
        if(map._layers[i]._path != undefined) {
            try {
                map.removeLayer(map._layers[i]);
            }
            catch(e) {
                console.log("problem with " + e + m._layers[i]);
            }
        }
    }
}