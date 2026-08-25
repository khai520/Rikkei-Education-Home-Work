package org.ra.qltt.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "username",length = 50,nullable = false,unique = true)
    private String username;

    @Column(name = "password_hash",nullable = false)
    private String passwordHash;

    @Column(name = "full_name" , length = 100 , nullable = false)
    private String fullName;

    @Column(name = "email" , length = 100 , nullable = false ,  unique = true)
    private String email;

    @Column(name = "phone_number" , length = 20)
    private String phoneNumber;

    @Column(name = "role", length = 50, nullable = false)
    private String role;

    @Column(name = "is_active")
    private boolean active;

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
