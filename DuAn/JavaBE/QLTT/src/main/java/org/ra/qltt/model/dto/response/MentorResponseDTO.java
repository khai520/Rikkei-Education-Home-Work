package org.ra.qltt.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MentorResponseDTO {

    private Long mentorId;

    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String role;
    private boolean active;

    private String department;
    private String academicRank;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}