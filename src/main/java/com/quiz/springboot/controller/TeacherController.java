package com.quiz.springboot.controller;

import com.quiz.springboot.entity.MyCourse;
import com.quiz.springboot.entity.MyCourseTopic;
import com.quiz.springboot.entity.MyQuestion;
import com.quiz.springboot.entity.MyQuestionOption;
import com.quiz.springboot.entity.User;
import com.quiz.springboot.service.CourseService;
import com.quiz.springboot.service.QuestionService;
import com.quiz.springboot.service.TopicService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teacher")
@Tag(name = "Teacher Controller", description = "Handles course, question, and Excel upload operations for teachers")

public class TeacherController {
	private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

	@Autowired
	private CourseService courseService;

	@Autowired
	private TopicService topicService;

	@Autowired
	private QuestionService questionService;

	@Operation(summary = "Teacher Home Page with course/topic/questions view")
	@GetMapping("/home")
	public String teacherHome(@RequestParam(required = false) Long courseId,
			@RequestParam(required = false) Long topicId, @RequestParam(required = false) String view,
			@RequestParam(required = false, defaultValue = "0") Integer page, HttpServletRequest request,
			HttpSession session, Model model) {
		logger.info("Teacher home accessed with courseId={}, topicId={}, view={}", courseId, topicId, view);

		User user = (User) session.getAttribute("user");
		if (user == null || !"Teacher".equalsIgnoreCase(user.getUserRole())) {
			return "redirect:/login";
		}

		List<MyCourse> courses = courseService.getAllCourses();
		model.addAttribute("courses", courses);
		/*
		 * MyCourse defaultCourse = courses.stream() .filter(c ->
		 * "Core Java".equalsIgnoreCase(c.getCourseName())) .findFirst() .orElse(null);
		 * model.addAttribute("selectedCourse", defaultCourse);
		 */

		if (courseId == null) {
			for (MyCourse c : courses) {
				if ("Core Java".equalsIgnoreCase(c.getCourseName())) {
					courseId = c.getCourseId();
					break;
				}
			}
		}
		if (courseId == null && !courses.isEmpty()) {
			courseId = courses.get(0).getCourseId();
		}

		MyCourse selectedCourse = courseService.getCourseById(courseId);
		model.addAttribute("selectedCourse", selectedCourse);

		List<MyCourseTopic> topics = topicService.getTopicsByCourse(courseId);
		model.addAttribute("topics", topics);
		model.addAttribute("topicCount", topics.size());

		if ("view".equals(view)) {
			int pageSize = 5;
			PageRequest pageable = PageRequest.of(page, pageSize);
			Page<MyQuestion> questionsPage = questionService.getQuestionsPage(courseId, topicId, pageable);
			model.addAttribute("questionsPage", questionsPage);
			model.addAttribute("selectedTopicId", topicId);
		}
		model.addAttribute("view", view);
		return "teacher-home";
	}

	@Operation(summary = "Display add question form")
	@GetMapping("/add-question")
	public String showAddQuestionForm(HttpSession session, Model model) {
		logger.info("Displaying add-question form");
		User user = (User) session.getAttribute("user");
		if (user == null || !"Teacher".equalsIgnoreCase(user.getUserRole())) {
			return "redirect:/login";
		}
		List<MyCourse> courses = courseService.getAllCourses();
		model.addAttribute("courses", courses);
		if (courses != null && !courses.isEmpty()) {
			Long courseId = courses.get(0).getCourseId();
			List<MyCourseTopic> topics = topicService.getTopicsByCourse(courseId);
			model.addAttribute("topics", topics);
		}

		return "add-question";
	}

	@Operation(summary = "Submit a new question")
	@PostMapping("/add-question")
	public String addQuestion(@RequestParam Long courseId, @RequestParam Long topicId,
			@RequestParam String questionText, @RequestParam String optionA, @RequestParam String optionB,
			@RequestParam String optionC, @RequestParam String optionD, @RequestParam String correctAnswer, Model model,
			HttpSession session) {
		logger.info("Submitting new question for courseId={}, topicId={}", courseId, topicId);

		User user = (User) session.getAttribute("user");
		if (user == null || !"Teacher".equalsIgnoreCase(user.getUserRole())) {
			return "redirect:/login";
		}

		MyQuestion q = new MyQuestion();
		q.setQuestionText(questionText);
		q.setCorrectAnswer(correctAnswer);

		MyCourse course = new MyCourse();
		course.setCourseId(courseId);
		q.setCourse(course);

		MyCourseTopic topic = new MyCourseTopic();
		topic.setTopicId(topicId);
		q.setTopic(topic);

		List<MyQuestionOption> options = new ArrayList<>();
		MyQuestionOption oa = new MyQuestionOption();
		oa.setOptionData(optionA);
		oa.setQuestion(q);
		options.add(oa);

		MyQuestionOption ob = new MyQuestionOption();
		ob.setOptionData(optionB);
		ob.setQuestion(q);
		options.add(ob);

		MyQuestionOption oc = new MyQuestionOption();
		oc.setOptionData(optionC);
		oc.setQuestion(q);
		options.add(oc);
		MyQuestionOption od = new MyQuestionOption();
		od.setOptionData(optionD);
		od.setQuestion(q);
		options.add(od);

		q.setOptions(options);

		questionService.saveSingleQuestion(q);

		return "redirect:/teacher/home";
	}

