package com.quiz.springboot.service;

import org.springframework.stereotype.Service;

@Service
public class OTPService {

	public int generateOTP() {
		return (int) (Math.random() * 900000) + 100000;
	}
}
