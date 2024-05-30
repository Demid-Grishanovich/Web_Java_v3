<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${bundle['register.title']}</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/">${bundle['register.back']}</a><br>
<h2>${bundle['register.header']}</h2>
<form action="${pageContext.request.contextPath}/register" method="post">
    ${bundle['register.username']}: <input type="text" name="username" /><br>
    ${bundle['register.email']}: <input type="text" name="email" /><br>
    ${bundle['register.password']}: <input type="password" name="password" required><br>
    <input type="submit" value="${bundle['register.submit']}" />
</form>
<% if (request.getAttribute("error") != null) { %>
<p>${bundle['register.error']}</p>
<% } %>
</body>
</html>
