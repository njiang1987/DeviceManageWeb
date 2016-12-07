
var pageSize = "10";//每页行数
var currentPage = "1";//当前页
var totalPage = "0";//总页数
$(document).ready(function() {
    queryForPages();
    function queryForPages(){
    $.ajax({
        type: "get",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        url: "/onlineList",
        success: function callbackFun(result) {
            var info = eval("(" + result + ")");
            clearDate();
            fillTable(info);

        }
        });
    }

    $("#onlinepackaging").click(function () {
        $.post("/androidOnline", {"onlinepackaging": true}, function (data) {
            setTimeout("window.location.reload()",500);
        });
    });


    function fillTable(info){
        totalPage = Math.ceil((info.length)/pageSize);
        listCount="";

        $("#apklist").html('');
        if(info.length <= pageSize){
            listCount = info.length;
        }else{
            listCount =pageSize;
        }
            var apklist="";
            var apklist1="";
            var apklist2="";
            var apklist3="";
        for(var i=0 ; i<listCount;i++) {
            var j = i  + (currentPage - 1) * pageSize;
            if (j < info.length) {
                apklist1 = "<tr>"
                    + "<td data-title='序号' >" + (j+1) + "</td>"
                    + "<td data-title='构建号'>" + info[j].job + "</td>"
                    + "<td data-title='打包时间'>" + info[j].createtime + "</td>"
                    + "<td data-title='构建状态'>" + info[j].jobstatus + "</td>"
                    + "<td data-title='包名'><a href=\"" + info[j].apklink + "\">" + info[j].apk + "</a></td>";

                apklist3 = "<td data-title='日志'><a href=\"" + info[j].log + "\" target=\"_blank\">查看日志</a></td>"
                    + "</tr>";

                if( info[j].erweima=="") {
                    apklist2  = "<td data-title='二维码' style=\"height:61px; \"></td>";

                }else{
                    apklist2  = "<td data-title='二维码' style=\"height:61px; \"><div> <a href=\"" + info[j].erweima + "\" target=\"_blank\" onclick=\"window.open(this.href,'','width=250,height=250,top='+(screen.height-400)/2+',left='+(screen.width-400)/2 + '');return false\"><img id=\"img\" style=\"width:25px;height:25px; \" alt =\"\" src=\"" + info[j].erweima + "\" /></a> </div> </td>";
                }
            }
            apklist += apklist1+apklist2+apklist3;
            $("#apklist").html(apklist);
        }

    }

    //清空数据
    function clearDate(){
        $("#apklist").html("");
    }
    //首页
    $("#firstPage").click(function(){
        currentPage="1";
        queryForPages();
    });
//上一页
    $("#previous").click(function(){
        if(currentPage>1){
            currentPage-- ;
        }
        queryForPages();
    });
//下一页
    $("#next").click(function(){
        if(currentPage<totalPage){
            currentPage++ ;
        }
        queryForPages();
    });
//尾页
    $("#last").click(function(){
        currentPage = totalPage;
        queryForPages();
    });

});