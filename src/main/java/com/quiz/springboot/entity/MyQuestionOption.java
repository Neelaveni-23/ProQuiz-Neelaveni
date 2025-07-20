package com.quiz.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "myquestion_options")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyQuestionOption {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "option_id")
	private Long optionId;

	@Column(name = "option_data")
	private String optionData;

	@ManyToOne
	@JoinColumn(name = "question_id")
	@ToString.Exclude
	private MyQuestion question;

}
