$( document ).ready(function() {

    var trajecten = {};
    var wpts = {};
    var opts = {
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

    function getTraject(id) {
        $.getJSON("json/trajecten/" + id, function (data) {
            $.each(data, function (key, val) {
                addPointToArray(id, val["latitude"], val["longitude"]);
            });
            updateLeaflet();
        });
    }

    $('body').on('click', '.view-traject-onmap', function () {
        id = $(this).data("id");
        $(this).parent().parent().addClass("map-showing");
        $(this).parent().parent().removeClass("map-not-showing");
        $(this).attr("class", "delete-traject-onmap");
        getTraject(id);
    });

    $('body').on('click', '.delete-traject-onmap', function () {
        id = $(this).data("id");
        $(this).parent().parent().removeClass("map-showing");
        $(this).parent().parent().addClass("map-not-showing");
        $(this).attr("class", "view-traject-onmap");
        removeTraject(id);
    });
    function removeTraject(id) {
        delete wpts[id];
        updateLeaflet();
    }

    function updateLeaflet() {
        clearMap();
        $.each(wpts, function (key, val) {
            trajecten[key] = new L.polyline(val, opts).addTo(map);
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

    /*
     var tableOffset = $("#trajecten-table").offset().top;
     var $header = $("#trajecten-table > thead").clone();
     var $fixedHeader = $("#header-fixed").append($header);

     $(window).bind("scroll", function() {
     var offset = $(this).scrollTop();

     if (offset >= tableOffset && $fixedHeader.is(":hidden")) {
     $('#header-fixed thead tr').width($('#trajecten-table thead tr').width());
     $('#trajecten-table thead tr').children('th').each(function(i){
     var width = $(this).width();
     var padding = parseInt($(this).css("padding-left")) + parseInt($(this).css("padding-right"));
     var child = $("#header-fixed thead tr").children('th').eq(i);
     child.width(width);
     });

     $('#trajecten-table thead tr').children('th').each(function(i) {
     var width = $(this).width();
     var padding = parseInt($(this).css("padding-left")) + parseInt($(this).css("padding-right"));
     var child = $("#header-fixed thead tr").children('th').eq(i);
     while (child.width() + padding > $(this).width()){
     width -= 5;
     child.width(width - padding);
     }
     });

     $fixedHeader.show();
     }
     else if (offset < tableOffset) {
     $fixedHeader.hide();
     }
     });*/
});