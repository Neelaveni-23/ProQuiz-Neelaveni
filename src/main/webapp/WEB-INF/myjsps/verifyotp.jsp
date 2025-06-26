<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Verify OTP</title>
<style>
body {
	font-family: Arial, sans-serif;
	background: #f4f4f4;
	padding: 50px;
}

.otp-box {
	width: 400px;
	margin: auto;
	padding: 30px;
	background: #fff;
	border-radius: 8px;
	box-shadow: 0 0 10px #ccc;
}

h2 {
	text-align: center;
	color: #333;
}

.input-group {
	margin-top: 20px;
}

label {
	display: block;
	font-weight: bold;
	margin-bottom: 8px;
}

input[type="text"] {
	width: 100%;
	padding: 10px;
	font-size: 16px;
}

.btn {
	margin-top: 20px;
	width: 100%;
	background: #28a745;
	border: none;
	color: white;
	padding: 10px;
	font-size: 16px;
	border-radius: 4px;
	cursor: pointer;
}

.btn:hover {
	background: #218838;
}

.resend-btn {
	background-color: #007bff;
	margin-top: 10px;
}

#timer {
	text-align: center;
	font-weight: bold;
	color: red;
	margin-bottom: 15px;
}
</style>
</head>
<body>

	<div class="otp-box">
		<h2>ProQuiz</h2>
		<form action="resend-otp" method="post">
			<label for="otp">OTP sent to your mail if not received click
				here to resend</label>
			<button type="submit" class="btn resend-btn">Resend</button>
		</form>

		<form action="verify-otp" method="post">
			<div class="input-group">
				<label for="otp">Enter OTP</label> <input type="text" name="otp"
					id="otp" required />
			</div>

			<button type="submit" class="btn">Submit</button>
		</form>
		<div id="timer">OTP will expire in: 03:00</div>
	</div>
	<script>
		let duration = 3 * 60; // 3 minutes in seconds
		let display = document.getElementById('timer');

		function startTimer(duration, display) {
			let timer = duration, minutes, seconds;
			let countdown = setInterval(
					function() {
						minutes = parseInt(timer / 60, 10);
						seconds = parseInt(timer % 60, 10);

						minutes = minutes < 10 ? "0" + minutes : minutes;
						seconds = seconds < 10 ? "0" + seconds : seconds;

						display.textContent = "OTP will expire in: " + minutes
								+ ":" + seconds;

						if (--timer < 0) {
							clearInterval(countdown);
							display.textContent = "OTP has expired!";
							document.getElementById("otpForm").elements["submitBtn"].disabled = true;
						}
					}, 1000);
		}

		window.onload = function() {
			startTimer(duration, display);
		};
	</script>
</body>
</html>
