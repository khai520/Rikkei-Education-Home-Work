package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.config.MessageSourceConfig;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.model.dto.request.EvaluationCriterionRequestDTO;
import org.ra.qltt.model.dto.response.EvaluationCriterionResponseDTO;
import org.ra.qltt.model.entity.EvaluationCriteria;
import org.ra.qltt.model.mapper.EvaluationCriterionMapper;
import org.ra.qltt.repository.EvaluationCriteriaRepository;
import org.ra.qltt.service.EvaluationCriteriaService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationCriteriaServiceImpl implements EvaluationCriteriaService {

    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final EvaluationCriterionMapper evaluationCriterionMapper;
    private final MessageSourceConfig messageSourceConfig;

    @Override
    public List<EvaluationCriterionResponseDTO> getEC() {
        List<EvaluationCriteria> evaluationCriteria = evaluationCriteriaRepository.findAll();
        return evaluationCriterionMapper.criteriaToResponseDTOs(evaluationCriteria);
    }

    @Override
    public EvaluationCriterionResponseDTO getECById(Long id) {
        EvaluationCriteria evaluationCriteria = evaluationCriteriaRepository.findById(id).orElseThrow(() -> {
            String errorMessage = messageSourceConfig.messageSource()
                    .getMessage("error.resource.not_found", new Object[]{"EvaluationCriteria", id}, LocaleContextHolder.getLocale());
            return new ResourceNotFoundException(errorMessage);
        });
        return evaluationCriterionMapper.criterionToResponseDTO(evaluationCriteria);
    }

    @Override
    public EvaluationCriterionResponseDTO createEC(EvaluationCriterionRequestDTO evaluationCriterionRequestDTO) {
        EvaluationCriteria evaluationCriteria = evaluationCriterionMapper.requestToCriterion(evaluationCriterionRequestDTO);
        EvaluationCriteria newEvaluationCriteria = evaluationCriteriaRepository.save(evaluationCriteria);
        return evaluationCriterionMapper.criterionToResponseDTO(newEvaluationCriteria);
    }

    @Override
    public EvaluationCriterionResponseDTO updateEC(EvaluationCriterionRequestDTO evaluationCriterionRequestDTO, Long id) {
        EvaluationCriteria evaluationCriteria = evaluationCriteriaRepository.findById(id).orElseThrow(() -> {
            String errorMessage = messageSourceConfig.messageSource()
                    .getMessage("error.resource.not_found", new Object[]{"EvaluationCriteria", id}, LocaleContextHolder.getLocale());
            return new ResourceNotFoundException(errorMessage);
        });
        evaluationCriterionMapper.updateCriterion(evaluationCriterionRequestDTO, evaluationCriteria);
        EvaluationCriteria updateEC = evaluationCriteriaRepository.save(evaluationCriteria);
        return evaluationCriterionMapper.criterionToResponseDTO(updateEC);
    }

    @Override
    public void deleteEC(Long id) {
        EvaluationCriteria evaluationCriteria = evaluationCriteriaRepository.findById(id).orElseThrow(() -> {
            String errorMessage = messageSourceConfig.messageSource()
                    .getMessage("error.resource.not_found", new Object[]{"EvaluationCriteria", id}, LocaleContextHolder.getLocale());
            return new ResourceNotFoundException(errorMessage);
        });
        evaluationCriteriaRepository.delete(evaluationCriteria);
    }
}
