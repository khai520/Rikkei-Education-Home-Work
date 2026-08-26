package org.ra.qltt.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssessmentRoundCriterionDTO {
    private Long criterionId;
    private String criterionName;
    private BigDecimal weight;
}
