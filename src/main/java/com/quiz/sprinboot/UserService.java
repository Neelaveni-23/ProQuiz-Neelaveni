package com.quiz.sprinboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private EmailService emailService;

	@Autowired
	private OTPService otpService;

	public boolean verifyUser(String email, String password) {
		User user = userDAO.verifyUser(email, password);
		if (user != null) {
			int otp = otpService.generateOTP();
			updateOtp(email, otp);
			emailService.sendEmail(user.getEmail(), "OTP Verification", "Your OTP is: " + otp);
			return true;
		}
		return false;
	}

	public boolean verifyOtp(String email, String otp) {
		try {
			int otpInt = Integer.parseInt(otp);
			return userDAO.verifyOtp(email, otpInt);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean resendOtp(String email) {
		User user = userDAO.getUserByEmail(email);
		if (user != null) {
			int otp = otpService.generateOTP();
			updateOtp(email, otp);
			emailService.sendEmail(user.getEmail(), "Resent OTP", "Your new OTP is: " + otp);
			return true;
		}
		return false;
	}

	public void updateOtp(String email, int otp) {
		userDAO.updateOtp(email, otp);
	}
}