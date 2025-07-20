package com.quiz.springboot.dao;

import com.quiz.springboot.entity.*;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Transactional
public interface QuestionDAO extends JpaRepository<MyQuestion, Long> {
	List<MyQuestion> findByTopic_TopicId(Long topicId);

	Page<MyQuestion> findByCourse(MyCourse course, Pageable pageable);

	Page<MyQuestion> findByCourseAndTopic(MyCourse course, MyCourseTopic topic, Pageable pageable);
}