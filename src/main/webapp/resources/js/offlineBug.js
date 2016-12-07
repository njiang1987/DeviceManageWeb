var pageSize = "10";//每页行数 
var currentPage = "1";//当前页 
var totalPage = "0";//总页数 
var rowCount = "0";//总条数 
var params="";//参数 
var url="activity_list.action";//action 
$(document).ready(function(){//jquery代码随着document加载完毕而加载 
//分页查询 
    function queryForPages(){
        $.ajax({
            url:url,
            type:'post',
            dataType:'json',
            data:"qo.currentPage="+currentPage+"&qo.pageSize="+pageSize+params,
            success:function callbackFun(data){
//解析json 
                var info = eval("("+data+")");
//清空数据 
                clearDate();
                fillTable(info);
            }
        });
    }
//填充数据 
    function fillTable(info){
        if(info.length>1){
            totalPage = info[info.length-1].totalPage;
            var tbody_content="";//不初始化字符串"",会默认"undefined" 
            for(var i=0 ; i<info.length-1;i++){
                tbody_content +="<tr>"
                    +"<td data-title='序号' >"+(i+1+(currentPage-1)*pageSize)+"</td>"
                    +"<td data-title='标题'>"+info[i].title.substr(0,20)+"</td>"
                    +"<td data-title='地点'>"+info[i].address.substr(0,6)+"</td>"
                    +"<td data-title='已报名'>"+info[i].quota_sign+"人</td>"
                    +"<td data-title='类别'>"+info[i].type+"</td>"
                    +"<td data-title='操作'><a href='<%=request.getContextPath()%>/activity_edit.action?id="+info[i].id+"'>编辑</a></td>"
                    +"</tr>"
                $("#t_body").html(tbody_content);
            }
        }else{
            $("#t_head").html("");
            $("#t_body").html("<div style='height: 200px;width: 700px;padding-top: 100px;' align='center'>"+info.msg+"</div>");
        }
    }

//清空数据 
    function clearDate(){
        $("#t_body").html("");
    }

//搜索活动 
    $("#searchActivity").click(function(){ queryForPages(); });
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