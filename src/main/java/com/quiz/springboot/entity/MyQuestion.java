package com.quiz.springboot.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "myquestions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyQuestion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private Long questionId;

	@Column(name = "question_data")
	private String questionText;

	@Column(name = "correct_answer")
	private String correctAnswer;

	@ManyToOne
	@JoinColumn(name = "course_id")
	@ToString.Exclude
	private MyCourse course;

	@ManyToOne
	@JoinColumn(name = "topic_id")
	@ToString.Exclude
	private MyCourseTopic topic;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<MyQuestionOption> options;;

}