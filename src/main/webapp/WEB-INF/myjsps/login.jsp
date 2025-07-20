<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Login Page</title>
<style>
body {
	font-family: 'Segoe UI', sans-serif;
	background-color: #e6e0fa;
	display: flex;
	height: 100vh;
	align-items: center;
	justify-content: center;
}

.login-container {
	background: white;
	padding: 30px;
	border-radius: 12px;
	box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
	width: 300px;
}

h2 {
	margin-bottom: 20px;
	text-align: center;
}

input[type="email"], input[type="password"] {
	width: 100%;
	padding: 12px;
	margin: 8px 0;
	border: 1px solid #ccc;
	border-radius: 6px;
}

button {
	width: 100%;
	padding: 10px;
	background: #4CAF50;
	border: none;
	color: white;
	font-size: 16px;
	border-radius: 6px;
}

button:disabled {
	background-color: #9e9e9e;
	cursor: not-allowed;
}

.error {
	color: red;
	margin-top: 10px;
	text-align: center;
}
</style>
<script>
	function disableLoginButton() {
		const loginBtn = document.getElementById("loginBtn");
		loginBtn.disabled = true;
		loginBtn.innerText = "Login";
	}
</script>
</head>
<body>
	<div class="login-container">
		<h2>ProQuiz</h2>
		<form action="/login" method="post" onsubmit="disableLoginButton()">
			<input type="email" name="email" placeholder="Email" required /> <input
				type="password" name="password" placeholder="Password" required />
			<button type="submit" id="loginBtn">Login</button>
			<p><a href="${pageContext.request.contextPath}/forgot-password">Forgot Password?</a></p>
		</form>
		<div class="error">${error}</div>
	</div>
</body>
</html>
