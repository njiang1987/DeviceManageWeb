<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/resources/css/index.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="http://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/machine.js"></script>	
<title>手机归属列表</title>
</head>
<body>
    <main>
  <div class="row">
    <div id="index-banner" class="index-banner section no-pad-bot primary-bg">
      <div class="container">
        <h1 class=" center-on-small-only  text-light ">测试机管理</h1>
        <input type="button" value="添加机器" onClick="javascript:window.open('add','','scrollbars=yes,width=600,height=500')" >
        <br>
      </div>
    </div>

    <div class="row">
      <div class="col s12">
        <table class="bordered">
          <thead id="t_head">
          <tr>
            <th>序号</th> 
            <th>平台</th>
            <th>编号</th>
            <th>手机名称</th>
            <th>手机版本</th>
            <th>借用人</th>
            <th>借用时间</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody id="machinelist">
          <!-- ajax填充列表 -->
          </tbody>
        </table><div class="pagging">
        <button id="firstPage">首页</button>
        <button id="previous">上一页</button>
        <button id="next">下一页</button>
        <button id="last">尾页</button>
          共${totalPageSize}条记录

  </div>
     </div>
</div>
</div>
</main>
</body>
</html>