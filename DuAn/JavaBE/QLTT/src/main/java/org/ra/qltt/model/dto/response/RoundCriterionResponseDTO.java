package org.ra.qltt.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RoundCriterionResponseDTO {

    private Long roundCriterionId;

    private Long roundId;
    private String roundName;

    private Long criterionId;
    private String criterionName;

    private BigDecimal maxScore;
    private BigDecimal weight;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}