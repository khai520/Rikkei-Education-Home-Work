package org.ra.qltt.repository;

import org.ra.qltt.model.entity.EvaluationCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteria, Long> {
    boolean existsByCriteriaName(String criteriaName);

    boolean existsByCriteriaNameAndIdNot(String criteriaName, Long id);
}
