package org.ra.qltt.service;

import org.ra.qltt.model.dto.request.RoundCriterionRequestDTO;
import org.ra.qltt.model.dto.response.RoundCriterionResponseDTO;

import java.util.List;

public interface RoundCriteriaService {
    List<RoundCriterionResponseDTO> getRC();
    RoundCriterionResponseDTO getRCById(Long id);
    RoundCriterionResponseDTO createRC(RoundCriterionRequestDTO rc);
    RoundCriterionResponseDTO updateRC(Long id, RoundCriterionRequestDTO rc);
    void deleteRC(Long id);
}
