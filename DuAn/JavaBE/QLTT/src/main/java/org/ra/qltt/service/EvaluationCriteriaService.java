package org.ra.qltt.service;

import org.ra.qltt.model.dto.request.EvaluationCriterionRequestDTO;
import org.ra.qltt.model.dto.response.EvaluationCriterionResponseDTO;

import java.util.List;

public interface EvaluationCriteriaService {
    List<EvaluationCriterionResponseDTO> getEC();
    EvaluationCriterionResponseDTO getECById(Long id);
    EvaluationCriterionResponseDTO createEC(EvaluationCriterionRequestDTO evaluationCriterionRequestDTO);
    EvaluationCriterionResponseDTO updateEC(EvaluationCriterionRequestDTO evaluationCriterionRequestDTO , Long id);
    void deleteEC (Long id);
}
