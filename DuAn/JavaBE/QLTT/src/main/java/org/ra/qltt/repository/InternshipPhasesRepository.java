package org.ra.qltt.repository;

import org.ra.qltt.model.entity.InternshipPhases;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternshipPhasesRepository extends JpaRepository<InternshipPhases, Long> {
    boolean existsByPhaseName(String phaseName);

    boolean existsByPhaseNameAndIdNot(String phaseName, Long id);
}
