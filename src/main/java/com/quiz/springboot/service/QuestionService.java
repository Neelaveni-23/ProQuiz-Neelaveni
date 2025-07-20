package com.quiz.springboot.service;

import com.quiz.springboot.dao.CourseDAO;
import com.quiz.springboot.dao.QuestionDAO;
import com.quiz.springboot.dao.TopicDAO;
import com.quiz.springboot.entity.*;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
	private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
	@Autowired
	private QuestionDAO questionRepo;
	@Autowired
	private TopicService topicService;
	@Autowired
	private CourseDAO courseRepo;
	@Autowired
	private TopicDAO topicRepo;

	public void saveQuestion(MyQuestion question) {
		logger.info("Saving question: {}", question.getQuestionText());
		questionRepo.save(question);
	}

	public List<MyQuestion> getQuestions(Long courseId, Long topicId) {
		logger.info("Fetching questions for courseId: {}, topicId: {}", courseId, topicId);
		return questionRepo.findByTopic_TopicId(topicId);
	}

	public MyQuestion getQuestionById(Long questionId) {
		logger.info("Fetching question by ID: {}", questionId);
		return questionRepo.findById(questionId).orElse(null);
	}

	@Transactional
	public void updateQuestion(Long questionId, Long courseId, Long topicId, String questionText, String optionA,
			String optionB, String optionC, String optionD, String correctAnswer) {
		logger.info("Updating question ID: {}", questionId);
		MyQuestion q = questionRepo.findById(questionId).orElse(null);
		if (q == null) {
			logger.error("Question not found for ID: {}", questionId);
			throw new RuntimeException("Question not found");
		}

		q.setQuestionText(questionText);
		q.setCorrectAnswer(correctAnswer);

		// Update course
		MyCourse course = new MyCourse();
		course.setCourseId(courseId);
		q.setCourse(course);

		// Update topic
		MyCourseTopic topic = new MyCourseTopic();
		topic.setTopicId(topicId);
		q.setTopic(topic);

		// Update options
		List<MyQuestionOption> options = q.getOptions();
		if (options != null && options.size() == 4) {
			options.get(0).setOptionData(optionA);
			options.get(1).setOptionData(optionB);
			options.get(2).setOptionData(optionC);
			options.get(3).setOptionData(optionD);
		}

		// Save updated question
		questionRepo.save(q);
		logger.info("Question ID {} updated successfully", questionId);
	}

	public Page<MyQuestion> getQuestionsPage(Long courseId, Long topicId, Pageable pageable) {
		logger.info("Paginating questions for courseId: {}, topicId: {}", courseId, topicId);
		MyCourse course = new MyCourse();
		course.setCourseId(courseId);

		if (topicId != null) {
			MyCourseTopic topic = new MyCourseTopic();
			topic.setTopicId(topicId);
			return questionRepo.findByCourseAndTopic(course, topic, pageable);
		} else {
			return questionRepo.findByCourse(course, pageable);
		}
	}

	@Transactional
	public void saveSingleQuestion(MyQuestion question) {
		logger.info("Saving single question: {}", question.getQuestionText());
		questionRepo.save(question);
		topicService.incrementQuestionCount(question.getTopic().getTopicId());
	}

	@Transactional
	public void saveMultipleQuestions(List<MyQuestion> questions) {
		logger.info("Saving multiple questions: {}", questions.size());
		for (MyQuestion q : questions) {
			questionRepo.save(q);
			topicService.incrementQuestionCount(q.getTopic().getTopicId());
		}
	}

	@Transactional
	public void deleteQuestion(Long questionId) {
		logger.info("Deleting question ID: {}", questionId);
		MyQuestion question = questionRepo.findById(questionId).orElseThrow(() -> {
			logger.error("Question not found with ID: {}", questionId);
			return new RuntimeException("Question not found");
		});
		Long topicId = question.getTopic().getTopicId();
		questionRepo.delete(question);
		topicService.decrementQuestionCount(topicId);
		logger.info("Question deleted and topic count updated for topicId: {}", topicId);
	}

	@Transactional
	public int importQuestionsFromExcel(InputStream inputStream) {
		int savedCount = 0;

		try (Workbook workbook = new XSSFWorkbook(inputStream)) {
			Sheet sheet = workbook.getSheetAt(0);

			List<MyQuestion> questionBatch = new ArrayList<>();
			logger.info("Started importing questions from Excel...");

			for (Row row : sheet) {
				if (row.getRowNum() == 0)
					continue; // Skip header

				String courseName = getCellValue(row.getCell(0));
				String topicName = getCellValue(row.getCell(1));
				String questionText = getCellValue(row.getCell(2));
				String optionA = getCellValue(row.getCell(3));
				String optionB = getCellValue(row.getCell(4));
				String optionC = getCellValue(row.getCell(5));
				String optionD = getCellValue(row.getCell(6));
				String correctAnswer = getCellValue(row.getCell(7));

				MyCourse course = courseRepo.findByCourseName(courseName);
				if (course == null)
					throw new RuntimeException("Course not found: " + courseName);

				MyCourseTopic topic = topicRepo.findByTopicName(topicName);
				if (topic == null)
					throw new RuntimeException("Topic not found: " + topicName);

				MyQuestion question = new MyQuestion();
				question.setQuestionText(questionText);
				question.setCorrectAnswer(correctAnswer);
				question.setCourse(course);
				question.setTopic(topic);

				List<MyQuestionOption> options = new ArrayList<>();
				options.add(createOption(optionA, question));
				options.add(createOption(optionB, question));
				options.add(createOption(optionC, question));
				options.add(createOption(optionD, question));
				question.setOptions(options);

				questionBatch.add(question);
			}

			questionRepo.saveAll(questionBatch);
			savedCount = questionBatch.size();

			for (MyQuestion q : questionBatch) {
				topicService.incrementQuestionCount(q.getTopic().getTopicId());
			}

			logger.info("Finished importing and updating topic question counts.");
			return savedCount;

		} catch (Exception e) {
			logger.error("Failed to import questions from Excel: {}", e.getMessage(), e);
			throw new RuntimeException("Failed to import questions from Excel: " + e.getMessage(), e);
		}
	}

	private MyQuestionOption createOption(String data, MyQuestion question) {
		MyQuestionOption opt = new MyQuestionOption();
		opt.setOptionData(data);
		opt.setQuestion(question);
		return opt;
	}

	public int saveQuestionsFromExcel(byte[] fileData) {
		try (InputStream inputStream = new ByteArrayInputStream(fileData)) {
			return importQuestionsFromExcel(inputStream);
		} catch (IOException e) {
			logger.error("Error reading Excel input stream", e);
			throw new RuntimeException("Failed to read Excel data", e);
		}
	}

	private String getCellValue(Cell cell) {
		if (cell == null)
			return "";
		if (cell.getCellType() == CellType.STRING)
			return cell.getStringCellValue().trim();
		if (cell.getCellType() == CellType.NUMERIC)
			return String.valueOf((long) cell.getNumericCellValue());
		return "";
	}

	public int countQuestionsInExcel(MultipartFile file) {
		int count = 0;
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			for (Row row : sheet) {
				if (row.getRowNum() == 0) {
					continue; // skip header
				}
				if (row.getCell(0) != null && !row.getCell(0).getStringCellValue().trim().isEmpty()) {
					count++;
				}
			}
			logger.info("Total questions found in Excel: {}", count);
		} catch (Exception e) {
			logger.error("Error counting questions in Excel: {}", e.getMessage(), e);
			e.printStackTrace();
		}
		return count;
	}
}