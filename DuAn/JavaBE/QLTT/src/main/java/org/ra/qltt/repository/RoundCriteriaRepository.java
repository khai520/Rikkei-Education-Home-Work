package org.ra.qltt.repository;


import org.ra.qltt.model.entity.RoundCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;


public interface RoundCriteriaRepository extends JpaRepository<RoundCriteria, Long> {


    RoundCriteria findFirstByCriterion_IdAndRound_Id(Long criterionId, Long roundId);

    @Query("""
    SELECT COALESCE(SUM(rc.weight), 0)
    FROM RoundCriteria rc
    WHERE rc.round.id = :roundId
""")
    BigDecimal sumWeightByRoundId(@Param("roundId") Long roundId);
}
