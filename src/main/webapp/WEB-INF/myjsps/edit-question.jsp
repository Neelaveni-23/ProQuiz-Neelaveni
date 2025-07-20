<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Edit Question</h2>

<form method="post" action="${pageContext.request.contextPath}/teacher/edit-question">
    <input type="hidden" name="questionId" value="${question.questionId}"/>

    <label>Course:</label>
    <select name="courseId">
        <c:forEach var="c" items="${courses}">
            <option value="${c.courseId}" ${c.courseId == question.course.courseId ? 'selected' : ''}>
                ${c.courseName}
            </option>
        </c:forEach>
    </select>
    <br/><br/>

    <label>Topic:</label>
    <select name="topicId">
        <c:forEach var="t" items="${topics}">
            <option value="${t.topicId}" ${t.topicId == question.topic.topicId ? 'selected' : ''}>
                ${t.topicName}
            </option>
        </c:forEach>
    </select>
    <br/><br/>

    <label>Question Text:</label><br/>
    <textarea name="questionText" rows="4" cols="60">${question.questionText}</textarea>
    <br/><br/>

    <label>Option A:</label><br/>
    <input type="text" name="optionA" value="${question.options[0].optionData}" size="60"/>
    <br/><br/>

    <label>Option B:</label><br/>
    <input type="text" name="optionB" value="${question.options[1].optionData}" size="60"/>
    <br/><br/>

    <label>Option C:</label><br/>
    <input type="text" name="optionC" value="${question.options[2].optionData}" size="60"/>
    <br/><br/>

    <label>Option D:</label><br/>
    <input type="text" name="optionD" value="${question.options[3].optionData}" size="60"/>
    <br/><br/>

    <label>Correct Answer:</label><br/>
    <input type="text" name="correctAnswer" value="${question.correctAnswer}" size="10"/>
    <br/><br/>

    <button type="submit">Save Changes</button>
    <a href="${pageContext.request.contextPath}/teacher/home?view=view">Cancel</a>
</form>