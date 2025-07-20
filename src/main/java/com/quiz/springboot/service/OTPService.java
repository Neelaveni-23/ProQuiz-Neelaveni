package com.quiz.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class OTPService {
	private static final Logger logger = LoggerFactory.getLogger(OTPService.class);

	public int generateOTP() {
		int otp = (int) (ThreadLocalRandom.current().nextDouble() * 900000) + 100000;
		logger.info("Generated OTP: {}", otp);
		return otp;
	}
}
