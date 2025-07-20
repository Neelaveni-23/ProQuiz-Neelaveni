package com.quiz.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MyBootApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MyBootApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MyBootApplication.class);
	}
}

//com.quiz.sprinboot.controller -> all ur controllers
//com.quiz.sprinboot.service -> all ur service
//com.quiz.sprinboot.dao -> all ur daos
//implement swagger
//update the java <!--properties-->
// add some background color to homepage
// after login display some message. login successfully done. dash board etc.
//lombok