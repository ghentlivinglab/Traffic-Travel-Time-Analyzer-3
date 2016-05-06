$(document).ready(function(){
    //Global props
    var selected_traject_id_1;
    var selected_traject_1;
    var selected_traject_id_2;
    var selected_traject_2;
    var chart;
    var x = new Date();
    var offset = x.getTimezoneOffset();
    console.log("Offset "+offset);

    //Datetimepicker
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

    $("#container").highcharts({
        global: {
            timezoneOffset: offset
        },
        chart: {
            zoomType: 'x',
            type: 'spline'
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


    //Dropdown
    function clearChart() {
        chart = $("#container").highcharts();
        var seriesLength = chart.series.length;
        for(var i = seriesLength -1; i > -1; i--) {
            chart.series[i].remove();
        }
        chart.redraw(true);
    }

    $("#traject-dropdown1").change(function()
    {
        selected_traject_id_1 = $(this).val();
        selected_traject_1 = $(this).text();


        //Change chart
        if(selected_traject_1 && selected_traject_id_1)
        {
            clearChart();
            getTraveltimes(selected_traject_id_1, 1);
            if(selected_traject_2 && selected_traject_id_2)
            {
                getTraveltimes(selected_traject_id_2, 2);
                chart = $("#container").highcharts();
                chart.legend.legendWitdh = (chart.series.length / 2) * 150;
            }
        }
    });

    $("#traject-dropdown2").change(function()
    {
        selected_traject_id_2 =  $(this).val();
        selected_traject_2 = $(this).text();


        //Change chart
        if(selected_traject_2 && selected_traject_id_2)
        {
            clearChart();
            getTraveltimes(selected_traject_id_2, 2);
            if(selected_traject_1 && selected_traject_id_1)
            {
                getTraveltimes(selected_traject_id_1, 1);
                chart = $("#container").highcharts();
                chart.legend.legendWitdh = (chart.series.length / 2) * 150;
            }
        }
    });


    //Helper function which makes the JSON call
    function getTraveltimes(selected_traject_id, route_id) {
        toggleLoader();
        chart = $("#container").highcharts();
        var startdatum = $("#startdate input[type='text']").val();
        var einddatum = $("#enddate input[type='text']").val();
        //console.log("start = "+startdatum);
        //console.log("eind = "+einddatum);

        $.getJSON( "json/metingen/"+selected_traject_id+"/"+startdatum+"/"+einddatum, function( data ) {
            $.each( data, function( key, val ) {
                addMeting(val["provider"]["naam"],val["timestamp"],val["reistijd"], route_id);
            });
            chart.redraw();
            toggleLoader();
        });
    }

    function addMeting(provider, datetime, traveltime, route_id)
    {
        chart = $("#container").highcharts();
        var formatted_date = new Date(
            datetime["date"]["year"],
            datetime["date"]["month"]-1,
            datetime["date"]["day"],
            datetime["time"]["hour"],
            datetime["time"]["minute"],
            datetime["time"]["second"],
            0
        ).getTime();//args: year, month, day, hours, minutes, seconds, milliseconds
        var provider_trajectid = provider + "-" + route_id;
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
});
