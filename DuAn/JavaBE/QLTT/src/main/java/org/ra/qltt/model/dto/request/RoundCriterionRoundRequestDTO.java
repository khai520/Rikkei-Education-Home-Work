package org.ra.qltt.model.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoundCriterionRoundRequestDTO {
    @NotNull(message = "Criterion ID không được để trống")
    private Long criterionId;

    @NotNull(message = "Trọng số không được để trống")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Trọng số phải lớn hơn 0"
    )
    @DecimalMax(
            value = "1.0",
            message = "Trọng số không được lớn hơn 1"
    )
    private BigDecimal weight;
}
