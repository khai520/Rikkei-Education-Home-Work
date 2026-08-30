package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.request.RoundCriterionRequestDTO;
import org.ra.qltt.model.dto.request.RoundCriterionUpdateRequestDTO;
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
import java.math.RoundingMode;
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
                assessmentRoundsRepository.findById(rc.getRoundId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Không tìm thấy đợt đánh giá với ID: "
                                                + rc.getRoundId()
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
                rc.getRoundId()
        ) != null) {

            throw new IllegalArgumentException(
                    "Tiêu chí ID "
                            + rc.getCriterionId()
                            + " đã tồn tại trong đợt đánh giá ID "
                            + rc.getRoundId()
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
                        rc.getRoundId()
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
            RoundCriterionUpdateRequestDTO rc
    ) {

        RoundCriteria roundCriteria =
                roundCriteriaRepository.findById(id)
                        .orElseThrow(() ->
                            new ResourceNotFoundException(ResponseWrapper.getMessage("error.round_criteria.not_found"))
                        );

        BigDecimal currentTotal =
                roundCriteriaRepository.sumWeightByRoundId(
                        id
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
        roundCriteria.setWeight(rc.getWeight());

        RoundCriteria saved =
                roundCriteriaRepository.save(roundCriteria);

        return roundCriteriaMapper
                .roundCriteriaToResponseDTO(saved);
    }

    @Override
    @Transactional
    public void deleteRC(Long id) {

        // 1. Tìm RoundCriteria cần xóa
        RoundCriteria roundCriteria =
                roundCriteriaRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        ResponseWrapper.getMessage(
                                                "error.round_criteria.not_found"
                                        )
                                )
                        );

        // 2. Lấy round mà RoundCriteria này thuộc về
        AssessmentRounds round = roundCriteria.getRound();

        // 3. Lấy tất cả RoundCriteria của round đó
        List<RoundCriteria> remainingCriteria =
                roundCriteriaRepository.findByRoundId(round.getId());

        // 4. Loại bỏ criteria đang chuẩn bị xóa
        remainingCriteria.removeIf(
                rc -> rc.getId().equals(id)
        );

        // 5. Xóa RoundCriteria
        roundCriteriaRepository.delete(roundCriteria);

        // 6. Nếu không còn criteria nào thì kết thúc
        if (remainingCriteria.isEmpty()) {
            return;
        }

        // 7. Chia đều trọng số cho các criteria còn lại
        BigDecimal equalWeight =
                BigDecimal.ONE.divide(
                        BigDecimal.valueOf(remainingCriteria.size()),
                        10,
                        RoundingMode.HALF_UP
                );

        // 8. Gán trọng số mới
        for (RoundCriteria rc : remainingCriteria) {
            rc.setWeight(equalWeight);
        }

        // 9. Lưu lại
        roundCriteriaRepository.saveAll(remainingCriteria);
    }
}
