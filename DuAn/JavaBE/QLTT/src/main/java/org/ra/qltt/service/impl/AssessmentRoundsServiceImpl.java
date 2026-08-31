package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.request.AssessmentRoundRequestDTO;
import org.ra.qltt.model.dto.request.AssessmentRoundUpdateRequestDTO;
import org.ra.qltt.model.dto.request.RoundCriterionRoundRequestDTO;
import org.ra.qltt.model.dto.response.AssessmentRoundResponseDTO;
import org.ra.qltt.model.entity.AssessmentRounds;
import org.ra.qltt.model.entity.EvaluationCriteria;
import org.ra.qltt.model.entity.InternshipPhases;
import org.ra.qltt.model.entity.RoundCriteria;
import org.ra.qltt.model.mapper.AssessmentRoundMapper;
import org.ra.qltt.repository.AssessmentRoundsRepository;
import org.ra.qltt.repository.EvaluationCriteriaRepository;
import org.ra.qltt.repository.InternshipPhasesRepository;
import org.ra.qltt.service.AssessmentRoundsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssessmentRoundsServiceImpl implements AssessmentRoundsService {

    private final AssessmentRoundsRepository assessmentRoundsRepository;
    private final AssessmentRoundMapper assessmentRoundMapper;
    private final InternshipPhasesRepository internshipPhasesRepository;
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;


    @Override
    public List<AssessmentRoundResponseDTO> getAR() {
        List<AssessmentRounds> assessmentRounds = assessmentRoundsRepository.findAll();
        return assessmentRoundMapper.roundsToResponseDTOs(assessmentRounds);
    }

    @Override
    public AssessmentRoundResponseDTO getARById(Long id) {
        AssessmentRounds assessmentRounds = assessmentRoundsRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(ResponseWrapper.getMessage("error.assessment_round.not_found"))
        );
        return assessmentRoundMapper.roundToResponseDTO(assessmentRounds);
    }

    @Override
    @Transactional
    public AssessmentRoundResponseDTO createAR(
            AssessmentRoundRequestDTO request
    ) {
        if(assessmentRoundsRepository.existsByRoundName(request.getRoundName())) {
            throw new ResourceNotFoundException(ResponseWrapper.getMessage("error.assessment_round.already_exists"));
        }
        InternshipPhases phase =
                internshipPhasesRepository
                        .findById(request.getPhaseId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "error.internship_phase.not_found"
                                )
                        );

        Set<Long> criterionIds = new HashSet<>();

        for (RoundCriterionRoundRequestDTO criterionRequest : request.getCriteria()) {
            if (!criterionIds.add(criterionRequest.getCriterionId())) {
                throw new IllegalArgumentException(
                        "error.round_criteria.duplicate"
                );
            }
        }

        BigDecimal totalWeight =
                request.getCriteria()
                        .stream()
                        .map(RoundCriterionRoundRequestDTO::getWeight)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalWeight.compareTo(BigDecimal.ONE) != 0) {
            throw new IllegalArgumentException(
                    "error.round_criteria.invalid_weight"
            );
        }


        AssessmentRounds round =
                assessmentRoundMapper.requestToRound(request);

        round.setPhases(phase);

        List<RoundCriteria> roundCriteriaList = new ArrayList<>();

        for (RoundCriterionRoundRequestDTO criterionRequest : request.getCriteria()) {

            EvaluationCriteria criterion =
                    evaluationCriteriaRepository
                            .findById(criterionRequest.getCriterionId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "error.evaluation_criteria.not_found"
                                    )
                            );

            RoundCriteria roundCriteria = new RoundCriteria();

            roundCriteria.setRound(round);
            roundCriteria.setCriterion(criterion);
            roundCriteria.setWeight(criterionRequest.getWeight());

            roundCriteriaList.add(roundCriteria);
        }

        round.setRoundCriteria(roundCriteriaList);

        AssessmentRounds savedRound =
                assessmentRoundsRepository.save(round);

        return assessmentRoundMapper
                .roundToResponseDTO(savedRound);
    }

    @Override
    @Transactional
    public AssessmentRoundResponseDTO updateAR(
            AssessmentRoundUpdateRequestDTO request,
            Long id
    ) {
        AssessmentRounds assessmentRound =
                assessmentRoundsRepository.findById(id)
                        .orElseThrow(() ->
                            new ResourceNotFoundException(ResponseWrapper.getMessage(
                                    "error.assessment_round.not_found"))
                        );
        if(assessmentRoundsRepository.existsByRoundNameAndIdNot(request.getRoundName(), id)) {
            throw new ResourceNotFoundException(ResponseWrapper.getMessage("error.assessment_round.already_exists"));
        }
        assessmentRoundMapper.updateRound(request , assessmentRound);
        assessmentRoundsRepository.save(assessmentRound);

        return assessmentRoundMapper.roundToResponseDTO(assessmentRound);
    }

    @Override
    @Transactional
    public void deleteAR(Long id) {

        AssessmentRounds assessmentRound =
                assessmentRoundsRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(ResponseWrapper.getMessage("error.assessment_round.not_found"))
                        );

        assessmentRoundsRepository.delete(assessmentRound);
    }
}
