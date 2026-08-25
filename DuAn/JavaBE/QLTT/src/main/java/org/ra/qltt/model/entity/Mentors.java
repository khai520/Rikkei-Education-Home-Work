package org.ra.qltt.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "mentors")
public class Mentors {

    @Id
    @Column(name = "mentor_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "mentor_id")
    private Users user;

    @Column(name = "department" , length = 100)
    private String department;

    @Column(name = "academic_rank" , length = 50)
    private String academicRank;

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
