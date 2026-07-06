CREATE DATABASE QLKH;

CREATE TABLE Student(
    id SERIAL PRIMARY KEY ,
    name varchar(100) NOT NULL ,
    dob DATE NOT NULL ,
    email varchar(100) NOT NULL UNIQUE ,
    sex BIT(1) NOT NULL ,
    phone varchar(20) NULL ,
    role varchar(20) CHECK ( role in ('ADMIN' , 'STUDENT') ),
    password varchar(255) NULL ,
    create_at DATE default current_date
);
ALTER TABLE student
    ALTER COLUMN sex TYPE BOOLEAN
        USING (sex::int::boolean);

Create TABLE Course(
    id Serial PRIMARY KEY ,
    name varchar(100) NOT NULL ,
    duration int not null CHECK(duration>0),
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
INSERT INTO student(name, dob, email, sex, phone, role, password ,create_at)
VALUES ('ADMIN' , '14/01/2002', 'khai5108@gmail.com', '1', '0856139465', 'ADMIN', crypt('123456', gen_salt('bf')) , '28/06/2026');

DROP TABLE enrollment , student , course;
DROP TaBLE enrollment;