package org.ra.qltt.test;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.entity.Users;
import org.ra.qltt.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;

    @Bean
    CommandLineRunner initData() {
        return args -> {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // ==================== ADMIN ====================
            if (userRepository.findByUsername("admin").isEmpty()) {

                Users admin = new Users();

                admin.setUsername("admin");
                admin.setPasswordHash(
                        passwordEncoder.encode("123456")
                );
                admin.setEmail("admin@gmail.com");
                admin.setFullName("Administrator");
                admin.setPhoneNumber("0123456789");
                admin.setActive(true);
                admin.setRole("ADMIN");

                userRepository.save(admin);
            }

            // ==================== STUDENT ====================
            if (userRepository.findByUsername("student").isEmpty()) {

                Users student = new Users();

                student.setUsername("student");
                student.setPasswordHash(
                        passwordEncoder.encode("123456")
                );
                student.setEmail("student@gmail.com");
                student.setFullName("Student");
                student.setPhoneNumber("0123456788");
                student.setActive(true);
                student.setRole("STUDENT");

                userRepository.save(student);
            }

            // ==================== MENTOR ====================
            if (userRepository.findByUsername("mentor").isEmpty()) {

                Users mentor = new Users();

                mentor.setUsername("mentor");
                mentor.setPasswordHash(
                        passwordEncoder.encode("123456")
                );
                mentor.setEmail("mentor@gmail.com");
                mentor.setFullName("Mentor");
                mentor.setPhoneNumber("0123456787");
                mentor.setActive(true);
                mentor.setRole("MENTOR");

                userRepository.save(mentor);
            }
        };
    }
}