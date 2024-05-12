<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <div>
        Logged in as : <span id="user">${userName}</span>
        <img src="${picture}" alt="">
    </div>
    <div>
        <button onclick="location.href='/oauth2/authorization/google'">
            로그인
        </button>
        <button onclick="location.href='/logout'">
            로그아웃
        </button>
<%--        <a href="/logout" class="btn btn-info active" role="button">Logout</a>--%>
    </div>



<%--<a href="/oauth2/authorization/google" class="btn btn-success active" role="button">Google Login</a>--%>
</body>
</html>

