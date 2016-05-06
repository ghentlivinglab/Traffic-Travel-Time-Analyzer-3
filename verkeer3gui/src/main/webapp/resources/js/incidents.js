$( document ).ready(function() {
    jQuery('#startTime').datetimepicker({format: 'd/m/Y H:m'});
    jQuery('#endTime').datetimepicker({format: 'd/m/Y H:m'});

    $("#incidents-table").floatThead();

});