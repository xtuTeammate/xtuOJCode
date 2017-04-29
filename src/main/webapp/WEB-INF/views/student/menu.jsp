<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="securityrity" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML >
<html>
<head>
    <base target="contentFrame" href="<%=basePath%>">
    <title>学生信息</title>
    <META content="text/html; charset=utf-8" http-equiv=Content-Type>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/custom.css">
    <style type="text/css">
        div {
            margin-top: 20px;
        }
    </style>
</head>
<body style="background-color:#DCDCDC">
<div class="head" align="center">
    <a size="5px" class="text-muted" href="/info"> 你好！</a>
</div>
<security:authorize access="isAuthenticated()">
    <securityrity:authentication property="principal.username" var="userId"/>
</security:authorize>
<div class="dropdown">
    <button class="btn btn-primary dropdown-toggle form-control" type="button" id="dropdownMenu1"
            data-toggle="dropdown">
        个人信息管理
        <span class="caret"></span>
    </button>
    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
        <li role="presentation"><a href="/userInfo/${userId}">个人信息</a></li>
        <li role="presentation"><a href="/modifyPassword">修改密码</a></li>
        <li role="presentation"><a href="/modifyUserInfo">修改个人信息</a></li>
    </ul>
</div>
<div>
    <a class="btn btn-danger form-control" href="/logout" target="_top">退出登录</a>
</div>
</body>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</html>
