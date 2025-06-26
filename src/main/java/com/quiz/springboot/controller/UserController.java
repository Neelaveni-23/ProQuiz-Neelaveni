package com.quiz.springboot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quiz.springboot.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Tag(name = "User Controller", description = "Handles Login and OTP Verification")
public class UserController {

	@Autowired
	private UserService userService;

	@Operation(summary = "Display login page")
	@GetMapping("/")
	public String displayLogin() {
		return "login";
	}

	@PostMapping("/login")
	@Operation(summary = "Verify User Login Credentials")
	public String verifyUser(@RequestParam String email, @RequestParam String password, HttpSession session,
			Model model) {
		if (userService.verifyUser(email, password)) {

			session.setAttribute("email", email);
			return "verifyotp";
		} else {
			model.addAttribute("error", "Invalid Email or Password");
			return "login";
		}
	}

	@PostMapping("/verify-otp")
	@Operation(summary = "Verify OTP Sent to Email")
	public String verifyOtp(@RequestParam String otp, HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		if (email != null && userService.verifyOtp(email, otp)) {
			return "home";
		} else {
			model.addAttribute("error", "Invalid or expired OTP");
			return "verifyotp";
		}
	}

	@PostMapping("/resend-otp")
	@Operation(summary = "Resend OTP to User Email")
	public String resendOtp(@RequestParam String email, Model model) {
		if (userService.resendOtp(email)) {
			model.addAttribute("message", "OTP resent to your email");
			model.addAttribute("email", email);
			return "verifyotp";
		} else {
			model.addAttribute("error", "Unable to resend OTP");
			return "login";
		}
	}
}
