package org.ra.qltt.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EvaluationCriterionResponseDTO {

    private Long criterionId;
    private String criterionName;
    private String description;
    private BigDecimal maxScore;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}