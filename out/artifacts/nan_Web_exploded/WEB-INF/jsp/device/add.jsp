<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎来到这个页面~~</title>
</head>
<body>
<main>
  <div class="row">
    <div id="index-banner" class="index-banner section no-pad-bot primary-bg">
      <div class="container">
        <h1 class=" center-on-small-only  text-light ">添加机器</h1>
        <br>
      </div>
    </div>


    <form action="EditMachineServlet" method="post">

      <div class="row">
        <div class="input-field col s6">
          <input id="id" name ="id"  type="hidden"  value= "${id}">
        </div>
      </div>

        <div class="row">
          <div class="input-field col s6">
          <label></label>
    <select id="plantform" name="plantform">
    <option value="">请选择手机系统</option>
    <option value="android">android系统</option>
    <option value="ios">ios系统</option>
   </select>
          </div>
        </div>
      <div class="row">
          <div class="input-field col s6">
            <input id="tel_name" name ="tel_name"  type="text"  data-default-value="请输入手机名称" class="validate">
            <label for="model">手机名称</label>
          </div>
        </div>
      <div class="row">
        <div class="input-field col s6">
          <input id="os" name ="tel_version"  type="text" data-default-value="请输入手机版本" class="validate">
          <label for="os">手机版本</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s6">
          <input id="NO" name ="imei"  type="text" data-default-value="请输入手机编号" class="validate">
          <label for="NO">手机编号</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s6">
          <input id="user" name ="borrow_name"  type="text" data-default-value="请输入手机使用人" class="validate">
          <label for="user">使用人</label>
        </div>
      </div>
     
      <div class="row">
        <button class="btn waves-effect waves-light" type="submit" name="action">确定
          <i class="mdi-content-send right"></i>
        </button>
        &nbsp&nbsp
        <a class="waves-effect waves-teal btn-flat" onclick="javascript:history.back(-1);">返回</a>
      </div>

    </form>

    </div>
  <script type="text/javascript">
    $(document).ready(function() {
  $('select').material_select();
  });
  </script>

</main>

</body>
</html>