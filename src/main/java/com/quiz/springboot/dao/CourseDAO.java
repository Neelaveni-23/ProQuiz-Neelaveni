package com.quiz.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.springboot.entity.MyCourse;

public interface CourseDAO extends JpaRepository<MyCourse, Long> {
	MyCourse findByCourseName(String courseName);

}
