package org.ra.qltt.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssessmentRoundUpdateRequestDTO {
    @NotBlank(message = "Tên đợt đánh giá không được để trống")
    @Size(max = 100, message = "Tên đợt đánh giá không được vượt quá 100 ký tự")
    private String roundName;


    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate endDate;

    private String description;

    private boolean active;
}
