package org.ra.qltt.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MentorRequestDTO {

    @NotNull(message = "User ID không được để trống")
    private Long userId;

    @Size(max = 100)
    private String department;

    @Size(max = 50)
    private String academicRank;
}