//Global props
var selected_traject_id;
var selected_traject;
var chart = c3.generate({
    bindto: '#chart',
    data: {
        columns: [
        ]
    }
});

//Dropdown
$("#dropdown-select-traject ul li.traject-dropdown-item").click(function()
{
    selected_traject_id = $(this).data("trajectid");
    selected_traject = $(this).text();

    updateDropdownChoice();

    //Change chart
    if(selected_traject && selected_traject_id)
    {
        chartdata.series = [];
        chartdata.labels = [];
        getTraveltimes(selected_traject_id);
    }
});


function updateDropdownChoice() {
    $("p#geselecteerd-traject").text(selected_traject);
    $("p#geselecteerd-traject").data("trajectid",selected_traject_id);
}


//Helper function which makes the JSON call
function getTraveltimes(selected_traject_id) {
    console.log("Getting traveltimes of "+selected_traject_id);
    $.getJSON( "json/metingen/"+selected_traject_id, function( data ) {
        $.each( data, function( key, val ) {
            $.each( data, function( key, val ) {
                addPoint(val["provider"]["naam"],val["timestamp"],val["reistijd"]);
            });
        });
    });
}

function addPoint(provider, datetime, traveltime)
{
    var id;
    var formatted_date = new Date(
        datetime["date"]["year"],
        datetime["date"]["month"],
        datetime["date"]["day"],
        datetime["time"]["hour"],
        datetime["time"]["minute"],
        datetime["time"]["second"],
        0
    );                                  //args: year, month, day, hours, minutes, seconds, milliseconds
    id=isInChart(provider);
    if(id>=0)
    {
        chart.data.columns[i].push({x: formatted_date, y: traveltime});
    }
    else
    {
        chartdata.series.push({
            name: provider,
            data: [
                {x: formatted_date, y: traveltime}
            ]
        })
    }
}

function isInChart(provider)
{
    var rc=-1;
    $.each(chart.data.columns, function(key,val)
    {
        if(val[0]===provider) {
            rc = key;
            return;
        }
    });
    return rc;
}