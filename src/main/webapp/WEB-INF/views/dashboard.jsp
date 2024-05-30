<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${bundle['dashboard.title']}</title>
</head>
<body>
<h1>${bundle['dashboard.welcome']} ${sessionScope.currentUser.username}!</h1>
<h2>${bundle['dashboard.contacts']}</h2>
<a href="${pageContext.request.contextPath}/addContact">${bundle['dashboard.addContact']}</a><br><br>
<table border="1">
    <tr>
        <th>${bundle['dashboard.name']}</th>
        <th>${bundle['dashboard.phone']}</th>
        <th>${bundle['dashboard.actions']}</th>
    </tr>
    <c:forEach var="contact" items="${contacts}">
        <tr>
            <td>${contact.name}</td>
            <td>${contact.phone}</td>
            <td>
                <a href="${pageContext.request.contextPath}/contacts?action=edit&contactId=${contact.id}">${bundle['dashboard.edit']}</a>
                <a href="${pageContext.request.contextPath}/contacts?action=delete&contactId=${contact.id}" onclick="return confirm('${bundle['dashboard.confirmDelete']}');">${bundle['dashboard.delete']}</a>
            </td>
        </tr>
    </c:forEach>
</table>
<h2>${bundle['dashboard.actionsHeader']}</h2>
<a href="${pageContext.request.contextPath}/logout">${bundle['dashboard.logout']}</a>
</body>
</html>
