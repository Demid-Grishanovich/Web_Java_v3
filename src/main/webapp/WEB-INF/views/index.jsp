<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title><fmt:message key="index.title" /></title>
</head>
<body>
<a href="?lang=en">English</a> | <a href="?lang=fr">Français</a><br>
<h1>${bundle['index.welcome']}</h1>
<a href="login">${bundle['index.login']}</a> | <a href="register">${bundle['index.register']}</a> | <a href="dashboard">${bundle['index.dashboard']}</a>
</body>
</html>
