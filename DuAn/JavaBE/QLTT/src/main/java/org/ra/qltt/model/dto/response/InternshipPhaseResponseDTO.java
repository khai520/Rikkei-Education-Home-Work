package org.ra.qltt.model.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InternshipPhaseResponseDTO {

    private Long phaseId;
    private String phaseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}