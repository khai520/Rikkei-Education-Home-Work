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

            if (userRepository.findByUsername("admin").isEmpty()) {

                Users user = new Users();

                user.setUsername("admin");
                user.setPasswordHash(
                        new BCryptPasswordEncoder().encode("123456")
                );

                user.setEmail("admin@gmail.com");
                user.setFullName("Administrator");
                user.setPhoneNumber("0123456789");
                user.setActive(true);

                user.setRole("ADMIN");


                userRepository.save(user);
            }
        };
    }
}