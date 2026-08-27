package org.ra.qltt.service;

import org.ra.qltt.model.dto.request.AssessmentResultRequestDTO;
import org.ra.qltt.model.dto.response.AssessmentResultResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AssessmentResultsService {
    List<AssessmentResultResponseDTO> findAR();
    AssessmentResultResponseDTO createAR(AssessmentResultRequestDTO assessmentResultRequestDTO);
    AssessmentResultResponseDTO updateAR(Long id, BigDecimal score);
}
