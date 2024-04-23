<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h1>Welcome, ${sessionScope.currentUser.username}!</h1>
<h2>Your Contacts</h2>
<a href="/contacts?action=add">Add New Contact</a>
<table border="1">
    <tr>
        <th>Name</th>
        <th>Phone</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="contact" items="${contacts}">
        <tr>
            <td>${contact.name}</td>
            <td>${contact.phone}</td>
            <td>
                <a href="/contacts?action=edit&contactId=${contact.id}">Edit</a>
                <a href="/contacts?action=delete&contactId=${contact.id}" onclick="return confirm('Are you sure?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<h2>Actions</h2>
<a href="/logout">Logout</a>
</body>
</html>
