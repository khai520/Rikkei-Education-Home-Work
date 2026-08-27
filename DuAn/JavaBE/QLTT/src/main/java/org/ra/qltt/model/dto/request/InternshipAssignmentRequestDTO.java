package org.ra.qltt.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.ra.qltt.model.dto.enums.AssignmentStatus;

@Data
public class InternshipAssignmentRequestDTO {

    @NotNull(message = "Student ID không được để trống")
    private Long studentId;

    @NotNull(message = "Mentor ID không được để trống")
    private Long mentorId;

    @NotNull(message = "Phase ID không được để trống")
    private Long phaseId;

    private AssignmentStatus status;
}