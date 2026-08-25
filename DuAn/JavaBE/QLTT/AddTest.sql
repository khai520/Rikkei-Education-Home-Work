-- =========================================================
-- STUDENTS USERS
-- student02 -> student10
-- UserID dự kiến: 4 -> 12
-- Password: 123456
-- =========================================================

INSERT INTO users
(
    username,
    password_hash,
    full_name,
    email,
    phone_number,
    role,
    is_active,
    created_at,
    updated_at
)
VALUES
    (
        'student02',
        '$2a$10$8/lBhZC2ce9iNqOMRncW0ehv3sPPOfgRBvR98YVuix8StDPNDJe7O',
        'Trần Văn Bình',
        'student02@gmail.com',
        '0901000002',
        'STUDENT',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'student03',
        '$2a$10$8/lBhZC2ce9iNqOMRncW0ehv3sPPOfgRBvR98YVuix8StDPNDJe7O',
        'Lê Minh Châu',
        'student03@gmail.com',
        '0901000003',
        'STUDENT',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'student04',
        '$2a$10$8/lBhZC2ce9iNqOMRncW0ehv3sPPOfgRBvR98YVuix8StDPNDJe7O',
        'Phạm Quốc Dũng',
        'student04@gmail.com',
        '0901000004',
        'STUDENT',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'student05',
        '$2a$10$8/lBhZC2ce9iNqOMRncW0ehv3sPPOfgRBvR98YVuix8StDPNDJe7O',
        'Hoàng Minh Đức',
        'student05@gmail.com',
        '0901000005',
        'STUDENT',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'student06',
        '$2a$10$8/lBhZC2ce9iNqOMRncW0ehv3sPPOfgRBvR98YVuix8StDPNDJe7O',
        'Vũ Hoàng Giang',
        'student06@gmail.com',
        '0901000006',
        'STUDENT',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'student07',
        '$2a$10$8/lBhZC2ce9iNqOMRncW0ehv3sPPOfgRBvR98YVuix8StDPNDJe7O',
        'Đặng Tuấn Hùng',
        'student07@gmail.com',
        '0901000007',
        'STUDENT',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'student08',
        '$2a$10$8/lBhZC2ce9iNqOMRncW0ehv3sPPOfgRBvR98YVuix8StDPNDJe7O',
        'Bùi Thị Lan',
        'student08@gmail.com',
        '0901000008',
        'STUDENT',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'student09',
        '$2a$10$8/lBhZC2ce9iNqOMRncW0ehv3sPPOfgRBvR98YVuix8StDPNDJe7O',
        'Nguyễn Thị Mai',
        'student09@gmail.com',
        '0901000009',
        'STUDENT',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'student10',
        '$2a$10$8/lBhZC2ce9iNqOMRncW0ehv3sPPOfgRBvR98YVuix8StDPNDJe7O',
        'Trần Thị Ngọc',
        'student10@gmail.com',
        '0901000010',
        'STUDENT',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );
-- =========================================================
-- STUDENTS
-- =========================================================

INSERT INTO students
(
    student_id,
    student_code,
    major,
    class,
    date_of_birth,
    address,
    created_at,
    updated_at
)
SELECT
    u.user_id,
    v.student_code,
    v.major,
    v.class,
    v.date_of_birth,
    v.address,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM
    (
        VALUES
            (
                'student02',
                'K20CNTT002',
                'Công nghệ thông tin',
                'K20CNTT01',
                DATE '2002-05-20',
                'Hải Phòng'
            ),
            (
                'student03',
                'K20CNTT003',
                'Công nghệ thông tin',
                'K20CNTT02',
                DATE '2002-07-11',
                'Hà Nội'
            ),
            (
                'student04',
                'K20CNTT004',
                'Kỹ thuật phần mềm',
                'K20KTPM01',
                DATE '2002-01-25',
                'Nam Định'
            ),
            (
                'student05',
                'K20CNTT005',
                'Kỹ thuật phần mềm',
                'K20KTPM01',
                DATE '2002-09-10',
                'Bắc Ninh'
            ),
            (
                'student06',
                'K20CNTT006',
                'Khoa học máy tính',
                'K20KHMT01',
                DATE '2002-11-02',
                'Hà Nội'
            ),
            (
                'student07',
                'K20CNTT007',
                'Khoa học máy tính',
                'K20KHMT01',
                DATE '2002-04-18',
                'Thái Bình'
            ),
            (
                'student08',
                'K20CNTT008',
                'Hệ thống thông tin',
                'K20HTTT01',
                DATE '2002-06-30',
                'Hà Nam'
            ),
            (
                'student09',
                'K20CNTT009',
                'Hệ thống thông tin',
                'K20HTTT01',
                DATE '2002-08-21',
                'Hà Nội'
            ),
            (
                'student10',
                'K20CNTT010',
                'Công nghệ thông tin',
                'K20CNTT02',
                DATE '2002-12-05',
                'Hưng Yên'
            )
    ) AS v(username, student_code, major, class, date_of_birth, address)
        JOIN users u
             ON u.username = v.username
