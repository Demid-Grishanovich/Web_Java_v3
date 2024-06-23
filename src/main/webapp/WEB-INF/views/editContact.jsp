<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${bundle['editContact.title']}</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/dashboard">${bundle['editContact.back']}</a><br><br>
<h1>${bundle['editContact.header']}</h1>
<form action="${pageContext.request.contextPath}/contacts" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="contactId" value="${contact.id}">
    ${bundle['editContact.name']}: <input type="text" name="name" value="${contact.name}" required><br>
    ${bundle['editContact.phone']}: <input type="text" name="phone" value="${contact.phone}" required><br>
    ${bundle['editContact.photoUrl']}: <input type="text" name="photoUrl" value="${contact.photoUrl}"><br>
    <button type="submit">${bundle['editContact.save']}</button>
</form>
</body>
</html>
