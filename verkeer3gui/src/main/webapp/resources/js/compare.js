/**
 * Created by Jan on 3/05/2016.
 */
//Global props
var selected_traject_id;
var selected_traject;
var chart;
var x = new Date();
var offset = x.getTimezoneOffset();
console.log("Offset "+offset);
//Datetimepicker
$(function () {
    //Begindatum 1 week terug
    var begindatum = new Date();
    var einddatum = new Date();
    //First date
    begindatum.setDate(begindatum.getDate()-14);
    einddatum.setDate(einddatum.getDate()-1);
    $('#startdate-1').datetimepicker({
        format: "YYYY-MM-DD HH:mm",
        date: begindatum
    });
    $('#enddate-1').datetimepicker({
        format: "YYYY-MM-DD HH:mm",
        date: einddatum
    });
    //Second date
    $('#startdate-2').datetimepicker({
        format: "YYYY-MM-DD HH:mm",
        date: einddatum
    });
    $('#enddate-2').datetimepicker({
        format: "YYYY-MM-DD HH:mm",
        date: new Date()
    });
});

$(document).ready(function(){
    $("#container1").highcharts({
        global: {
            timezoneOffset: offset
        },
        chart: {
            zoomType: 'x',
            type: 'line'
        },
        tooltip: {
            formatter: function() {
                // If you want to see what is available in the formatter, you can
                // examine the `this` variable.
                //     console.log(this);

                return '<p><b>'+new Date(this.x).toString()+"</b> : "+ Math.round(this.y/60,2) +' min</p>';
            }
        },
        title: {
            text: 'Reistijden eerste interval'
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
            enabled: true,
            itemWidth:150,
            width: 750
        },
        plotOptions: {
            series: {
                connectNulls: true
            }
        },
        series : []
    });

    //Second chart
    $("#container2").highcharts({
        global: {
            timezoneOffset: offset
        },
        chart: {
            zoomType: 'x',
            type: 'line'
        },
        tooltip: {
            formatter: function() {
                // If you want to see what is available in the formatter, you can
                // examine the `this` variable.
                //     console.log(this);

                return '<p><b>'+new Date(this.x).toString()+"</b> : "+ Math.round(this.y/60,2) +' min</p>';
            }
        },
        title: {
            text: 'Reistijden tweede interval'
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
            enabled: true,
            itemWidth:150,
            width: 750
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
    chart = $("#container1").highcharts();
    var seriesLength = chart.series.length;
    for(var i = seriesLength -1; i > -1; i--) {
        chart.series[i].remove();
    }
    chart.redraw(true);
    chart = $("#container2").highcharts();
    seriesLength = chart.series.length;
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
        getTraveltimesContainer1(selected_traject_id);
        getTraveltimesContainer2(selected_traject_id);
    }
});

function updateDropdownChoice() {
    $("p#geselecteerd-traject").text(selected_traject);
    $("p#geselecteerd-traject").data("trajectid",selected_traject_id);
}


//Helper function which makes the JSON call
function getTraveltimesContainer1(selected_traject_id) {
    toggleLoader();
    chart = $("#container1").highcharts();
    var startdatum = $("#startdate-1 input[type='text']").val();
    var einddatum = $("#enddate-1 input[type='text']").val();
    //console.log("start = "+startdatum);
    //console.log("eind = "+einddatum);

    $.getJSON( "json/metingen/"+selected_traject_id+"/"+startdatum+"/"+einddatum, function( data ) {
        $.each( data, function( key, val ) {
            addMeting(val["provider"]["naam"],val["timestamp"],val["reistijd"], 1);
        });
        chart.redraw();
        toggleLoader();
    });
}
function getTraveltimesContainer2(selected_traject_id) {
    toggleLoader();
    chart = $("#container1").highcharts();
    var startdatum = $("#startdate-2 input[type='text']").val();
    var einddatum = $("#enddate-2 input[type='text']").val();
    //console.log("start = "+startdatum);
    //console.log("eind = "+einddatum);

    $.getJSON( "json/metingen/"+selected_traject_id+"/"+startdatum+"/"+einddatum, function( data ) {
        $.each( data, function( key, val ) {
            addMeting(val["provider"]["naam"],val["timestamp"],val["reistijd"], 2);
        });
        chart.redraw();
        toggleLoader();
    });
}

function addMeting(provider, datetime, traveltime, containerid)
{
    chart = $("#container" + containerid).highcharts();
    var formatted_date = Date.UTC(
        datetime["date"]["year"],
        datetime["date"]["month"]-1,
        datetime["date"]["day"],
        datetime["time"]["hour"],
        datetime["time"]["minute"],
        datetime["time"]["second"],
        0
    );//args: year, month, day, hours, minutes, seconds, milliseconds
    var provider_trajectid = provider;
    if(chart.get(provider_trajectid)){
        chart.get(provider_trajectid).addPoint([formatted_date, traveltime],false);
    }
    else
    {
        chart.addSeries({
            id: provider_trajectid,
            name: provider,
            data: [[formatted_date, traveltime]]
        },false,false);
    }
}

function toggleLoader()
{
    var status = $(".cs-loader").css('display');
    if(status=="none")
    {
        $(".cs-loader").css('display','block');
    }
    else
    {
        $(".cs-loader").css('display','none');
    }
}