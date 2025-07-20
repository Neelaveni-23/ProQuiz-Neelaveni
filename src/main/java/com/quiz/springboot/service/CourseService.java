package com.quiz.springboot.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.springboot.dao.CourseDAO;
import com.quiz.springboot.entity.MyCourse;

@Service
public class CourseService {
	private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

	@Autowired
	private CourseDAO courseDao;

	public List<MyCourse> getAllCourses() {
		logger.info("Fetching all courses from the database");

		List<MyCourse> courses = courseDao.findAll();
		if (courses != null) {
			logger.debug("Fetched {} courses", courses.size());
		} else {
			logger.warn("No courses found or courseRepo returned null");
		}

		return courses;
	}

	public MyCourse getCourseById(Long id) {
		logger.info("Fetching course by ID: {}", id);
		return courseDao.findById(id).orElse(null);
	}
}
