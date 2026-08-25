package org.ra.qltt.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoundCriterionRequestDTO {

    @NotNull(message = "Round ID không được để trống")
    private Long roundId;

    @NotNull(message = "Criterion ID không được để trống")
    private Long criterionId;

    @NotNull(message = "Trọng số không được để trống")
    @DecimalMin(value = "0.01", message = "Trọng số phải lớn hơn 0")
    private BigDecimal weight;
}