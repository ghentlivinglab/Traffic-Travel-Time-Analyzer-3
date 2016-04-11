//Global props
var selected_traject_id;
var selected_traject;
var chart;

//Datetimepicker
$(function () {
    //Begindatum 1 week terug
    var begindatum = new Date();
    begindatum.setDate(begindatum.getDate()-7);
    $('#startdate').datetimepicker({
        format: "YYYY-MM-DD HH:mm",
        date: begindatum
    });
    $('#enddate').datetimepicker({
        format: "YYYY-MM-DD HH:mm",
        date: new Date()
    });
});

$(document).ready(function(){
     $("#container").highcharts({
            chart: {
                zoomType: 'x',
                type: 'line'
            },
            tooltip: {
                formatter: function() {
                    // If you want to see what is available in the formatter, you can
                    // examine the `this` variable.
                    //     console.log(this);
                    var d = new Date(0); // The 0 there is the key, which sets the date to the epoch
                    d.setUTCSeconds(this.x);
                    return '<p>'+ Math.floor(this.y/60,2) +' min ' + this.y % 60 + ' s<br>'+ d.toLocaleString("dd/MM/yyyy HH:mm") + '</p>';
                }
             },
            title: {
                text: 'Reistijden historiek'
            },
            subtitle: {
                text: document.ontouchstart === undefined ?
                    'Klik en sleep om een meer gedetaileerde weergave van een periode' : 'Zoom voor meer gedetaileerde weergave van een periode'
            },
            xAxis: {
                type: 'datetime'
            },
            yAxis: {
                title: {
                    text: 'Reistijd in minuten'
                },
                labels: {
                    formatter: function(){return parseInt(this.value/60);}
                }

            },
            legend: {
                enabled: true
            },
             plotOptions: {
                 series: {
                         connectNulls: true
                 }
             },
            series : []
    });

});

//Dropdown
function clearChart() {
    chart = $("#container").highcharts();
    var seriesLength = chart.series.length;
    for(var i = seriesLength -1; i > -1; i--) {
        chart.series[i].remove();
    }
    chart.redraw(true);
}

$("#dropdown-select-traject ul li.traject-dropdown-item").click(function()
{
    selected_traject_id = $(this).data("trajectid");
    selected_traject = $(this).text();

    updateDropdownChoice();

    //Change chart
    if(selected_traject && selected_traject_id)
    {
        clearChart();
        getTraveltimes(selected_traject_id);
    }
});


function updateDropdownChoice() {
    $("p#geselecteerd-traject").text(selected_traject);
    $("p#geselecteerd-traject").data("trajectid",selected_traject_id);
}


//Helper function which makes the JSON call
function getTraveltimes(selected_traject_id) {
    chart = $("#container").highcharts();
    var startdatum = $("#startdate input[type='text']").val();
    var einddatum = $("#enddate input[type='text']").val();
    //console.log("start = "+startdatum);
    //console.log("eind = "+einddatum);

    $.getJSON( "json/metingen/"+selected_traject_id+"/"+startdatum+"/"+einddatum, function( data ) {
        $.each( data, function( key, val ) {
            addMeting(val["provider"]["naam"],val["timestamp"],val["reistijd"]);
        });
        chart.redraw();
    });
}

function addMeting(provider, datetime, traveltime)
{
    chart = $("#container").highcharts();
    var formatted_date = new Date(
        datetime["date"]["year"],
        datetime["date"]["month"],
        datetime["date"]["day"],
        datetime["time"]["hour"],
        datetime["time"]["minute"],
        datetime["time"]["second"],
        0
    );//args: year, month, day, hours, minutes, seconds, milliseconds

    if(chart.get(provider)){
        chart.get(provider).addPoint([formatted_date.getTime(), traveltime],false);
    }
    else
    {
        chart.addSeries({
            id: provider,
            name: provider,
            data: [[formatted_date.getTime(), traveltime]]
        },false,false);
    }
}

