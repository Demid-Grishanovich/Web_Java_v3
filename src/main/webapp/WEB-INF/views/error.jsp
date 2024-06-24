<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
<h1>Error Details</h1>
<p>Status Code: ${statusCode}</p>
<p>Error Message: ${errorMessage}</p>
<c:if test="${not empty exception}">
    <p>Exception: ${exception.message}</p>
    <p>Stack Trace:</p>
    <ul>
        <c:forEach var="stackTraceElement" items="${exception.stackTrace}">
            <li>${stackTraceElement}</li>
        </c:forEach>
    </ul>
</c:if>
<a href="v3_war_exploded/index">Go Back to Home</a>
</body>
</html>