package org.ra.qltt.repository;

import org.ra.qltt.model.entity.Mentors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentors, Long> {
}
