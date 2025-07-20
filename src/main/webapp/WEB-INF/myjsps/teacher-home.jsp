<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Teacher Dashboard</title>
    <style>
    
     * {
            box-sizing: border-box;
        }
        body {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: Arial, sans-serif;
        }

        .header {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            height: 60px;
            background-color: #9597db;
            color: white;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 20px;
            z-index: 1000;
        }

        .header img {
            height: 40px;
        }

        .header .center-text {
            flex-grow: 1;
            text-align: center;
            font-size: 18px;
            font-weight: bold;
        }
 .container { display: flex; min-height:80vh; }
        .sidebar {
            position: fixed;
            top: 60px; /* below header */
            left: 0;
            bottom: 0;
            width: 250px;
            background-color: #f0f0f0;
            padding-top: 20px;
            overflow-y: auto;
            border-right: 1px solid #ccc;
        }

        .sidebar a {
            display: block;
            padding: 12px 20px;
            color: #333;
            text-decoration: none;
            font-weight: bold;
        }

        .sidebar a:hover {
            background-color: #ddd;
        }

        .main-content {
             margin-left: 250px;
            margin-top: 60px;
            padding: 30px;
            min-height: calc(100vh - 60px);
        }

        .footer {
            background-color: #9597db;
            color: white;
            text-align: center;
            padding: 10px;
            position: fixed;
            bottom: 0;
            left: 0;
            width: 100%;
        }

        a.logout-link {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }

        select,textarea {
            padding: 8px;
            font-size: 16px;
            margin-bottom: 20px;
            width: 10opx;
        }
table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        button {
            padding: 8px 16px;
            background-color: #9597db;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #7a7cc7;
        }
        .topics-list {
            padding-left: 20px;
        }
    </style>
</head>
<body>

<!-- Header -->
<div class="header">
    <img src="${pageContext.request.contextPath}/images/logo.png" alt="Logo">
    <div class="center-text">Welcome to ${sessionScope.user.fullName}</div>
    <form action="${pageContext.request.contextPath}/teacher/logout" method="post">
    <button type="submit">Logout</button>
    </form>
</div>

<div class="container">
<div class="sidebar">

    <a href="${pageContext.request.contextPath}/teacher/home?view=add">Add Question</a>
    <a href="${pageContext.request.contextPath}/teacher/home?view=view">View Questions</a>
    <a href="${pageContext.request.contextPath}/teacher/home?view=upload">Upload Questions</a>
    
</div>

<div class="main-content">  
 <c:choose>
            <c:when test="${view eq 'add'}">
                <jsp:include page="add-question.jsp"/>
            </c:when>
            <c:when test="${view eq 'view'}">
        <jsp:include page="view-questions.jsp"/>
    </c:when>
     <c:when test="${view eq 'upload'}">
        <%-- Show upload form --%>
        <jsp:include page="upload-questions.jsp"/>
    </c:when>
            <c:otherwise>
        <form method="get" action="${pageContext.request.contextPath}/teacher/home">
    <label>Course Name:</label>
    <select name="courseId" onchange="this.form.submit()">
        <c:forEach var="course" items="${courses}">
            <option value="${course.courseId}"
                <c:if test="${selectedCourse != null && selectedCourse.courseId == course.courseId}">selected</c:if>>
                ${course.courseName}
            </option>
        </c:forEach>
     
    </select>


    
        <table>
            <thead>
                <tr>
                    <th>Topic</th>
                    <th>No. of Questions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="topic" items="${topics}">
                    <tr>
                        <td>${topic.topicName}</td>
                        <td>${topic.noOfQuestions}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

</form>
 
 </c:otherwise>
        </c:choose>
 </div>
 </div>
<!-- Footer -->
<div class="footer">
    &copy; 2025 ProQuiz | Designed by Neelu
</div>

</body>
</html>