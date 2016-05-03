$( document ).ready(function() {
    jQuery('#startTime').datetimepicker({format: 'd/m/Y H:m'});
    jQuery('#endTime').datetimepicker({format: 'd/m/Y H:m'});

    var trajecten = {};
    var wpts = {};
    var opts = {
        //TODO: Aanpassen van de opties per traject
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
    }

    function removeTraject(id) {
        delete wpts[id];
        updateLeaflet();
    }

    function getTraject(id) {
        $("#mapModal .modal-title").html($("#traject-" + id + " td .view-traject-onmap").html());
        $.getJSON("json/trajecten/" + id, function (data) {
            $.each(data, function (key, val) {
                addPointToArray(id, val["latitude"], val["longitude"]);
            });
            console.dir(wpts);
            updateLeaflet();
        });
    }


    function updateLeaflet() {
        clearMap();
        $.each(wpts, function (key, value) {
            trajecten[key] = new L.polyline(value, opts).addTo(map);
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