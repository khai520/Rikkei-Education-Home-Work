package org.ra.qltt.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.ra.qltt.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users getUsersByUsername(String username);
    Optional<Users> findByUsername (String username);

    boolean existsByUsername(@NotBlank(message = "Username không được để trống") @Size(max = 50, message = "Username không được vượt quá 50 ký tự") String username);

    boolean existsByEmail(@NotBlank(message = "Email không được để trống") @Email(message = "Email không hợp lệ") @Size(max = 100) String email);

    boolean existsByEmailAndIdNot(String email, long id);
}
