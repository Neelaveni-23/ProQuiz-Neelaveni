<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Question Details</h2>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>Course</th>
        <td>${question.course.courseName}</td>
    </tr>
    <tr>
        <th>Topic</th>
        <td>${question.topic.topicName}</td>
    </tr>
    <tr>
        <th>Question Text</th>
        <td>${question.questionText}</td>
    </tr>
    <tr>
        <th>Options</th>
        <td>
            <ul>
                <c:forEach var="opt" items="${question.options}" varStatus="status">
                    <li>Option ${status.index + 1}: ${opt.optionData}</li>
                </c:forEach>
            </ul>
        </td>
    </tr>
    <tr>
        <th>Correct Answer</th>
        <td>${question.correctAnswer}</td>
    </tr>
</table>

<br/>
<a href="${pageContext.request.contextPath}/teacher/home?view=view">
    Back to Questions List
</a>