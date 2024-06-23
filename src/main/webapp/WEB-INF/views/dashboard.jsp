<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${bundle['dashboard.title']}</title>
    <style>
        .edit-btn {
            background-color: #4CAF50; /* Green */
            color: white;
            padding: 8px 16px;
            margin: 4px 2px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .delete-btn {
            color: red;
            background-color: transparent;
            border: none;
            text-decoration: underline;
            cursor: pointer;
        }
    </style>
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
                <form action="${pageContext.request.contextPath}/editContact" method="GET" style="display: inline;">
                    <input type="hidden" name="contactId" value="${contact.id}">
                    <button type="submit" class="edit-btn">${bundle['dashboard.edit']}</button>
                </form>
                <form action="${pageContext.request.contextPath}/contacts" method="POST" style="display: inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="contactId" value="${contact.id}">
                    <button type="submit" class="delete-btn">${bundle['dashboard.delete']}</button>
                </form>

            </td>
        </tr>
    </c:forEach>
</table>
<h2>${bundle['dashboard.actionsHeader']}</h2>
<a href="${pageContext.request.contextPath}/logout">${bundle['dashboard.logout']}</a>
</body>
</html>
