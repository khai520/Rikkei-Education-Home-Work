package org.ra.qltt.service;

import org.ra.qltt.model.dto.request.AssessmentRoundRequestDTO;
import org.ra.qltt.model.dto.response.AssessmentRoundResponseDTO;

import java.util.List;

public interface AssessmentRoundsService {
    List<AssessmentRoundResponseDTO> getAR();
    AssessmentRoundResponseDTO getARById(Long id);
    AssessmentRoundResponseDTO createAR(AssessmentRoundRequestDTO assessmentRoundRequestDTO);
    AssessmentRoundResponseDTO updateAR(AssessmentRoundRequestDTO assessmentRoundRequestDTO , Long id);
    void deleteAR (Long id);
}
