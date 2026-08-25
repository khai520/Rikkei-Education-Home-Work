package org.ra.qltt.repository;

import org.ra.qltt.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users getUsersByUsername(String username);
    Optional<Users> findByUsername (String username);

}
