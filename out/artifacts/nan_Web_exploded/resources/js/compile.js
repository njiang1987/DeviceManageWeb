$(document).ready(function() {

        //$('select').material_select();

    $("#compiling").click(function () {
        //在提交数据之后,回调刷新firstleft内的数据
        //alert($("#project").val());

        $.post("/compile", {"compiling": true}, function (data) {

            //图表
            //console.log(data);
            //alert($("#project").val());
            //查询
            //function loadDrugs() {
            //    $("body").html(data);
            //
            //}
            //
            ////载入图表
            //loadDrugs();
            window.location.reload();

        });
    });


});