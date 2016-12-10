
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
            url: "/machinelist",
            success: function callbackFun(result) {
                var info = result;
                clearDate();
                fillTable(info);

            },
            error : function callbackFun(result){
            	console.log(result);
            }
        });
    }


    function fillTable(info){
        totalPage = Math.ceil((info.length)/pageSize);
        listCount="";

        $("#machinelist").html('');
        if(info.length <= pageSize){
            listCount = info.length;
        }else if((info.length-(currentPage - 1) * pageSize)>=10) {
            listCount = pageSize;
        }else{
            listCount =info.length-(currentPage - 1) * pageSize;
        }

        var content="";
        for(var i=0 ; i<listCount;i++) {
            var j = i  + (currentPage - 1) * pageSize;
            if (j < info.length) {
                content += "<tr>"
                    + "<td data-title='序号' >" + (j+1) + "</td>"
                    + "<td data-title='创建时间'>" + info[j].createtime + "</td>"
                    + "<td data-title='平台'>" + info[j].plantform + "</td>"
                    + "<td data-title='编号'>" + info[j].imei + "</td>"
                    + "<td data-title='手机名称'>" + info[j].tel_name + "</td>"
                    + "<td data-title='手机版本'>" + info[j].tel_version + "</td>"
                    + "<td data-title='借用人'>" + info[j].borrow_name + "</td>"
                    + "<td data-title='借用时间'>"+ info[j].modify + "</td>"
                    + "<td data-title='操作'><a href=\"editMachine?edit=" + info[j].id + "\" >编辑</a>&nbsp&nbsp<a href=\"machineModifyInfo?mid=" + info[j].id + "\" >记录</a></td></tr>";

            }
        }
        $("#machinelist").html(content);

    }

    //清空数据
    function clearDate(){
        $("#machinelist").html("");
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