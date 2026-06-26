CREATE DATABASE QLKH;

CREATE TABLE Student(
    id SERIAL PRIMARY KEY ,
    name varchar(100) NOT NULL ,
    dob DATE NOT NULL ,
    email varchar(100) NOT NULL UNIQUE ,
    sex BIT NOT NULL ,
    phone varchar(20) NULL ,
    role varchar(20) CHECK ( role in ('ADMIN' , 'STUDENT') ),
    password varchar(255) NOT NULL ,
    create_at DATE default current_date
);

Create TABLE Course(
    id Serial PRIMARY KEY ,
    name varchar(100) NOT NULL ,
    duration int not null ,
    instructor varchar(100) not null ,
    create_at date default  current_date
);

CREATE TABLE Enrollment(
    id SERIAL Primary Key ,
    student_id int NOT NULL REFERENCES Student(id) On DELETE CASCADE ,
    course_id int NOT NULL REFERENCES Course(id) On DELETE CASCADE ,
    registered_at TIMESTAMP default current_timestamp,
    status varchar(20) CHECK ( status in ('WAITING' , 'DENIED' , 'CANCEL' , 'CONFIRM') ) default 'WAITING',
    UNIQUE(student_id, course_id)
);

DROP TABLE Enrollment, Student , Course;
