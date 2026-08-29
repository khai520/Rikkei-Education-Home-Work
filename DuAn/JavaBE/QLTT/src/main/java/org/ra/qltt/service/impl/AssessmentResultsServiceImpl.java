package org.ra.qltt.service.impl;


import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.ra.qltt.exception.ResourceAlreadyExistsException;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.enums.UserRole;
import org.ra.qltt.model.dto.request.AssessmentResultRequestDTO;
import org.ra.qltt.model.dto.response.AssessmentResultResponseDTO;
import org.ra.qltt.model.entity.*;
import org.ra.qltt.model.mapper.AssessmentResultMapper;
import org.ra.qltt.repository.AssessmentResultsRepository;
import org.ra.qltt.repository.AssessmentRoundsRepository;
import org.ra.qltt.repository.EvaluationCriteriaRepository;
import org.ra.qltt.repository.InternshipAssignmentsRepository;
import org.ra.qltt.security.UserPrinciple;
import org.ra.qltt.service.AssessmentResultsService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentResultsServiceImpl implements AssessmentResultsService {
    private final AssessmentResultsRepository  assessmentResultsRepository;
    private final AssessmentResultMapper  assessmentResultMapper;
    private final InternshipAssignmentsRepository internshipAssignmentsRepository;
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final AssessmentRoundsRepository  assessmentRoundsRepository;
    @Override
    public List<AssessmentResultResponseDTO>    findAR() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        UserPrinciple principal =
                (UserPrinciple) authentication.getPrincipal();

        assert principal != null;
        Users user = principal.getUser();

        List<AssessmentResults> results ;
        if (user.getRole().equals(UserRole.ADMIN.name())) {
            results = assessmentResultsRepository.findAll();
        } else if (user.getRole().equals(UserRole.MENTOR.name())) {
            results = assessmentResultsRepository.findByAssignment_Mentor_Id(user.getId());
        }
        else {
            results = assessmentResultsRepository.findByAssignment_Student_Id(user.getId());
        }
        return assessmentResultMapper.resultsToResponseDTOs(results);
    }

    @Override
    public AssessmentResultResponseDTO createAR(AssessmentResultRequestDTO assessmentResultRequestDTO) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        UserPrinciple principal =
                (UserPrinciple) authentication.getPrincipal();

        assert principal != null;
        Users user = principal.getUser();

        InternshipAssignments internshipAssignments = internshipAssignmentsRepository.findById(assessmentResultRequestDTO.getAssignmentId()).orElseThrow(() ->
                    new NoResultException(ResponseWrapper.getMessage("error.assessment_result.assignment_not_found"))
                );

        if (!internshipAssignmentsRepository.existsByIdAndMentorId(assessmentResultRequestDTO.getAssignmentId(), user.getId())) {
            throw new AccessDeniedException("error.assessment_result.mentor_denied");
        }

        EvaluationCriteria evaluationCriteria = evaluationCriteriaRepository.findById(assessmentResultRequestDTO.getCriterionId()).orElseThrow(() ->
                    new NoResultException(ResponseWrapper.getMessage("error.assessment_result.criterion_not_found")         )
                );

        AssessmentRounds assessmentRounds = assessmentRoundsRepository.findById(assessmentResultRequestDTO.getRoundId()).orElseThrow(() ->
                    new NoResultException(ResponseWrapper.getMessage("error.assessment_result.round_not_found"))
                );

        if (assessmentResultsRepository.existsByAssignmentIdAndCriterionIdAndRoundId(assessmentResultRequestDTO.getAssignmentId(), assessmentResultRequestDTO.getCriterionId(), assessmentResultRequestDTO.getRoundId())) {
            throw new ResourceAlreadyExistsException(ResponseWrapper.getMessage("error.assessment_result.already_exists"));
        }

        AssessmentResults assessmentResults = assessmentResultMapper.requestToResult(assessmentResultRequestDTO);
        assessmentResults.setAssignment(internshipAssignments);
        assessmentResults.setCriterion(evaluationCriteria);
        assessmentResults.setRound(assessmentRounds);
        assessmentResults.setEvaluatedBy(user);

        AssessmentResults creatAR = assessmentResultsRepository.save(assessmentResults);
        return assessmentResultMapper.resultToResponseDTO(creatAR);
    }

    @Override
    public AssessmentResultResponseDTO updateAR(Long id, BigDecimal score) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        UserPrinciple principal =
                (UserPrinciple) authentication.getPrincipal();

        assert principal != null;
        Users user = principal.getUser();

        AssessmentResults assessmentResults = assessmentResultsRepository.findById(id).orElseThrow(() ->
                    new NoResultException(ResponseWrapper.getMessage("error.assessment_result.not_found"))
                );
        if (!assessmentResults.getEvaluatedBy().equals(user)) {
            throw new AccessDeniedException(ResponseWrapper.getMessage("error.assessment_result.mentor_denied"));
        }
        assessmentResults.setScore(score);
        AssessmentResults updateAR = assessmentResultsRepository.save(assessmentResults);
        return assessmentResultMapper.resultToResponseDTO(updateAR);
    }
}
