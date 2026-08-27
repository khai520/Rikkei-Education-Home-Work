package org.ra.qltt.model.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoundCriterionRequestDTO {
    @NotNull
    private Long assessmentId;

    @NotNull
    private Long roundId;

    @NotNull
    private Long criterionId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "1.0")
    private BigDecimal weight;
}