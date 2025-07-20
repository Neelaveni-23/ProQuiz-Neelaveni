package com.quiz.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quiz.springboot.entity.User;
import com.quiz.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@Controller
@Tag(name = "User Controller", description = "Handles login, OTP verification, and password reset")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@Operation(summary = "Display login page")
	@GetMapping("/")
	public String displayLogin() {
		return "login";
	}

	@Operation(summary = "Show login page")
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	@Operation(summary = "Verify user credentials and send OTP")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Login successful, redirecting to OTP page"),
			@ApiResponse(responseCode = "401", description = "Invalid credentials") })
	@PostMapping("/login")
	public String verifyUser(@RequestParam String email, @RequestParam String password, HttpSession session,
			Model model) {
		logger.info("Attempting login for user: {}", email);
		User user = userService.verifyUser(email, password);
		if (user != null) {
			logger.info("Login successful for user: {}", email);
			session.setAttribute("email", email);
			session.setAttribute("fullName", user.getFullName());
			session.setAttribute("role", user.getUserRole());
			return "verifyotp";
		} else {
			logger.warn("Login failed for user: {}", email);
			model.addAttribute("error", "Invalid Email or Password");
			return "login";
		}
	}

	@Operation(summary = "Verify OTP and redirect user to their dashboard")
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam String otp, HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		String role = (String) session.getAttribute("role");
		if (email != null && userService.verifyOtp(email, otp)) {
			logger.info("OTP verification successful for email: {}", email);
			User user = userService.findByEmail(email);
			session.setAttribute("user", user);
			switch (role.toUpperCase()) {
			case "ADMIN":
				logger.info("Redirecting to admin page for user: {}", email);
				return "admin";
			case "TEACHER":
				logger.info("Redirecting to teacher home for user: {}", email);
				return "redirect:/teacher/home";
			case "STUDENT":
				logger.info("Redirecting to student page for user: {}", email);
				return "student";
			default:
				logger.warn("Unknown role '{}' for user: {}. Redirecting to login.", role, email);
				return "login";
			}
		} else {
			logger.warn("OTP verification failed for email: {}", email);
			model.addAttribute("error", "Invalid or expired OTP");
			return "verifyotp";
		}
	}

	@Operation(summary = "Resend OTP to User Email")
	@PostMapping("/resend-otp")
	public String resendOtp(@RequestParam String email, Model model) {
		logger.info("Resend OTP request received for email: {}", email);
		if (userService.resendOtp(email)) {
			logger.info("OTP successfully resent to email: {}", email);
			model.addAttribute("message", "OTP resent to your email");
			model.addAttribute("email", email);
			return "verifyotp";
		} else {
			logger.warn("Failed to resend OTP to email: {}", email);
			model.addAttribute("error", "Unable to resend OTP");
			return "login";
		}
	}

	@Operation(summary = "Show forgot password page")
	@GetMapping("/forgot-password")
	public String showForgotPassword() {
		logger.info("Displaying forgot-password page");
		return "forgot-password";
	}

	@Operation(summary = "Send OTP for password reset")
	@PostMapping("/forgot-password")
	public String processForgotPassword(@RequestParam String email, Model model) {
		logger.info("Received forgot password request for email: {}", email);
		boolean userExists = userService.findByEmail(email) != null;
		if (!userExists) {
			logger.warn("No user found with email: {}", email);

			model.addAttribute("error", "No account found with that email.");
			return "forgot-password";
		}

		userService.generateAndSaveOtp(email);

		logger.info("OTP generated and sent to email: {}", email);

		model.addAttribute("email", email);
		model.addAttribute("message", "OTP sent to your email.");
		return "reset-password";
	}

	@Operation(summary = "Reset user password using OTP")
	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword,
			Model model) {
		Logger logger = LoggerFactory.getLogger(UserController.class);
		boolean validOtp = userService.verifyOtp(email, otp);
		if (!validOtp) {
			logger.warn("Invalid or expired OTP entered for email: {}", email);
			model.addAttribute("error", "Invalid or expired OTP.");
			model.addAttribute("email", email);
			return "reset-password";
		}

		userService.updatePassword(email, newPassword);
		logger.info("Password successfully reset for email: {}", email);

		model.addAttribute("message", "Password reset successfully. Please login.");
		return "login";
	}
}
