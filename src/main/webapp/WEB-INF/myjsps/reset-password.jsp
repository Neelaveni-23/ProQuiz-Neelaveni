<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reset Password</title>
</head>
<body>
<h2>Reset Password</h2>
<form action="${pageContext.request.contextPath}/reset-password" method="post">
    <input type="hidden" name="email" value="${email}" />
    <label>OTP:</label>
    <input type="text" name="otp" required /><br><br>
    <label>New Password:</label>
    <input type="password" name="newPassword" required /><br><br>
    <button type="submit">Reset Password</button>
</form>

<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>
</body>
</html>