package com.quiz.springboot;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "myusers")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

	public User(String fullName, String email, Long phone, String password, Integer otp, String status) {
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.otp = otp;
		this.status = status;
	}
}
