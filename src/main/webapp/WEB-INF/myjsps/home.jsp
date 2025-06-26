<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Welcome</title>
<style>
body {
	font-family: 'Segoe UI', sans-serif;
	background-color: #007BFF;
	margin: 0;
	padding: 0;
	height: 100vh;
	position: relative;
}

.home-box {
	background: #d4edda;
	padding: 30px;
	border-radius: 12px;
	box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
	text-align: center;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}

.top-right-button {
	position: absolute;
	top: 20px;
	right: 20px;
}

.top-right-button button {
	padding: 10px 20px;
	background-color: #4CAF50;
	color: white;
	border: none;
	border-radius: 6px;
	font-size: 14px;
	cursor: pointer;
}

.top-right-button button:hover {
	background-color: #45a049;
}
</style>
</head>
<body>
	<div class="top-right-button">
		<form action="/dashboard" method="get">
			<button type="submit">Dashboard</button>
		</form>
	</div>
	<div class="home-box">
		<h1>ProQuiz</h1>
		<p>Login Successfully</p>
	</div>
</body>
</html>
