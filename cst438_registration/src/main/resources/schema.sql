 create table course(`year` integer NOT NULL,
    semester VARCHAR(10) NOT NULL,
    course_id integer NOT NULL,
    section integer NOT NULL,
    title VARCHAR(255) NOT NULL,
    times VARCHAR(50) DEFAULT NULL,
    building VARCHAR(20) DEFAULT NULL,
    room VARCHAR(20) DEFAULT NULL,
    instructor VARCHAR(50) DEFAULT NULL,
    start_date DATE DEFAULT NULL,
    end_date DATE DEFAULT NULL,
    PRIMARY KEY (course_id)
);

create table student(
  student_id integer  NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  status VARCHAR(255) DEFAULT NULL,
  status_code integer  NOT NULL,
  PRIMARY KEY (student_id)
);

create table enrollment(
  enrollment_id integer NOT NULL AUTO_INCREMENT,
  student_id integer  NOT NULL,
  `year` integer NOT NULL,
  semester VARCHAR(10) NOT NULL,
  course_id integer  NOT NULL,
  course_grade VARCHAR(5) DEFAULT NULL,
  PRIMARY KEY (enrollment_id),
  FOREIGN KEY (course_id) REFERENCES course (course_id) on delete cascade, 
  FOREIGN KEY (student_id) REFERENCES student (student_id) on delete cascade 
);
