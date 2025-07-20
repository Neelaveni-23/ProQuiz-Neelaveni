package com.quiz.springboot.dao;

import com.quiz.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {

	User findByEmailAndPassword(String email, String password);

	User findByEmailAndOtp(String email, int otp);

	User findByEmail(String email);

	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.otp = :otp WHERE u.email = :email")
	void updateOtpByEmail(@Param("email") String email, @Param("otp") int otp);
}