use challengedb;

CREATE TABLE student
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_student PRIMARY KEY (id),
    CONSTRAINT name_unique UNIQUE (name)
);

CREATE TABLE course
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_course PRIMARY KEY (id)
);

CREATE TABLE course_registration
(
    id         INT AUTO_INCREMENT NOT NULL,
    student_id BIGINT             NULL,
    course_id  BIGINT             NULL,
    score      DOUBLE PRECISION(4,2)       NULL,
    CONSTRAINT pk_courseregistration PRIMARY KEY (id)
);

ALTER TABLE course_registration
    ADD CONSTRAINT FK_COURSEREGISTRATION_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id);

ALTER TABLE course_registration
    ADD CONSTRAINT FK_COURSEREGISTRATION_ON_STUDENT FOREIGN KEY (student_id) REFERENCES student (id);