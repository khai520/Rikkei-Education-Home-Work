package org.ra.qltt.model.dto.response;

import lombok.Data;
import org.ra.qltt.model.entity.InternshipAssignments;

import java.time.LocalDateTime;

@Data
public class InternshipAssignmentResponseDTO {

    private Long assignmentId;

    private Long studentId;
    private String studentCode;
    private String studentName;

    private Long mentorId;
    private String mentorName;

    private Long phaseId;
    private String phaseName;

    private LocalDateTime assignedDate;

    private InternshipAssignments.AssignmentStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}