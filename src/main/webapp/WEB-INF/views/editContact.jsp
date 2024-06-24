<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${bundle['editContact.title']}</title>
</head>
<body>
<h1>${bundle['editContact.header']}</h1>
<form action="${pageContext.request.contextPath}/editContact" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="contactId" value="${contact.id}">
    <label for="name">${bundle['editContact.name']}:</label>
    <input type="text" name="name" id="name" value="${contact.name}" required><br><br>
    <label for="phone">${bundle['editContact.phone']}:</label>
    <input type="text" name="phone" id="phone" value="${contact.phone}" required><br><br>
    <label for="photo">${bundle['editContact.photoUrl']}:</label>
    <input type="file" name="photo" id="photo" accept="image/*"><br><br>
    <button type="submit">${bundle['editContact.save']}</button>
</form>
<a href="${pageContext.request.contextPath}/dashboard">${bundle['editContact.back']}</a>
</body>
</html>
