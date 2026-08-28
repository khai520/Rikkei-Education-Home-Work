package org.ra.qltt.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentUpdateRequestDTO {

    @Size(max = 100)
    private String major;

    @Size(max = 50)
    private String className;

    private LocalDate dob;

    @Size(max = 255)
    private String address;
}
