package org.ra.qltt.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "assessment_results")
public class AssessmentResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private InternshipAssignments assignment;

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    private AssessmentRounds round;

    @ManyToOne
    @JoinColumn(name = "criterion_id", nullable = false)
    private EvaluationCriteria criterion;

    @ManyToOne
    @JoinColumn(name = "evaluated_by", nullable = false)
    private Users evaluatedBy;

    @Column(name = "score", nullable = false)
    private BigDecimal score;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "evaluation_date")
    private LocalDateTime evaluationDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        evaluationDate = now;
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
