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
@Table(name = "mycourse_topics")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyCourseTopic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "topic_id")
	private Long topicId;

	@Column(name = "topic_name")
	private String topicName;

	@Column(name = "no_of_questions")
	private Integer noOfQuestions;

	@ManyToOne
	@JoinColumn(name = "course_id")
	@ToString.Exclude
	private MyCourse course;

	@OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<MyQuestion> questions;

}