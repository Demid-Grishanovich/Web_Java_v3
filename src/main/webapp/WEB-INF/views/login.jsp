<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${bundle['login.title']}</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/">${bundle['login.back']}</a><br>
<h2>${bundle['login.header']}</h2>
<form action="${pageContext.request.contextPath}/login" method="post">
    ${bundle['login.username']}: <input type="text" name="username" /><br>
    ${bundle['login.password']}: <input type="password" name="password" /><br>
    <input type="submit" value="${bundle['login.submit']}" />
</form>
<% if (request.getAttribute("error") != null) { %>
<p>${bundle['login.error']}</p>
<% } %>
</body>
</html>
