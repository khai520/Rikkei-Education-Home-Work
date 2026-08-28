package org.ra.qltt.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.ra.qltt.validation.ValidDateRange;

import java.time.LocalDate;

@ValidDateRange(startDate = "startDate" , endDate = "endDate")
@Data
public class InternshipPhaseRequestDTO {

    @NotBlank(message = "Tên giai đoạn không được để trống")
    @Size(max = 100)
    private String phaseName;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate endDate;

    private String description;
}