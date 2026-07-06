INSERT INTO Student(name, dob, email, sex, phone, role, password)
VALUES
    ('Nguyễn Văn An', '2001-05-10', 'student1@gmail.com', true, '0901111111', 'STUDENT', ''),
    ('Trần Thị Bình', '2002-03-15', 'student2@gmail.com', false, '0902222222', 'STUDENT', ''),
    ('Lê Văn Cường', '2000-11-20', 'student3@gmail.com', true, '0903333333', 'STUDENT', ''),
    ('Phạm Thị Dung', '2001-08-18', 'student4@gmail.com', false, '0904444444', 'STUDENT', ''),
    ('Hoàng Minh Đức', '1999-12-30', 'student5@gmail.com', true, '0905555555', 'STUDENT', ''),
    ('Vũ Thu Hà', '2003-01-12', 'student6@gmail.com', false, '0906666666', 'STUDENT', ''),
    ('Đỗ Quang Huy', '2002-06-08', 'student7@gmail.com', true, '0907777777', 'STUDENT', ''),
    ('Bùi Ngọc Lan', '2001-09-21', 'student8@gmail.com', false, '0908888888', 'STUDENT', ''),
    ('Đặng Quốc Nam', '2000-04-14', 'student9@gmail.com', true, '0909999999', 'STUDENT', ''),
    ('Ngô Thanh Phương', '2002-10-01', 'student10@gmail.com', false, '0910000000', 'STUDENT', '');

INSERT INTO Course(name, duration, instructor)
VALUES
    ('Java Core', 30, 'Nguyễn Văn A'),
    ('Java OOP', 25, 'Trần Văn B'),
    ('Spring Boot', 40, 'Lê Văn C'),
    ('SQL Cơ Bản', 20, 'Phạm Văn D'),
    ('PostgreSQL', 25, 'Hoàng Văn E'),
    ('HTML CSS', 15, 'Nguyễn Thị F'),
    ('JavaScript', 35, 'Trần Thị G'),
    ('ReactJS', 40, 'Lê Thị H'),
    ('Data Structure', 45, 'Phạm Văn I'),
    ('Algorithm', 50, 'Hoàng Thị K');

INSERT INTO Enrollment(student_id, course_id, status)
VALUES
    (2,1,'WAITING'),
    (3,2,'CONFIRM'),
    (2,2,'CONFIRM'),
    (4,2,'CONFIRM'),
    (5,2,'CONFIRM'),
    (6,2,'CONFIRM'),
    (7,2,'CONFIRM'),
    (8,2,'CONFIRM'),
    (9,2,'CONFIRM'),
    (10,2,'CONFIRM'),
    (11,2,'CONFIRM'),
    (4,3,'WAITING'),
    (5,4,'DENIED'),
    (6,5,'CONFIRM'),
    (7,6,'WAITING'),
    (8,7,'CANCEL'),
    (9,8,'WAITING'),
    (10,9,'CONFIRM'),
    (11,10,'WAITING');