WHERE u.role = 'STUDENT';
-- =========================================================
-- MENTOR
-- mentor01 phải tồn tại trong users
-- =========================================================

INSERT INTO mentors
(
    mentor_id,
    department,
    academic_rank,
    created_at,
    updated_at
)
SELECT
    u.user_id,
    'Khoa Công nghệ thông tin',
    'ThS',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM users u
WHERE u.username = 'mentor'
  AND u.role = 'MENTOR';
-- =========================================================
-- INTERNSHIP PHASES
-- =========================================================

INSERT INTO internship_phases
(
    phase_name,
    start_date,
    end_date,
    description,
    created_at,
    updated_at
)
VALUES
    (
        'Thực tập cơ sở',
        DATE '2026-09-01',
        DATE '2026-10-31',
        'Giai đoạn thực tập cơ sở dành cho sinh viên.',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Thực tập tốt nghiệp',
        DATE '2026-11-01',
        DATE '2027-02-28',
        'Giai đoạn thực tập tốt nghiệp và hoàn thành khóa luận.',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );
-- =========================================================
-- EVALUATION CRITERIA
-- =========================================================

INSERT INTO evaluation_criteria
(
    criterion_name,
    description,
    max_score,
    created_at,
    updated_at
)
VALUES
    (
        'Thái độ làm việc',
        'Đánh giá thái độ, tinh thần và trách nhiệm trong quá trình thực tập.',
        10.00,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Kiến thức chuyên môn',
        'Đánh giá kiến thức chuyên môn áp dụng vào công việc.',
        10.00,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Kỹ năng thực hành',
        'Đánh giá khả năng thực hiện các nhiệm vụ được giao.',
        10.00,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Khả năng làm việc nhóm',
        'Đánh giá khả năng phối hợp và làm việc với các thành viên khác.',
        10.00,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Tiến độ công việc',
        'Đánh giá khả năng hoàn thành công việc đúng thời hạn.',
        10.00,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );
-- =========================================================
-- ASSESSMENT ROUNDS
-- =========================================================

INSERT INTO assessment_rounds
(
    phase_id,
    round_name,
    start_date,
    end_date,
    description,
    is_active,
    created_at,
    updated_at
)
SELECT
    p.phase_id,
    r.round_name,
    r.start_date,
    r.end_date,
    r.description,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM
    (
        VALUES
            (
                'Thực tập cơ sở',
                'Đánh giá giữa kỳ',
                DATE '2026-09-25',
                DATE '2026-10-05',
                'Đánh giá kết quả giữa giai đoạn thực tập cơ sở.'
            ),
            (
                'Thực tập cơ sở',
                'Đánh giá cuối kỳ',
                DATE '2026-10-20',
                DATE '2026-10-31',
                'Đánh giá kết quả cuối giai đoạn thực tập cơ sở.'
            ),
            (
                'Thực tập tốt nghiệp',
                'Đánh giá giữa kỳ',
                DATE '2026-12-15',
                DATE '2026-12-25',
                'Đánh giá giữa kỳ thực tập tốt nghiệp.'
            ),
            (
                'Thực tập tốt nghiệp',
                'Đánh giá cuối kỳ',
                DATE '2027-02-15',
                DATE '2027-02-28',
                'Đánh giá kết quả cuối kỳ thực tập tốt nghiệp.'
            )
    ) AS r(
           phase_name,
           round_name,
           start_date,
           end_date,
           description
        )
        JOIN internship_phases p
             ON p.phase_name = r.phase_name;
-- =========================================================
-- ROUND CRITERIA
-- =========================================================

