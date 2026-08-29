package org.ra.qltt.repository;

import org.ra.qltt.model.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Students, Long> {
    @Query("""
    SELECT ia.student
    FROM InternshipAssignments ia
    WHERE ia.mentor.id = :mentorId
""")
    List<Students> findStudentsByMentorId(
            @Param("mentorId") Long mentorId
    );


    boolean existsByStudentCode(String studentCode);
}
