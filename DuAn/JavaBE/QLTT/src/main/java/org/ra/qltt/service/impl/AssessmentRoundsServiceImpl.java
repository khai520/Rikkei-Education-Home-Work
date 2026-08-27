package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.request.AssessmentRoundRequestDTO;
import org.ra.qltt.model.dto.request.RoundCriterionRequestDTO;
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
            AssessmentRoundRequestDTO assessmentRoundRequestDTO
    ) {

        InternshipPhases phase =
                internshipPhasesRepository
                        .findById(assessmentRoundRequestDTO.getPhaseId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Không tìm thấy giai đoạn thực tập với ID: "
                                                + assessmentRoundRequestDTO.getPhaseId()
                                )
                        );


        Set<Long> criterionIds = new HashSet<>();

        for (RoundCriterionRequestDTO criterionRequest
                : assessmentRoundRequestDTO.getCriteria()) {

            if (!criterionIds.add(
                    criterionRequest.getCriterionId()
            )) {

                throw new IllegalArgumentException(
                        "Tiêu chí ID "
                                + criterionRequest.getCriterionId()
                                + " bị trùng"
                );
            }
        }


        BigDecimal totalWeight =
                assessmentRoundRequestDTO
                        .getCriteria()
                        .stream()
                        .map(RoundCriterionRequestDTO::getWeight)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalWeight.compareTo(BigDecimal.ONE) != 0) {

            throw new IllegalArgumentException(
                    "Tổng trọng số của các tiêu chí phải bằng 1.0"
            );
        }


        AssessmentRounds round =
                assessmentRoundMapper
                        .requestToRound(
                                assessmentRoundRequestDTO
                        );

        round.setPhases(phase);


        List<RoundCriteria> roundCriteriaList =
                new ArrayList<>();

        for (RoundCriterionRequestDTO criterionRequest
                : assessmentRoundRequestDTO.getCriteria()) {

            EvaluationCriteria criterion =
                    evaluationCriteriaRepository
                            .findById(
                                    criterionRequest.getCriterionId()
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Không tìm thấy tiêu chí với ID: "
                                                    + criterionRequest.getCriterionId()
                                    )
                            );

            RoundCriteria roundCriteria =
                    new RoundCriteria();

            roundCriteria.setRound(round);
            roundCriteria.setCriterion(criterion);
            roundCriteria.setWeight(
                    criterionRequest.getWeight()
            );

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
            AssessmentRoundRequestDTO request,
            Long id
    ) {


        AssessmentRounds assessmentRound =
                assessmentRoundsRepository.findById(id)
                        .orElseThrow(() ->
                            new ResourceNotFoundException(ResponseWrapper.getMessage(
                                    "error.assessment_round.not_found"))
                        );


        InternshipPhases phase =
                internshipPhasesRepository.findById(request.getPhaseId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Không tìm thấy giai đoạn thực tập với ID: "
                                                + request.getPhaseId()
                                )
                        );
        Set<Long> criterionIds = new HashSet<>();

        for (RoundCriterionRequestDTO criterionRequest
                : request.getCriteria()) {

            if (!criterionIds.add(criterionRequest.getCriterionId())) {

                throw new IllegalArgumentException(
                        "Tiêu chí ID "
                                + criterionRequest.getCriterionId()
                                + " bị trùng"
                );
            }
        }

        BigDecimal totalWeight =
                request.getCriteria()
                        .stream()
                        .map(RoundCriterionRequestDTO::getWeight)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        if (totalWeight.compareTo(BigDecimal.ONE) != 0) {

            throw new IllegalArgumentException(
                    "Tổng trọng số của các tiêu chí phải bằng 1.0"
            );
        }

        assessmentRound.setPhases(phase);
        assessmentRound.setRoundName(request.getRoundName());
        assessmentRound.setStartDate(request.getStartDate());
        assessmentRound.setEndDate(request.getEndDate());
        assessmentRound.setDescription(request.getDescription());
        assessmentRound.setActive(request.isActive());

        assessmentRound.getRoundCriteria().clear();

        for (RoundCriterionRequestDTO criterionRequest
                : request.getCriteria()) {

            EvaluationCriteria criterion =
                    evaluationCriteriaRepository
                            .findById(criterionRequest.getCriterionId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Không tìm thấy tiêu chí với ID: "
                                                    + criterionRequest.getCriterionId()
                                    )
                            );

            RoundCriteria roundCriteria = new RoundCriteria();

            roundCriteria.setRound(assessmentRound);
            roundCriteria.setCriterion(criterion);
            roundCriteria.setWeight(criterionRequest.getWeight());

            assessmentRound.getRoundCriteria().add(roundCriteria);
        }

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
