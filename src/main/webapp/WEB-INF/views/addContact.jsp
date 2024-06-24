<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${bundle['addContact.title']}</title>
</head>
<body>
<h1>${bundle['addContact.header']}</h1>
<form action="${pageContext.request.contextPath}/addContact" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="add">
    <label for="name">${bundle['addContact.name']}:</label>
    <input type="text" name="name" id="name" required><br><br>
    <label for="phone">${bundle['addContact.phone']}:</label>
    <input type="text" name="phone" id="phone" required><br><br>
    <label for="photo">${bundle['addContact.photoUrl']}:</label>
    <input type="file" name="photo" id="photo"><br><br>
    <button type="submit">${bundle['addContact.create']}</button>
</form>
<a href="${pageContext.request.contextPath}/dashboard">${bundle['addContact.back']}</a>
</body>
</html>
