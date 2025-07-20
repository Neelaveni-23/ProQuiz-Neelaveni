<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<style>
nav ul li {
    display: inline;
}
nav ul li a {
    text-decoration: none;
    padding: 6px 12px;
    border: 1px solid #ccc;
    background: #f0f0f0;
    color: #333;
    display: inline-block;
}
nav ul li a:hover {
    background: #ddd;
}
nav ul li strong {
    padding: 6px 12px;
    border: 1px solid #ccc;
    background: #9597db;
    color: white;
}
nav ul li .disabled-btn {
    display: inline-block;
    padding: 6px 12px;
    border: 1px solid #ccc;
    background: #eee;
    color: #999;
    cursor: not-allowed;
}
nav ul li span {
    display: inline-block;
    padding: 6px 12px;
    border: 1px solid #ccc;
    background: #eee;
    color: #999;
    cursor: not-allowed;
}
nav ul li a button {
    padding: 6px 12px;
    border: 1px solid #ccc;
    background: #f0f0f0;
    color: #333;
    cursor: pointer;
}
nav ul li a button:hover {
    background: #ddd;
}
nav ul li button[disabled] {
    background: #eee;
    color: #999;
    cursor: not-allowed;
}
</style>
<h2>Show Questions</h2>

<form method="get" action="${pageContext.request.contextPath}/teacher/home">
    <input type="hidden" name="view" value="view"/>
    <label>Course:</label>
    <select name="courseId" onchange="this.form.submit()">
        <c:forEach var="c" items="${courses}">
            <option value="${c.courseId}" 
                            <c:if test="${selectedCourse != null and c.courseId == selectedCourse.courseId}">selected</c:if>>
            ${c.courseName}
            </option>
        </c:forEach>
    </select>
    <label>Topic:</label>
    <select name="topicId">
        <c:forEach var="t" items="${topics}">
            <option value="${t.topicId}" 
                            <c:if test="${selectedTopicId != null and t.topicId == selectedTopicId}">selected</c:if>>
            
                ${t.topicName}
            </option>
        </c:forEach>
    </select>
    <button type="submit">Show</button>
</form>

<hr/>

<c:if test="${questionsPage != null and not empty questionsPage.content}">
<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
<div>
Questions from Course: ${selectedCourse.courseName}
<c:if test="${not empty selectedTopicId}">
    - Topic:
    <c:forEach var="t" items="${topics}">
        <c:if test="${t.topicId == selectedTopicId}">
            ${t.topicName}
        </c:if>
    </c:forEach>
</c:if>
</div>
<div>
            <c:set var="start" value="${questionsPage.number * questionsPage.size + 1}" />
            <c:set var="end" value="${start + questionsPage.numberOfElements - 1}" />
            <span style="font-weight: bold;">
                Showing ${start}–${end} of ${questionsPage.totalElements} questions
            </span>
        </div>
        </div>
        
<table border="1" cellpadding="5" cellspacing="0">
    <thead>
        <tr>
            <th>S.No</th>
            <th>Question</th>
            <th>Correct Answer</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    <c:set var="startSerial" value="${questionsPage.number * questionsPage.size}" />
    <c:forEach var="q" items="${questionsPage.content}" varStatus="status">
        <tr>
            <td>${startSerial + status.index + 1}</td>
            <td>${q.questionText}</td>
            <td>${q.correctAnswer}</td>
            <td>
                <form method="get" action="${pageContext.request.contextPath}/teacher/view-full" style="display:inline;">
                    <input type="hidden" name="questionId" value="${q.questionId}"/>
                    <button type="submit">Full View</button>
                </form>
                <form method="get" action="${pageContext.request.contextPath}/teacher/edit-question" style="display:inline;">
                    <input type="hidden" name="questionId" value="${q.questionId}"/>
                    <button type="submit">Edit</button>
                </form>
                <form method="post" action="${pageContext.request.contextPath}/teacher/delete-question" style="display:inline;" onsubmit="return confirm('Are you sure?');">
                    <input type="hidden" name="questionId" value="${q.questionId}"/>
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</tbody>
</table>

 <!-- Pagination Controls -->
 <div style="margin-top:20px;">
    <c:if test="${questionsPage.totalPages > 1}">
        <nav>
            <ul style="list-style:none; display:flex; gap:10px; padding:0;">
                
                <!-- Previous button -->
                <li>
                <c:choose>
                
                <c:when test="${questionsPage.hasPrevious()}">
                    
                 <a href="${pageContext.request.contextPath}/teacher/home?view=view&courseId=${selectedCourse.courseId}&topicId=${selectedTopicId}&page=${questionsPage.number - 1}">
           &laquo; Previous
           </a>
                 </c:when>
                 <c:otherwise>
                 <span style="color:#999; padding:6px 12px; border:1px solid #ccc; background:#eee; cursor:not-allowed;">
                                &laquo; Previous
                            </span> 
                </c:otherwise>
                </c:choose>
                </li>
    
                <!-- Next button -->
                <li>
                <c:choose>
                <c:when test="${questionsPage.hasNext()}">
                    
                      <a href="${pageContext.request.contextPath}/teacher/home?view=view&courseId=${selectedCourse.courseId}&topicId=${selectedTopicId}&page=${questionsPage.number + 1}">
                            Next &raquo;
                        </a>
                    </c:when>
                    <c:otherwise>
                            <span style="color:#999; padding:6px 12px; border:1px solid #ccc; background:#eee; cursor:not-allowed;">
                                Next &raquo;
                            </span>
                        </c:otherwise>
                        </c:choose>
                        </li>
            </ul>
        </nav>
   </c:if>  
  </c:if>
    
       
<c:if test="${questionsPage == null or empty questionsPage.content}">
    <p>No questions to display. Please select a topic and click Show.</p>
</c:if>


