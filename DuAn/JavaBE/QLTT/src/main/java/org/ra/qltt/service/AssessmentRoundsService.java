package org.ra.qltt.service;

import org.ra.qltt.model.dto.request.AssessmentRoundRequestDTO;
import org.ra.qltt.model.dto.request.AssessmentRoundUpdateRequestDTO;
import org.ra.qltt.model.dto.response.AssessmentRoundResponseDTO;

import java.util.List;

public interface AssessmentRoundsService {
    List<AssessmentRoundResponseDTO> getAR();
    AssessmentRoundResponseDTO getARById(Long id);
    AssessmentRoundResponseDTO createAR(AssessmentRoundRequestDTO assessmentRoundRequestDTO);
    AssessmentRoundResponseDTO updateAR(AssessmentRoundUpdateRequestDTO assessmentRoundRequestDTO , Long id);
    void deleteAR (Long id);
}
