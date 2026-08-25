package org.ra.qltt.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "students")
public class Students {

    @Id
    @Column(name = "student_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private Users user;

    @Column(
            name = "student_code",
            length = 20,
            nullable = false,
            unique = true
    )
    private String studentCode;

    @Column(name = "major", length = 100)
    private String major;

    @Column(name = "class", length = 50)
    private String className;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "address", length = 255)
    private String address;

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