package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.request.RoundCriterionRequestDTO;
import org.ra.qltt.model.dto.response.RoundCriterionResponseDTO;
import org.ra.qltt.model.entity.AssessmentRounds;
import org.ra.qltt.model.entity.EvaluationCriteria;
import org.ra.qltt.model.entity.RoundCriteria;
import org.ra.qltt.model.mapper.RoundCriteriaMapper;
import org.ra.qltt.repository.AssessmentRoundsRepository;
import org.ra.qltt.repository.EvaluationCriteriaRepository;
import org.ra.qltt.repository.RoundCriteriaRepository;
import org.ra.qltt.service.RoundCriteriaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundCriteriaServiceImpl implements RoundCriteriaService {
    private final RoundCriteriaRepository roundCriteriaRepository;
    private final RoundCriteriaMapper roundCriteriaMapper;
    private final AssessmentRoundsRepository  assessmentRoundsRepository;
    private final EvaluationCriteriaRepository  evaluationCriteriaRepository;


    @Override
    public List<RoundCriterionResponseDTO> getRC() {
        List<RoundCriteria> roundCriteria = roundCriteriaRepository.findAll();
        return roundCriteriaMapper.roundCriteriaToResponseDTOs(roundCriteria);
    }

    @Override
    public RoundCriterionResponseDTO getRCById(Long id) {
        RoundCriteria roundCriteria = roundCriteriaRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(ResponseWrapper.getMessage("error.round_criteria.not_found"))
        );
        return roundCriteriaMapper.roundCriteriaToResponseDTO(roundCriteria);
    }

    @Override
    @Transactional
    public RoundCriterionResponseDTO createRC(RoundCriterionRequestDTO rc) {

        AssessmentRounds assessmentRounds =
                assessmentRoundsRepository.findById(rc.getAssessmentId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Không tìm thấy đợt đánh giá với ID: "
                                                + rc.getAssessmentId()
                                )
                        );

        EvaluationCriteria evaluationCriteria =
                evaluationCriteriaRepository.findById(rc.getCriterionId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Không tìm thấy tiêu chí với ID: "
                                                + rc.getCriterionId()
                                )
                        );

        if (roundCriteriaRepository.findFirstByCriterion_IdAndRound_Id(
                rc.getCriterionId(),
                rc.getAssessmentId()
        ) != null) {

            throw new IllegalArgumentException(
                    "Tiêu chí ID "
                            + rc.getCriterionId()
                            + " đã tồn tại trong đợt đánh giá ID "
                            + rc.getAssessmentId()
            );
        }

        if (rc.getWeight() == null ||
                rc.getWeight().compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Trọng số phải lớn hơn 0"
            );
        }

        BigDecimal currentWeight =
                roundCriteriaRepository.sumWeightByRoundId(
                        rc.getAssessmentId()
                );

        if (currentWeight == null) {
            currentWeight = BigDecimal.ZERO;
        }

        BigDecimal totalWeight =
                currentWeight.add(rc.getWeight());

        if (totalWeight.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException(
                    "Tổng trọng số không được vượt quá 1.00"
            );
        }

        RoundCriteria roundCriteria = new RoundCriteria();

        roundCriteria.setRound(assessmentRounds);
        roundCriteria.setCriterion(evaluationCriteria);
        roundCriteria.setWeight(rc.getWeight());

        RoundCriteria saved =
                roundCriteriaRepository.save(roundCriteria);

        return roundCriteriaMapper.roundCriteriaToResponseDTO(saved);
    }

    @Override
    @Transactional
    public RoundCriterionResponseDTO updateRC(
            Long id,
            RoundCriterionRequestDTO rc
    ) {

        RoundCriteria roundCriteria =
                roundCriteriaRepository.findById(id)
                        .orElseThrow(() ->
                            new ResourceNotFoundException(ResponseWrapper.getMessage("error.round_criteria.not_found"))
                        );

        AssessmentRounds assessmentRounds =
                assessmentRoundsRepository.findById(
                                rc.getAssessmentId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Không tìm thấy đợt đánh giá với ID: "
                                                + rc.getAssessmentId()
                                )
                        );

        EvaluationCriteria evaluationCriteria =
                evaluationCriteriaRepository.findById(
                                rc.getCriterionId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Không tìm thấy tiêu chí với ID: "
                                                + rc.getCriterionId()
                                )
                        );

        RoundCriteria existing =
                roundCriteriaRepository
                        .findFirstByCriterion_IdAndRound_Id(
                                rc.getCriterionId(),
                                rc.getAssessmentId()
                        );

        if (existing != null &&
                !existing.getId().equals(id)) {

            throw new IllegalArgumentException(
                    "Tiêu chí ID "
                            + rc.getCriterionId()
                            + " đã tồn tại trong đợt đánh giá ID "
                            + rc.getAssessmentId()
            );
        }

        BigDecimal currentTotal =
                roundCriteriaRepository.sumWeightByRoundId(
                        rc.getAssessmentId()
                );

        if (currentTotal == null) {
            currentTotal = BigDecimal.ZERO;
        }

        BigDecimal totalWeight =
                currentTotal
                        .subtract(roundCriteria.getWeight())
                        .add(rc.getWeight());

        if (totalWeight.compareTo(BigDecimal.ONE) > 0) {

            throw new IllegalArgumentException(
                    "Tổng trọng số không được vượt quá 1.00"
            );
        }


        roundCriteria.setRound(assessmentRounds);
        roundCriteria.setCriterion(evaluationCriteria);
        roundCriteria.setWeight(rc.getWeight());

        RoundCriteria saved =
                roundCriteriaRepository.save(roundCriteria);

        return roundCriteriaMapper
                .roundCriteriaToResponseDTO(saved);
    }

    @Override
    @Transactional
    public void deleteRC(Long id) {

        RoundCriteria roundCriteria =
                roundCriteriaRepository.findById(id)
                        .orElseThrow(() ->
                            new ResourceNotFoundException(ResponseWrapper.getMessage("error.round_criteria.not_found"))
                        );

        roundCriteriaRepository.delete(roundCriteria);
    }
}
