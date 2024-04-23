<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>Login</h2>
<form action="/v3_war_exploded/login" method="post">
    Username: <input type="text" name="username" /><br>
    Password: <input type="password" name="password" /><br>
    <input type="submit" value="Login" />
</form>
<% if (request.getAttribute("error") != null) { %>
<p><%= request.getAttribute("error") %></p>
<% } %>
</body>
</html>
