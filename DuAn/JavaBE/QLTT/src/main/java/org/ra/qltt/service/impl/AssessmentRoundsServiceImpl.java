package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.config.MessageSourceConfig;
import org.ra.qltt.exception.ResourceNotFoundException;
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
import org.ra.qltt.repository.RoundCriteriaRepository;
import org.ra.qltt.service.AssessmentRoundsService;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final MessageSourceConfig messageSourceConfig;
    private final InternshipPhasesRepository internshipPhasesRepository;
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final RoundCriteriaRepository roundCriteriaRepository;

    @Override
    public List<AssessmentRoundResponseDTO> getAR() {
        List<AssessmentRounds> assessmentRounds = assessmentRoundsRepository.findAll();
        return assessmentRoundMapper.roundsToResponseDTOs(assessmentRounds);
    }

    @Override
    public AssessmentRoundResponseDTO getARById(Long id) {
        AssessmentRounds assessmentRounds = assessmentRoundsRepository.findById(id).orElseThrow(() -> {
            String errorMessage = messageSourceConfig.messageSource()
                    .getMessage("error.resource.not_found", new Object[]{"User", id}, LocaleContextHolder.getLocale());
            return new ResourceNotFoundException(errorMessage);
        });
        return assessmentRoundMapper.roundToResponseDTO(assessmentRounds);
    }

    @Override
    @Transactional
    public AssessmentRoundResponseDTO createAR(
            AssessmentRoundRequestDTO assessmentRoundRequestDTO
    ) {

        if (assessmentRoundRequestDTO.getStartDate()
                .isAfter(assessmentRoundRequestDTO.getEndDate())) {

            throw new IllegalArgumentException(
                    "Ngày bắt đầu không được lớn hơn ngày kết thúc"
            );
        }


        InternshipPhases phase =
                internshipPhasesRepository
                        .findById(assessmentRoundRequestDTO.getPhaseId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Không tìm thấy giai đoạn thực tập với ID: "
                                                + assessmentRoundRequestDTO.getPhaseId()
                                )
                        );


        if (assessmentRoundRequestDTO.getCriteria() == null
                || assessmentRoundRequestDTO.getCriteria().isEmpty()) {

            throw new IllegalArgumentException(
                    "Đợt đánh giá phải có ít nhất một tiêu chí"
            );
        }


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
                        .orElseThrow(() -> {
                            String errorMessage = messageSourceConfig
                                    .messageSource()
                                    .getMessage(
                                            "error.resource.not_found",
                                            new Object[]{"Assessment Round", id},
                                            LocaleContextHolder.getLocale()
                                    );

                            return new ResourceNotFoundException(errorMessage);
                        });


        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException(
                    "Ngày bắt đầu không được lớn hơn ngày kết thúc"
            );
        }


        InternshipPhases phase =
                internshipPhasesRepository.findById(request.getPhaseId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Không tìm thấy giai đoạn thực tập với ID: "
                                                + request.getPhaseId()
                                )
                        );


        if (request.getCriteria() == null
                || request.getCriteria().isEmpty()) {

            throw new IllegalArgumentException(
                    "Đợt đánh giá phải có ít nhất một tiêu chí"
            );
        }


        Set<Long> criterionIds = new HashSet<>();

        for (RoundCriterionRequestDTO criterionRequest
                : request.getCriteria()) {

            if (criterionRequest.getCriterionId() == null) {
                throw new IllegalArgumentException(
                        "criterionId không được để trống"
                );
            }

            if (criterionRequest.getWeight() == null) {
                throw new IllegalArgumentException(
                        "Trọng số của tiêu chí không được để trống"
                );
            }

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


        AssessmentRounds savedRound =
                assessmentRoundsRepository.save(assessmentRound);



        roundCriteriaRepository.deleteById(savedRound.getId());


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


            RoundCriteria roundCriteria =
                    new RoundCriteria();

            roundCriteria.setRound(savedRound);
            roundCriteria.setCriterion(criterion);
            roundCriteria.setWeight(criterionRequest.getWeight());

            roundCriteriaRepository.save(roundCriteria);
        }



        return assessmentRoundMapper.roundToResponseDTO(savedRound);
    }

    @Override
    @Transactional
    public void deleteAR(Long id) {

        AssessmentRounds assessmentRound =
                assessmentRoundsRepository.findById(id)
                        .orElseThrow(() -> {

                            String errorMessage = messageSourceConfig
                                    .messageSource()
                                    .getMessage(
                                            "error.resource.not_found",
                                            new Object[]{"Assessment Round", id},
                                            LocaleContextHolder.getLocale()
                                    );

                            return new ResourceNotFoundException(errorMessage);
                        });

        assessmentRoundsRepository.delete(assessmentRound);
    }
}
