F<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Question</title>
    <style>
        body {
            font-family: Arial;
            margin: 50px;
        }
        .form-box {
            width: 600px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
        }
        input, textarea, select, button {
            width: 100%;
            margin-top: 10px;
            padding: 10px;
            font-size: 16px;
        }
        label {
            margin-top: 15px;
            display: block;
        }
    </style>
</head>
<body>
    <div class="form-box">
        <h2>Add Question</h2>

        <form action="/add-question" method="post">

            <!-- Course Name (Dropdown from topic.course.courseName) -->
            <label for="course">Course Name:</label>
            <select name="courseId" required>
                <c:forEach var="course" items="${courses}">
                    <option value="${course.courseId}">${course.courseName}</option>
                </c:forEach>
            </select>

            <!-- Topic Name -->
            <label for="topic">Topic Name:</label>
            <select name="topicId" required>
                <c:forEach var="topic" items="${topics}">
                    <option value="${topic.topicId}">
                        ${topic.topicName} (${topic.course.courseName})
                    </option>
                </c:forEach>
            </select>

            <!-- Question -->
            <label for="question">Question:</label>
            <textarea name="questionText" rows="3" required></textarea>

            <!-- Options -->
            <label>Option A:</label>
            <input type="text" name="option1" placeholder="Option A" required />
            <label>Option B:</label>
            <input type="text" name="option2" placeholder="Option B" required />
            <label>Option C:</label>
            <input type="text" name="option3" placeholder="Option C" required />
            <label>Option D:</label>
            <input type="text" name="option4" placeholder="Option D" required />

            <!-- Correct Answer -->
            <label>Correct Answer (A, B, C, D):</label>
            <input type="text" name="correctAnswer" required />

            <button type="submit">Add Question</button>
        </form>

        <c:if test="${not empty message}">
            <p style="color: green;">${message}</p>
        </c:if>
    </div>
</body>
</html>