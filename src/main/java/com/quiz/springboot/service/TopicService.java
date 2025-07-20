package com.quiz.springboot.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.springboot.dao.TopicDAO;
import com.quiz.springboot.entity.MyCourseTopic;

@Service
public class TopicService {
	private static final Logger logger = LoggerFactory.getLogger(TopicService.class);
	@Autowired
	private TopicDAO topicDao;

	public List<MyCourseTopic> getTopicsByCourse(Long courseId) {
		logger.info("Fetching topics for courseId: {}", courseId);
		List<MyCourseTopic> topics = topicDao.findByCourse_CourseId(courseId);

		for (MyCourseTopic topic : topics) {
			if (topic.getQuestions() != null) {
				topic.setNoOfQuestions(topic.getQuestions().size());
			} else {
				topic.setNoOfQuestions(0);
			}
		}

		return topics;
	}

	public void incrementQuestionCount(Long topicId) {
		logger.info("Incrementing question count for topicId: {}", topicId);

		MyCourseTopic topic = topicDao.findById(topicId).orElseThrow(() -> {
			logger.error("Topic not found with id: {}", topicId);
			return new RuntimeException("Topic not found");
		});
		if (topic.getNoOfQuestions() == null) {
			topic.setNoOfQuestions(1);
		} else {
			topic.setNoOfQuestions(topic.getNoOfQuestions() + 1);
		}

		topicDao.save(topic);
	}

	public void decrementQuestionCount(Long topicId) {
		System.out.println(">>>> Decrementing topic count for topicId=" + topicId);

		MyCourseTopic topic = topicDao.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));

		if (topic.getNoOfQuestions() == null || topic.getNoOfQuestions() <= 1) {
			topic.setNoOfQuestions(0);
		} else {
			topic.setNoOfQuestions(topic.getNoOfQuestions() - 1);
		}
		topicDao.save(topic);
		logger.debug("Updated topic question count to {} for topicId: {}", topic.getNoOfQuestions(), topicId);
	}

}