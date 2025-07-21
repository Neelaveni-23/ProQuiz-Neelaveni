# Table Creation
README.txt
-----------------
CREATE TABLE myusers (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(255),
    email_id VARCHAR(255),
    phone BIGINT,
    password VARCHAR(255),
    otp INT,
    status VARCHAR(255),
    user_role VARCHAR(255)
);

CREATE TABLE mycourses (
    course_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL
);

CREATE TABLE mycourse_topics (
    topic_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    topic_name VARCHAR(255) NOT NULL,
    no_of_questions INT,
    course_id BIGINT,
    FOREIGN KEY (course_id) REFERENCES mycourses(course_id) ON DELETE CASCADE
);

CREATE TABLE myquestions (
    question_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_data VARCHAR(1000),
    correct_answer VARCHAR(255),
    course_id BIGINT,
    topic_id BIGINT,
    CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES mycourses(course_id) ON DELETE CASCADE,
    CONSTRAINT fk_topic_id FOREIGN KEY (topic_id) REFERENCES mytopics(topic_id) ON DELETE CASCADE
);


CREATE TABLE myquestion_options (
    option_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    option_data VARCHAR(255),
    question_id BIGINT,
    CONSTRAINT fk_question_id FOREIGN KEY (question_id) REFERENCES myquestions(question_id) ON DELETE CASCADE
);

# Insert Query
insert into myusers(user_id,email_id,fullname,otp,password,phone,status,user_role) 
values(101,'neelimadande23.mca@gmail.com','Neelaveni',734593,'neelima123',9052260983,'Active',teacher);
insert into mycourses(course_id,course_name) values(101,'Core Java');
insert into mycourse_tooics(course_id,topics_id,topic_name,no_of_questions)
values(101,1,'Data Types and variables',0);


# To check dependency tree
mvn dependency:tree > proquiz.txt


