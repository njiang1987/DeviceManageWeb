$(document).ready(function() {

        //$('select').material_select();

    $("#indexBussnissBug").click(function () {
        //在提交数据之后,回调刷新firstleft内的数据
        //alert($("#project").val());

        $.post("/mass", {"startDate": $("#startdate").val(), "endDate": $("#enddate").val(),"project":$("#project").val() }, function (data) {

            //图表
            //console.log(data);
            //alert($("#project").val());
            //查询
            function loadDrugs() {
                $("body").html(data);

            }

            //载入图表
            loadDrugs();

        });
    });

    //$('.datepicker').pickadate({
    //    selectMonths: true,
    //    selectYears: 15
    //});

});