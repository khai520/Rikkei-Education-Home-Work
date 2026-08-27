package org.ra.qltt.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "assessment_rounds")
public class AssessmentRounds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "round_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "phase_id" ,  nullable = false)
    private InternshipPhases phases;

    @Builder.Default
    @OneToMany(
            mappedBy = "round",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RoundCriteria> roundCriteria = new ArrayList<>();

    @OneToMany(mappedBy = "round")
    private List<AssessmentResults> assessmentResults;

    @Column(name = "round_name" , length = 100 , nullable = false)
    private String roundName;

    @Column(name = "start_date" , nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date" , nullable = false )
    private LocalDate endDate;

    @Column(name = "description" , columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

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