INSERT INTO round_criteria
(
    round_id,
    criterion_id,
    weight,
    created_at,
    updated_at
)
SELECT
    ar.round_id,
    ec.criterion_id,
    rc.weight,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM
    (
        VALUES
            ('Đánh giá giữa kỳ', 'Thực tập cơ sở', 'Thái độ làm việc', 0.20),
            ('Đánh giá giữa kỳ', 'Thực tập cơ sở', 'Kiến thức chuyên môn', 0.25),
            ('Đánh giá giữa kỳ', 'Thực tập cơ sở', 'Kỹ năng thực hành', 0.25),
            ('Đánh giá giữa kỳ', 'Thực tập cơ sở', 'Khả năng làm việc nhóm', 0.15),
            ('Đánh giá giữa kỳ', 'Thực tập cơ sở', 'Tiến độ công việc', 0.15),

            ('Đánh giá cuối kỳ', 'Thực tập cơ sở', 'Thái độ làm việc', 0.20),
            ('Đánh giá cuối kỳ', 'Thực tập cơ sở', 'Kiến thức chuyên môn', 0.25),
            ('Đánh giá cuối kỳ', 'Thực tập cơ sở', 'Kỹ năng thực hành', 0.25),
            ('Đánh giá cuối kỳ', 'Thực tập cơ sở', 'Khả năng làm việc nhóm', 0.15),
            ('Đánh giá cuối kỳ', 'Thực tập cơ sở', 'Tiến độ công việc', 0.15),

            ('Đánh giá giữa kỳ', 'Thực tập tốt nghiệp', 'Thái độ làm việc', 0.20),
            ('Đánh giá giữa kỳ', 'Thực tập tốt nghiệp', 'Kiến thức chuyên môn', 0.25),
            ('Đánh giá giữa kỳ', 'Thực tập tốt nghiệp', 'Kỹ năng thực hành', 0.25),
            ('Đánh giá giữa kỳ', 'Thực tập tốt nghiệp', 'Khả năng làm việc nhóm', 0.15),
            ('Đánh giá giữa kỳ', 'Thực tập tốt nghiệp', 'Tiến độ công việc', 0.15),

            ('Đánh giá cuối kỳ', 'Thực tập tốt nghiệp', 'Thái độ làm việc', 0.20),
            ('Đánh giá cuối kỳ', 'Thực tập tốt nghiệp', 'Kiến thức chuyên môn', 0.25),
            ('Đánh giá cuối kỳ', 'Thực tập tốt nghiệp', 'Kỹ năng thực hành', 0.25),
            ('Đánh giá cuối kỳ', 'Thực tập tốt nghiệp', 'Khả năng làm việc nhóm', 0.15),
            ('Đánh giá cuối kỳ', 'Thực tập tốt nghiệp', 'Tiến độ công việc', 0.15)
    ) AS rc(
            round_name,
            phase_name,
            criterion_name,
            weight
        )
        JOIN assessment_rounds ar
             ON ar.round_name = rc.round_name
        JOIN internship_phases p
             ON p.phase_id = ar.phase_id
                 AND p.phase_name = rc.phase_name
        JOIN evaluation_criteria ec
             ON ec.criterion_name = rc.criterion_name;
-- =========================================================
-- INTERNSHIP ASSIGNMENTS
-- =========================================================

INSERT INTO internship_assignments
(
    student_id,
    mentor_id,
    phase_id,
    assigned_date,
    status,
    created_at,
    updated_at
)
SELECT
    s.student_id,
    m.mentor_id,
    p.phase_id,
    CURRENT_TIMESTAMP,

    CASE
        WHEN s.student_code IN ('K20CNTT002', 'K20CNTT003')
            THEN 'COMPLETED'

        WHEN s.student_code IN (
                                'K20CNTT004',
                                'K20CNTT005',
                                'K20CNTT006'
            )
            THEN 'IN_PROGRESS'

        ELSE 'PENDING'
        END,

    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP

FROM students s

         JOIN users su
              ON su.user_id = s.student_id

         CROSS JOIN mentors m

         JOIN users mu
              ON mu.user_id = m.mentor_id

         JOIN internship_phases p
              ON p.phase_name = 'Thực tập cơ sở'

WHERE su.role = 'STUDENT'
  AND mu.username = 'mentor'
  AND mu.role = 'MENTOR'
  AND s.student_code IN (
                         'K20CNTT002',
                         'K20CNTT003',
                         'K20CNTT004',
                         'K20CNTT005',
                         'K20CNTT006',
                         'K20CNTT007',
                         'K20CNTT008',
                         'K20CNTT009',
                         'K20CNTT010'
    );