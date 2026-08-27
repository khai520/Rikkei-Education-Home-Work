package org.ra.qltt.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AssessmentResultRequestDTO {

    @NotNull(message = "Assignment ID không được để trống")
    private Long assignmentId;

    @NotNull(message = "Round ID không được để trống")
    private Long roundId;

    @NotNull(message = "Criterion ID không được để trống")
    private Long criterionId;

    @NotNull(message = "Điểm không được để trống")
    @DecimalMin(value = "0.00", message = "Điểm không được nhỏ hơn 0")
    private BigDecimal score;

    @FutureOrPresent(message = "Thời gian đánh giá không được ở quá khứ")
    private LocalDateTime evaluationDate;

    private String comment;
}