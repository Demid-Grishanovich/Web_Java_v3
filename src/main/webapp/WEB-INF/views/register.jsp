<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h2>Register</h2>
<form action="/v3_war_exploded/register" method="post">
    Username: <input type="text" name="username" /><br>
    Email: <input type="text" name="email" /><br>
    Password: <input type="password" name="password" /><br>
    <input type="submit" value="Register" />
</form>
<% if (request.getAttribute("error") != null) { %>
<p><%= request.getAttribute("error") %></p>
<% } %>
</body>
</html>
