<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${bundle['dashboard.title']}</title>
    <style>
        .edit-btn {
            background-color: #4CAF50;
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

        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        .pagination a {
            color: black;
            float: left;
            padding: 8px 16px;
            text-decoration: none;
            transition: background-color .3s;
        }

        .pagination a.active {
            background-color: #4CAF50;
            color: white;
            border-radius: 5px;
        }

        .pagination a:hover:not(.active) {
            background-color: #ddd;
            border-radius: 5px;
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

<div class="pagination">
    <c:if test="${currentPage > 1}">
        <a href="${pageContext.request.contextPath}/dashboard?page=${currentPage - 1}">&laquo; Previous</a>
    </c:if>
    <c:forEach var="i" begin="1" end="${totalPages}">
        <a href="${pageContext.request.contextPath}/dashboard?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
    </c:forEach>
    <c:if test="${currentPage < totalPages}">
        <a href="${pageContext.request.contextPath}/dashboard?page=${currentPage + 1}">Next &raquo;</a>
    </c:if>
</div>

<h2>${bundle['dashboard.actionsHeader']}</h2>
<a href="${pageContext.request.contextPath}/logout">${bundle['dashboard.logout']}</a>
</body>
</html>
