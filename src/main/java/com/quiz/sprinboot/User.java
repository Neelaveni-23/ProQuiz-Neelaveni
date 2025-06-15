package com.quiz.sprinboot;

import javax.persistence.*;

@Entity
@Table(name = "myusers")
public class User {
	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	@Column(name = "fullName")
	private String fullName;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private Long phone;

	@Column(name = "password")
	private String password;

	@Column(name = "otp")
	private Integer otp;

	@Column(name = "status")
	private String status;

	public User() {
	}

	public User(Integer userId, String fullName, String email, Long phone, String password, Integer otp,
			String status) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.otp = otp;
		this.status = status;
	}

	public User(String fullName, String email, Long phone, String password, Integer otp, String status) {
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.otp = otp;
		this.status = status;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", fullName=" + fullName + ", email=" + email + ", phone=" + phone
				+ ", password=" + password + ", otp=" + otp + ", status=" + status + "]";
	}

}
