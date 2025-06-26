package com.quiz.springboot.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.quiz.springboot.User;

@Repository
@Transactional
public class UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public User verifyUser(String email, String password) {
		try {
			return entityManager.createQuery("FROM User WHERE email = :email AND password = :password", User.class)
					.setParameter("email", email).setParameter("password", password).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean verifyOtp(String email, int otp) {

		try {
			User user = entityManager.createQuery("FROM User WHERE email = :email AND otp = :otp", User.class)
					.setParameter("email", email).setParameter("otp", otp).getSingleResult();
			return user != null;
		} catch (Exception e) {
			return false;
		}
	}

	public void updateOtp(String email, int otp) {
		entityManager.createQuery("UPDATE User SET otp = :otp WHERE email = :email").setParameter("otp", otp)
				.setParameter("email", email).executeUpdate();
	}

	public User getUserByEmail(String email) {
		try {
			return entityManager.createQuery("FROM User WHERE email = :email", User.class).setParameter("email", email)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}