CREATE DATABASE QLTT ;
DROP TABLE users,students,mentors,internship_phases,evaluation_criteria,assessment_rounds,round_criteria,internship_assignments,assessment_results;
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    role VARCHAR(20) NOT NULL
       CHECK (role IN ('ADMIN', 'MENTOR', 'STUDENT')),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE students (
    student_id INT PRIMARY KEY,
    student_code VARCHAR(20) NOT NULL UNIQUE,
    major VARCHAR(100),
    class VARCHAR(50),
    date_of_birth DATE,
    address VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_students_users
    FOREIGN KEY (student_id)
        REFERENCES users(user_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE mentors (
    mentor_id INT PRIMARY KEY,
    department VARCHAR(100),
    academic_rank VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mentors_users
        FOREIGN KEY (mentor_id)
            REFERENCES users(user_id)
                ON DELETE CASCADE
                ON UPDATE CASCADE
);

CREATE TABLE internship_phases (
    phase_id SERIAL PRIMARY KEY,
    phase_name VARCHAR(100) NOT NULL UNIQUE,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_phase_date
        CHECK (end_date >= start_date)
);

CREATE TABLE evaluation_criteria (
    criterion_id SERIAL PRIMARY KEY,
    criterion_name VARCHAR(200) NOT NULL UNIQUE,
    description TEXT,
    max_score NUMERIC(5,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_criterion_max_score
        CHECK (max_score > 0)
);

CREATE TABLE assessment_rounds (
    round_id SERIAL PRIMARY KEY,
    phase_id INT NOT NULL,
    round_name VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_assessment_rounds_phase
        FOREIGN KEY (phase_id)
            REFERENCES internship_phases(phase_id)
                ON DELETE CASCADE
                ON UPDATE CASCADE,
    CONSTRAINT chk_round_date
        CHECK (end_date >= start_date)
);

CREATE TABLE round_criteria (
    round_criterion_id SERIAL PRIMARY KEY,
    round_id INT NOT NULL,
    criterion_id INT NOT NULL,
    weight NUMERIC(5,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_round_criteria_round
        FOREIGN KEY (round_id)
            REFERENCES assessment_rounds(round_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_round_criteria_criterion
        FOREIGN KEY (criterion_id)
            REFERENCES evaluation_criteria(criterion_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT chk_round_criteria_weight
        CHECK (weight > 0),
    CONSTRAINT uq_round_criteria
        UNIQUE (round_id, criterion_id)
);

CREATE TABLE internship_assignments (
    assignment_id SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    mentor_id INT NOT NULL,
    phase_id INT NOT NULL,
    assigned_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_assignments_student
        FOREIGN KEY (student_id)
            REFERENCES students(student_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_assignments_mentor
        FOREIGN KEY (mentor_id)
            REFERENCES mentors(mentor_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT fk_assignments_phase
        FOREIGN KEY (phase_id)
            REFERENCES internship_phases(phase_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT chk_assignment_status
        CHECK (
            status IN (
                       'PENDING',
                       'IN_PROGRESS',
                       'COMPLETED',
                       'CANCELLED'
                )
            ),
    CONSTRAINT uq_student_phase
        UNIQUE (student_id, phase_id)
);

CREATE TABLE assessment_results (
    result_id SERIAL PRIMARY KEY,
    assignment_id INT NOT NULL,
    round_id INT NOT NULL,
    criterion_id INT NOT NULL,
    score NUMERIC(5,2) NOT NULL,
    comments TEXT,
    evaluated_by INT NOT NULL,
    evaluation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_results_assignment
        FOREIGN KEY (assignment_id)
            REFERENCES internship_assignments(assignment_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_results_round
        FOREIGN KEY (round_id)
            REFERENCES assessment_rounds(round_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_results_criterion
        FOREIGN KEY (criterion_id)
            REFERENCES evaluation_criteria(criterion_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_results_evaluated_by
        FOREIGN KEY (evaluated_by)
            REFERENCES users(user_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT chk_result_score
        CHECK (score >= 0),
    CONSTRAINT uq_assessment_result
        UNIQUE (assignment_id, round_id, criterion_id)
);

-- SEARCH
CREATE INDEX idx_assessment_rounds_phase_id
    ON assessment_rounds(phase_id);

CREATE INDEX idx_round_criteria_round_id
    ON round_criteria(round_id);

CREATE INDEX idx_round_criteria_criterion_id
    ON round_criteria(criterion_id);

CREATE INDEX idx_assignments_student_id
    ON internship_assignments(student_id);

CREATE INDEX idx_assignments_mentor_id
    ON internship_assignments(mentor_id);

CREATE INDEX idx_assignments_phase_id
    ON internship_assignments(phase_id);

CREATE INDEX idx_results_assignment_id
    ON assessment_results(assignment_id);

CREATE INDEX idx_results_round_id
    ON assessment_results(round_id);

CREATE INDEX idx_results_criterion_id
    ON assessment_results(criterion_id);

CREATE INDEX idx_results_evaluated_by
    ON assessment_results(evaluated_by);
