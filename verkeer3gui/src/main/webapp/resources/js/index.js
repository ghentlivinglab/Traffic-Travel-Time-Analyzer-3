var polylines = {};
//Gemiddelde vertraging tot 1 dag terug
var oneDayAgo = moment().subtract(1,'days');
var now = moment();

var map = L.map('map').setView([51.106596, 3.740759],11);
L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png',{
    attribution: '&copy; OpenStreetMap contributors'
}).addTo(map);

function addPointToArray(id, latitude, longitude) {
    if(traj_wpts[id]==undefined)
    {
        traj_wpts[id]=[[parseFloat(latitude),parseFloat(longitude)]];
    }
    else{
        traj_wpts[id].push([parseFloat(latitude),parseFloat(longitude)]);
    }
}

var drawPolyline = function (val) {
    var traj_id = val['traject']['id'];
    var wpts = [];
    $.each(val['traject']['waypoints'],function(key,waypoint){
        wpts.push([parseFloat(waypoint['latitude']),parseFloat(waypoint['longitude'])]);
    });
    var avg_vertraging =  val['avg_vertraging'];
    var optimale_reistijd = val['traject']['optimale_reistijd'];
    var line_color = (avg_vertraging-120<optimale_reistijd?'orange':(avg_vertraging>optimale_reistijd?"red":"green"));
    var opts = {
        color:line_color,
        weight:10,
        opacity:1
    };
    polylines[traj_id] = L.polyline(wpts,opts);
    polylines[traj_id].bindPopup("<h1 class='tooltip-title'>"+val['traject']['naam']+"</h1>" +
        "<ul>" +
        "<li><b>Vertraging: </b>" + parseInt(val['avg_vertraging']/60)+" minuten "+parseInt(val['avg_vertraging']-60*parseInt(val['avg_vertraging']/60))+" seconden</li>" +
        "</ul>");
    polylines[traj_id].on('mouseover',function(e){
       this.openPopup();
    });
    polylines[traj_id].on('mouseout',function(e){
        this.closePopup();
    });
    polylines[traj_id].addTo(map);
};

$(document).ready(function(){
    $.getJSON( "json/vertragingen/"+moment(oneDayAgo).format("YYYY-MM-DD HH:mm")+"/"+moment(now).format("YYYY-MM-DD HH:mm"), function( data ) {
        $.each( data, function( key, val ) {
            drawPolyline(val);
        });
    });
});

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