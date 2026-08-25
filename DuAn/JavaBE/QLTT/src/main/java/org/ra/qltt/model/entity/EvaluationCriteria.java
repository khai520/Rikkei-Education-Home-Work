package org.ra.qltt.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "evaluation_criteria")
public class EvaluationCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "criterion_id")
    private Long id;

    @Column(name = "criterion_name" , length = 200 , nullable = false, unique = true)
    private String criteriaName;

    @Column(name = "description" , columnDefinition = "TEXT")
    private String description;

    @Column(name = "max_score", nullable = false , precision = 5 , scale = 2)
    private BigDecimal maxScore;

    @OneToMany(mappedBy = "criterion")
    private List<RoundCriteria> roundCriteria;

    @OneToMany(mappedBy = "criterion")
    private List<AssessmentResults> assessmentResults;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
