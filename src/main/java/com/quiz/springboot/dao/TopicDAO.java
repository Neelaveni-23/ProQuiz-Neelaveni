package com.quiz.springboot.dao;

import com.quiz.springboot.entity.MyCourseTopic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicDAO extends JpaRepository<MyCourseTopic, Long> {
	List<MyCourseTopic> findByCourse_CourseId(Long courseId);

	MyCourseTopic findByTopicName(String topicName);

}
