package org.ra.qltt.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EvaluationCriterionRequestDTO {

    @NotBlank(message = "Tên tiêu chí không được để trống")
    @Size(max = 200)
    private String criteriaName;

    private String description;

    @NotNull(message = "Điểm tối đa không được để trống")
    @DecimalMin(value = "0.01", message = "Điểm tối đa phải lớn hơn 0")
    private BigDecimal maxScore;
}