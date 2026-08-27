package org.ra.qltt.repository;

import org.ra.qltt.model.entity.InternshipAssignments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipAssignmentsRepository extends JpaRepository<InternshipAssignments, Long> {
    List<InternshipAssignments> findByMentor_User_Id(long mentorUserId);

    List<InternshipAssignments> findByStudentUser_Id(long studentId);

    InternshipAssignments findByIdAndMentor_Id(Long id, Long mentorId);

    InternshipAssignments findByIdAndStudent_Id(Long id, Long studentId);

    boolean existsByStudentIdAndPhase_Id(Long studentId, Long phaseId);
}
