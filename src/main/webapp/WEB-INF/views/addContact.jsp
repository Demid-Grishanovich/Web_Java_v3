<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New Contact</title>
</head>
<body>
<a href="/v3_war_exploded/dashboard">back</a>
<br>
<h1>Add New Contact</h1>
<form action="${pageContext.request.contextPath}/contacts" method="post">
    <input type="hidden" name="action" value="add">
    Name: <input type="text" name="name" required><br>
    Phone: <input type="text" name="phone" required><br>
    Photo URL: <input type="text" name="photoUrl"><br>
    <button type="submit">Create Contact</button>
</form>
</body>
