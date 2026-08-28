package org.ra.qltt.repository;

import org.ra.qltt.model.entity.AssessmentResults;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentResultsRepository extends JpaRepository<AssessmentResults, Long> {

    List<AssessmentResults> findByAssignment_Mentor_Id(Long assignmentMentorId);

    List<AssessmentResults> findByAssignment_Student_Id(Long assignmentStudentId);

    boolean existsByAssignmentIdAndCriterionIdAndRoundId(Long assignmentId, Long criterionId, Long roundId);
}
