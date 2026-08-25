package org.ra.qltt.model.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AssessmentRoundResponseDTO {

    private Long roundId;

    private Long phaseId;
    private String phaseName;

    private String roundName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}