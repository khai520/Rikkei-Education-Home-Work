package org.ra.qltt.model.dto.response;

import lombok.Data;
import org.ra.qltt.model.entity.Users;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AssessmentResultResponseDTO {

    private Long resultId;

    private Long assignmentId;

    private Long studentId;
    private String studentCode;
    private String studentName;

    private Long mentorId;
    private String mentorName;

    private Long roundId;
    private String roundName;

    private Long criterionId;
    private String criterionName;

    private BigDecimal maxScore;
    private BigDecimal score;

    private String comments;

    private Users evaluatedBy;
    private String evaluatedByName;

    private LocalDateTime evaluationDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}