	@Operation(summary = "View full question detail")
	@GetMapping("/view-full")
	public String viewFullQuestion(@RequestParam Long questionId, HttpSession session, Model model) {

		User user = (User) session.getAttribute("user");
		if (user == null || !"Teacher".equalsIgnoreCase(user.getUserRole())) {
			return "redirect:/login";
		}

		MyQuestion question = questionService.getQuestionById(questionId);
		if (question == null) {
			return "redirect:/teacher/home?view=view";
		}

		model.addAttribute("question", question);
		return "fullview-question";
	}

	@Operation(summary = "Edit question form")
	@GetMapping("/edit-question")
	public String editQuestion(@RequestParam Long questionId, HttpSession session, Model model) {
		logger.info("Editing question with id={}", questionId);
		User user = (User) session.getAttribute("user");
		if (user == null || !"Teacher".equalsIgnoreCase(user.getUserRole())) {
			return "redirect:/login";
		}

		MyQuestion question = questionService.getQuestionById(questionId);
		if (question == null) {
			return "redirect:/teacher/home?view=view";
		}

		model.addAttribute("question", question);
		model.addAttribute("courses", courseService.getAllCourses());
		model.addAttribute("topics", topicService.getTopicsByCourse(question.getCourse().getCourseId()));
		return "edit-question";
	}

	@Operation(summary = "Update existing question")
	@PostMapping("/edit-question")
	public String updateQuestion(@RequestParam Long questionId, @RequestParam Long courseId, @RequestParam Long topicId,
			@RequestParam String questionText, @RequestParam String optionA, @RequestParam String optionB,
			@RequestParam String optionC, @RequestParam String optionD, @RequestParam String correctAnswer,
			HttpSession session) {
		logger.info("Updating question with id={}", questionId);
		User user = (User) session.getAttribute("user");
		if (user == null || !"Teacher".equalsIgnoreCase(user.getUserRole())) {
			return "redirect:/login";
		}

		questionService.updateQuestion(questionId, courseId, topicId, questionText, optionA, optionB, optionC, optionD,
				correctAnswer);

		return "redirect:/teacher/home?view=view";
	}

	@Operation(summary = "Delete question by ID")
	@PostMapping("/delete-question")
	public String deleteQuestion(@RequestParam Long questionId, HttpSession session) {
		logger.info("Deleting question with id={}", questionId);
		User user = (User) session.getAttribute("user");
		if (user == null || !"Teacher".equalsIgnoreCase(user.getUserRole())) {
			return "redirect:/login";
		}

		questionService.deleteQuestion(questionId);

		return "redirect:/teacher/home?view=view";
	}

	@GetMapping("/upload-questions")
	public String showUploadPage(HttpSession session) {
		// Clear previous upload-related session data
		session.removeAttribute("questionCount");
		session.removeAttribute("uploadCompleted");
		session.removeAttribute("uploadStatus");

		return "upload-questions"; // your JSP name (uploadquestions.jsp)
	}

	@Operation(summary = "Handle Excel upload to preview questions")
	@PostMapping("/upload-questions")
	public String handleUpload(@RequestParam MultipartFile file, HttpSession session) {
		logger.info("Uploading Excel file for preview");
		try {
			if (file == null || file.isEmpty()) {
				session.setAttribute("uploadStatus", "No file selected.");

				return "redirect:/teacher/home?view=upload";
			}
			session.setAttribute("uploadedFile", file.getBytes());
			int count = questionService.countQuestionsInExcel(file);
			session.setAttribute("questionCount", count);
			session.setAttribute("uploadCompleted", true);
			session.removeAttribute("uploadStatus");
		} catch (Exception e) {
			session.setAttribute("uploadStatus", "Error reading file: " + e.getMessage());

		}
		return "redirect:/teacher/home?view=upload";
	}

	@Operation(summary = "Confirm and save uploaded Excel questions")
	@PostMapping("/confirm-upload")
	public String confirmUpload(HttpSession session, Model model) {
		logger.info("Confirming Excel question upload");

		try {
			byte[] fileData = (byte[]) session.getAttribute("uploadedFile");

			if (fileData == null) {
				model.addAttribute("message", "No uploaded file found in session.");
				return "teacher-home";
			}

			// TODO: Process the fileData (e.g., parse Excel and save to DB)
			int savedCount = questionService.saveQuestionsFromExcel(fileData);

			session.removeAttribute("uploadedFile");
			session.removeAttribute("questionCount");
			session.removeAttribute("uploadCompleted");

			session.setAttribute("uploadStatus", savedCount + " questions uploaded successfully!");
		} catch (Exception e) {
			model.addAttribute("message", "Error uploading file: " + e.getMessage());
		}

		return "redirect:/teacher/home?view=upload";
	}

	@Operation(summary = "Reset uploaded question session")
	@PostMapping("/reset-upload")
	public String resetUpload(HttpSession session) {
		logger.info("Resetting upload session");
		session.removeAttribute("uploadCompleted");
		session.removeAttribute("questionCount");
		session.removeAttribute("uploadStatus");
		session.removeAttribute("uploadedFile");
		return "redirect:/teacher/home?view=upload";
	}

	@Operation(summary = "Logout teacher user")
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		logger.info("Teacher logout invoked");
		session.invalidate();
		return "redirect:/login";
	}

}