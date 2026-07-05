package model.dto;

import model.Enrollment;

import java.time.LocalDateTime;

public class EnrollmentDTO {
    public int id;
    public String studentName;
    public String courseName;
    public LocalDateTime registered_at;
    public Enrollment.Status status;

    public EnrollmentDTO(int id, String studentName, String courseName, LocalDateTime registered_at, Enrollment.Status status) {
        this.id = id;
        this.studentName = studentName;
        this.courseName = courseName;
        this.registered_at = registered_at;
        this.status = status;
    }
    public EnrollmentDTO() {

    }
}
