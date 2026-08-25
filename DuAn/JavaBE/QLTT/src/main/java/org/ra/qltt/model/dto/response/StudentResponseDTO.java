package org.ra.qltt.model.dto.response;

import lombok.Data;
import org.ra.qltt.model.entity.Users;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentResponseDTO {
    private Long studentId;


    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Users.UserRole role;
    private boolean active;

    private String studentCode;
    private String major;
    private String className;
    private LocalDate dob;
    private String address;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
