<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${bundle['addContact.title']}</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/dashboard">${bundle['addContact.back']}</a><br>
<h1>${bundle['addContact.header']}</h1>
<form action="${pageContext.request.contextPath}/contacts" method="post">
    <input type="hidden" name="action" value="add">
    ${bundle['addContact.name']}: <input type="text" name="name" required><br>
    ${bundle['addContact.phone']}: <input type="text" name="phone" required><br>
    ${bundle['addContact.photoUrl']}: <input type="text" name="photoUrl"><br>
    <button type="submit">${bundle['addContact.create']}</button>
</form>
</body>
</html>
