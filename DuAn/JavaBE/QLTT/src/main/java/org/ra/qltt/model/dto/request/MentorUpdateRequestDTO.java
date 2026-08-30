package org.ra.qltt.model.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MentorUpdateRequestDTO {
    @Size(max = 100)
    private String department;

    @Size(max = 50)
    private String academicRank;
}
