package org.ra.qltt.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "internship_assignments")
public class InternshipAssignments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Students student;

    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = false)
    private Mentors mentor;

    @ManyToOne
    @JoinColumn(name = "phase_id", nullable = false)
    private InternshipPhases phase;

    @OneToMany(mappedBy = "assignment")
    private List<AssessmentResults> assessmentResults;

    @Column(name = "assigned_date")
    private LocalDateTime assignedDate;

    @Column(name = "status")
    private String status;

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
