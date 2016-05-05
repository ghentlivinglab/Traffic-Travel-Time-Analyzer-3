$( document ).ready(function() {

    var traject = {};
    var wpts = {};
    var opts = {
        //TODO: Aanpassen van de opties per traject/
        color: 'blue',
        weight: 10,
        opacity: 1
    };
    var map = L.map('map').setView([51.106596, 3.740759], 11);
    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
    }).addTo(map);

    $("#trajecten-table").floatThead();

    function addPointToArray(id, latitude, longitude) {
        if (wpts[id] == undefined) {
            wpts[id] = [[parseFloat(latitude), parseFloat(longitude)]];
        }
        else {
            wpts[id].push([parseFloat(latitude), parseFloat(longitude)]);
        }
    }

    $('body').on('click', '.view-traject-onmap', function () {
        id = $(this).data("id");
        removeAllTrajects();
        getTraject(id);
    });

    function removeAllTrajects() {
        wpts = {};
        trajects = {};
    }

    function addTraject(id, traj){
        traject = traj;
        $.each(traj['traject']['waypoints'], function (key, val) {
            addPointToArray(id, val["latitude"], val["longitude"]);
        });
    }

    function getTraject(id) {
        $("#mapModal .modal-title").html($("#traject-" + id + " td .view-traject-onmap").html());
        $.getJSON("json/trajectenattributes/" + id, function (data) {
            addTraject(id, data);
            updateLeaflet();
        });
    }


    function updateLeaflet() {
        clearMap();

        var popupHtml = "<h2 class='tooltip-title'><b>Lengte:</b> "+traject['traject']['lengte']+"m</h2>" +
            "<hr>" +
            "<table id='maptable'>" +
            "<thead><tr><th>Reistijd</th><th>Optimaal</th><th>Huidig</th></tr></thead><tbody>";

        $.each(traject.providers, function(key, val){
            popupHtml += "<tr><td>"+val['naam']+"</td>";
            //Optimale Reistijden
            var optimale_reistijd = null;
            $.each(traject.traject.providerOptimaleReistijden, function(providerId, _optimale_reistijd){
                if(providerId == val['id']){
                    optimale_reistijd = _optimale_reistijd;
                    return;
                }
            });
            popupHtml += "<td>";
            if(optimale_reistijd != null) {
                if(optimale_reistijd >= 60)
                    popupHtml += Math.floor(optimale_reistijd / 60) + 'm';
                if(optimale_reistijd %60 != 0)
                    popupHtml += " " + optimale_reistijd % 60 + 's';
            }else
                popupHtml += '-';

            popupHtml += "</td>";

            //Vertragingen
            var vertraging = null;
            $.each(traject.huidigeVertragingen, function(providerId, _vertraging){
                if(providerId == val['id']){
                    vertraging = _vertraging;
                    return;
                }
            });

            popupHtml += "<td>";
            if(optimale_reistijd != null && vertraging != null){
                if(optimale_reistijd + vertraging >= 60)
                    popupHtml += Math.floor((optimale_reistijd + vertraging) / 60) + 'm';
                if((optimale_reistijd + vertraging) %60 != 0)
                    popupHtml += " " + (optimale_reistijd + vertraging) % 60 + 's';
            }else
                popupHtml += '-';

            popupHtml += "</td>";
            popupHtml += "</tr>";
        });

        popupHtml += "</tbody></table>";


        var popup = new L.popup({className: "trajectenPopup"}).setContent(popupHtml);
        $.each(wpts, function (key, value) {
            var polyline = new L.polyline(value, opts);
            polyline.bindPopup(popup);
            polyline.on('mouseover',function(e){
                this.openPopup();
            });
            polyline.on('mouseout',function(e){
                this.closePopup();
            });
            polyline.addTo(map);
        });
    }

    function clearMap() {
        for (i in map._layers) {
            if (map._layers[i]._path != undefined) {
                try {
                    map.removeLayer(map._layers[i]);
                }
                catch (e) {
                    console.log("problem with " + e + m._layers[i]);
                }
            }
        }
    }

    if($(".current-traject").length)
        $('html, body').animate({
            scrollTop: $(".current-traject").offset().top - 300
        }, 500);

});