package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.exception.ResourceAlreadyExistsException;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.request.EvaluationCriterionRequestDTO;
import org.ra.qltt.model.dto.response.EvaluationCriterionResponseDTO;
import org.ra.qltt.model.entity.EvaluationCriteria;
import org.ra.qltt.model.mapper.EvaluationCriterionMapper;
import org.ra.qltt.repository.EvaluationCriteriaRepository;
import org.ra.qltt.service.EvaluationCriteriaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationCriteriaServiceImpl implements EvaluationCriteriaService {

    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final EvaluationCriterionMapper evaluationCriterionMapper;

    @Override
    public List<EvaluationCriterionResponseDTO> getEC() {
        List<EvaluationCriteria> evaluationCriteria = evaluationCriteriaRepository.findAll();
        return evaluationCriterionMapper.criteriaToResponseDTOs(evaluationCriteria);
    }

    @Override
    public EvaluationCriterionResponseDTO getECById(Long id) {
        EvaluationCriteria evaluationCriteria = evaluationCriteriaRepository.findById(id).orElseThrow(() ->
             new ResourceNotFoundException(ResponseWrapper.getMessage("error.evaluation_criteria.not_found"))
        );
        return evaluationCriterionMapper.criterionToResponseDTO(evaluationCriteria);
    }

    @Override
    public EvaluationCriterionResponseDTO createEC(EvaluationCriterionRequestDTO evaluationCriterionRequestDTO) {
        if(evaluationCriteriaRepository.existsByCriteriaName(evaluationCriterionRequestDTO.getCriteriaName())){
            throw new ResourceAlreadyExistsException(ResponseWrapper.getMessage("error.evaluation_criteria.already_exists"));
        }
        EvaluationCriteria evaluationCriteria = evaluationCriterionMapper.requestToCriterion(evaluationCriterionRequestDTO);
        EvaluationCriteria newEvaluationCriteria = evaluationCriteriaRepository.save(evaluationCriteria);
        return evaluationCriterionMapper.criterionToResponseDTO(newEvaluationCriteria);
    }

    @Override
    public EvaluationCriterionResponseDTO updateEC(EvaluationCriterionRequestDTO evaluationCriterionRequestDTO, Long id) {
        EvaluationCriteria evaluationCriteria = evaluationCriteriaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ResponseWrapper.getMessage("error.evaluation_criteria.not_found"))
        );
        if(evaluationCriteriaRepository.existsByCriteriaNameAndIdNot(evaluationCriterionRequestDTO.getCriteriaName() , id)){
            throw new ResourceAlreadyExistsException(ResponseWrapper.getMessage("error.evaluation_criteria.already_exists"));
        }
        evaluationCriterionMapper.updateCriterion(evaluationCriterionRequestDTO, evaluationCriteria);
        EvaluationCriteria updateEC = evaluationCriteriaRepository.save(evaluationCriteria);
        return evaluationCriterionMapper.criterionToResponseDTO(updateEC);
    }

    @Override
    public void deleteEC(Long id) {
        EvaluationCriteria evaluationCriteria = evaluationCriteriaRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(ResponseWrapper.getMessage("error.evaluation_criteria.not_found"))
        );
        evaluationCriteriaRepository.delete(evaluationCriteria);
    }
}
