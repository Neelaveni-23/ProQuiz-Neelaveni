<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <h2>Upload Questions</h2>

    <!-- Show form ONLY if no uploadCompleted -->
    <c:if test="${empty sessionScope.uploadCompleted}">
        <form action="${pageContext.request.contextPath}/teacher/upload-questions" method="post" enctype="multipart/form-data">
            <label>Select Excel File:</label>
            <input type="file" name="file" accept=".xls,.xlsx" required />
            <button type="submit">Next</button>
        </form>
    </c:if>	
    <!-- If questions counted, show confirmation -->
    <c:if test="${not empty sessionScope.questionCount}">
        <p>Total <strong>${sessionScope.questionCount}</strong> questions found. Do you want to upload?</p>
        <form action="${pageContext.request.contextPath}/teacher/confirm-upload" method="post" style="display:inline;">
            <button type="submit">Continue Upload</button>
        </form>
        OR
        <form action="${pageContext.request.contextPath}/teacher/reset-upload" method="post" style="display:inline;">
            <button type="submit">Select New File</button>
        </form>
    </c:if>

    <!-- Show upload status if any -->
    <c:if test="${not empty sessionScope.uploadStatus}">
        <p><strong>${sessionScope.uploadStatus}</strong></p>
    </c:if>
</div>