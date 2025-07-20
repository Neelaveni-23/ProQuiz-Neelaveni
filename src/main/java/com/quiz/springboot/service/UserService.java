package com.quiz.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.springboot.entity.User;
import com.quiz.springboot.dao.UserDAO;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDAO userDao;

	@Autowired
	private EmailService emailService;

	@Autowired
	private OTPService otpService;

	public User verifyUser(String email, String password) {
		logger.info("Verifying user with email: {}", email);
		User user = userDao.findByEmailAndPassword(email, password);
		if (user != null) {
			int otp = otpService.generateOTP();
			logger.info("Generated OTP for {}: {}", email, otp);
			updateOtp(email, otp);
			emailService.sendEmail(user.getEmail(), "OTP Verification", "Your OTP is: " + otp);
			logger.info("OTP email sent to {}", email);
			return user;
		} else {
			logger.warn("Invalid login attempt for email: {}", email);
		}
		return null;
	}

	public boolean verifyOtp(String email, String otp) {
		logger.info("Verifying OTP for email: {}", email);
		try {
			int otpInt = Integer.parseInt(otp);
			return userDao.findByEmailAndOtp(email, otpInt) != null;
		} catch (NumberFormatException e) {
			logger.error("Invalid OTP format provided for email: {}", email);
			return false;
		}
	}

	public boolean resendOtp(String email) {
		logger.info("Resending OTP to email: {}", email);
		User user = userDao.findByEmail(email);
		if (user != null) {
			int otp = otpService.generateOTP();
			logger.info("Generated new OTP for {}: {}", email, otp);
			updateOtp(email, otp);
			emailService.sendEmail(user.getEmail(), "Resent OTP", "Your new OTP is: " + otp);
			logger.info("Resent OTP email sent to {}", email);
			return true;
		}
		logger.warn("Attempted to resend OTP to non-existent email: {}", email);
		return false;
	}

	public void updateOtp(String email, int otp) {
		logger.info("Updating OTP for email: {}", email);
		userDao.updateOtpByEmail(email, otp);
		logger.debug("OTP updated in database for email: {}", email);
	}

	public User findByEmail(String email) {
		logger.info("Finding user by email: {}", email);
		return userDao.findByEmail(email);
	}

	public void updatePassword(String email, String newPassword) {
		logger.info("Updating password for email: {}", email);
		User user = userDao.findByEmail(email);
		if (user != null) {
			user.setPassword(newPassword); // Ideally, hash this password
			userDao.save(user);
			logger.info("Password updated for email: {}", email);
		} else {
			logger.warn("Password update failed: user not found with email: {}", email);
		}
	}

	public String generateAndSaveOtp(String email) {
		logger.info("Generating OTP for password reset - email: {}", email);
		int otp = otpService.generateOTP();
		userDao.updateOtpByEmail(email, otp);
		String subject = "Your OTP for Password Reset";
		String body = "<p>Dear User,</p>" + "<p>Your OTP is: <strong>" + otp + "</strong></p>"
				+ "<p>This OTP is valid for 10 minutes.</p>";

		emailService.sendEmail(email, subject, body);
		logger.info("Password reset OTP sent to email: {}", email);

		return String.valueOf(otp);
	}

}
