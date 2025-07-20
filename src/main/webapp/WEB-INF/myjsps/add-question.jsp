<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
  <h2>Add Question</h2>
  <form method="post" action="${pageContext.request.contextPath}/teacher/add-question">

    <label>Course:</label>
    <select name="courseId" required>
      <c:forEach var="course" items="${courses}">
        <option value="${course.courseId}">${course.courseName}</option>
      </c:forEach>
    </select>
    <br/>

    <label>Topic:</label>
    <select name="topicId" required>
      <c:forEach var="topic" items="${topics}">
        <option value="${topic.topicId}">${topic.topicName}</option>
      </c:forEach>
    </select>
    <br/>

    <label>Question Text:</label><br/>
    <textarea name="questionText" rows="4" cols="60" required></textarea>
    <br/>

    <label>Option A:</label><br/>
    <textarea name="optionA" rows="2" cols="60" required></textarea>
    <br/>

    <label>Option B:</label><br/>
    <textarea name="optionB" rows="2" cols="60" required></textarea>
    <br/>

    <label>Option C:</label><br/>
    <textarea name="optionC" rows="2" cols="60" required></textarea>
    <br/>

    <label>Option D:</label><br/>
    <textarea name="optionD" rows="2" cols="60" required></textarea>
    <br/>

    <label>Correct Answer:</label>
    <select name="correctAnswer" required>
      <option value="A">A</option>
      <option value="B">B</option>
      <option value="C">C</option>
      <option value="D">D</option>
    </select>
    <br/>

    <button type="submit">Add Question</button>
  </form>
