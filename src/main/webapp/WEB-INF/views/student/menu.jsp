<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base target="contentFrame" href="<%=basePath%>">
    <title>学生信息</title> 
	<META content="text/html; charset=utf-8" http-equiv=Content-Type>
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/style.css">
	<style type="text/css">
	div{
	 margin-top:20px;
	}
	</style>
	<script  language=javascript1.2>
	function showSubmenu(sid){
	  whichEL=eval("sumMenu"+sid);
	  if(whichEL.style.display=="none"){
	  	eval("subMenu" + sid + ".style.display=\"\";")
	  }else{
	    eval("subMenu"+ sid + ".style.display=\"none\";")
	  }
	}
	
	</script>
  </head>
  <body style="background-color:DCDCDC">
  <div class="head" align="center">
      <p size="5px" class="text-muted">xxx同学，你好！</p>
  </div>
  <div class="dropdown" >
      <button class="btn btn-primary dropdown-toggle form-control" type="button" id="dropdownMenu1" data-toggle="dropdown">
          个人信息管理
          <span class="caret"></span>
      </button>
      <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
          <li role="presentation"><a href="/modifyPassword">修改密码</a></li>
          <li role="presentation"><a href="/modifyUserInfo">修改个人信息</a></li>
      </ul>
  </div>
  <div >
      <a class="btn btn-danger form-control" href="/logout">退出登录</a>
  </div>
  </body>
  <script src="js/jquery.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
</html>
