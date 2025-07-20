package com.quiz.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "myusers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	@Column(name = "fullname")
	private String fullName;

	@Column(name = "email_id")
	private String email;

	@Column(name = "phone")
	private Long phone;

	@Column(name = "password")
	private String password;

	@Column(name = "otp")
	private Integer otp;

	@Column(name = "status")
	private String status;
	@Column(name = "user_role")
	private String userRole;

	public User(String fullName, String email, Long phone, String password, Integer otp, String status,
			String userRole) {
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.otp = otp;
		this.status = status;
		this.userRole = userRole;
	}

}
