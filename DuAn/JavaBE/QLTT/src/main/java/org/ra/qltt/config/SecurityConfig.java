package org.ra.qltt.config;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.security.jwt.CustomAccessDenied;
import org.ra.qltt.security.jwt.JwtAuthTokenFilter;
import org.ra.qltt.security.jwt.JwtEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthTokenFilter jwtAuthTokenFilter;
    private final CustomAccessDenied customAccessDenied;
    private final JwtEntryPoint jwtEntryPoint;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> {

                    // ==================== AUTH ====================

                    auth.requestMatchers(HttpMethod.POST, "/api/auth/login")
                            .permitAll();

                    auth.requestMatchers(HttpMethod.GET, "/api/auth/me")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");


                    // ==================== USERS ====================

                    auth.requestMatchers(HttpMethod.GET, "/api/users")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.GET, "/api/users/*")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.POST, "/api/users")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.PUT, "/api/users/*")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.PUT, "/api/users/*/status")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.PUT, "/api/users/*/role")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.DELETE, "/api/users/*")
                            .hasRole("ADMIN");


                    // ==================== STUDENTS ====================

                    auth.requestMatchers(HttpMethod.GET, "/api/students")
                            .hasAnyRole("ADMIN", "MENTOR");

                    auth.requestMatchers(HttpMethod.GET, "/api/students/*")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.POST, "/api/students")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.PUT, "/api/students/*")
                            .hasAnyRole("ADMIN", "STUDENT");


                    // ==================== MENTORS ====================

                    auth.requestMatchers(HttpMethod.GET, "/api/mentors")
                            .hasAnyRole("ADMIN", "STUDENT");

                    auth.requestMatchers(HttpMethod.GET, "/api/mentors/*")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.POST, "/api/mentors")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.PUT, "/api/mentors/*")
                            .hasAnyRole("ADMIN", "MENTOR");


                    // ==================== INTERNSHIP PHASES ====================

                    auth.requestMatchers(HttpMethod.GET, "/api/internship_phases")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.GET, "/api/internship_phases/*")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.POST, "/api/internship_phases")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.PUT, "/api/internship_phases/*")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.DELETE, "/api/internship_phases/*")
                            .hasRole("ADMIN");


                    // ==================== EVALUATION CRITERIA ====================

                    auth.requestMatchers(HttpMethod.GET, "/api/evaluation_criteria")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.GET, "/api/evaluation_criteria/*")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.POST, "/api/evaluation_criteria")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.PUT, "/api/evaluation_criteria/*")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.DELETE, "/api/evaluation_criteria/*")
                            .hasRole("ADMIN");


                    // ==================== ASSESSMENT ROUNDS ====================

                    auth.requestMatchers(HttpMethod.GET, "/api/assessment_rounds")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.GET, "/api/assessment_rounds/*")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.POST, "/api/assessment_rounds")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.PUT, "/api/assessment_rounds/*")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.DELETE, "/api/assessment_rounds/*")
                            .hasRole("ADMIN");


                    // ==================== ROUND CRITERIA ====================

                    auth.requestMatchers(HttpMethod.GET, "/api/round_criteria")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.GET, "/api/round_criteria/*")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.POST, "/api/round_criteria")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.PUT, "/api/round_criteria/*")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.DELETE, "/api/round_criteria/*")
                            .hasRole("ADMIN");


                    // ==================== INTERNSHIP ASSIGNMENTS ====================

                    auth.requestMatchers(HttpMethod.GET, "/api/internship_assignments")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.GET, "/api/internship_assignments/*")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.POST, "/api/internship_assignments")
                            .hasRole("ADMIN");

                    auth.requestMatchers(HttpMethod.PUT, "/api/internship_assignments/*/status")
                            .hasRole("ADMIN");


                    // ==================== ASSESSMENT RESULTS ====================

                    auth.requestMatchers(HttpMethod.GET, "/api/assessment_results")
                            .hasAnyRole("ADMIN", "MENTOR", "STUDENT");

                    auth.requestMatchers(HttpMethod.POST, "/api/assessment_results")
                            .hasRole("MENTOR");

                    auth.requestMatchers(HttpMethod.PUT, "/api/assessment_results/*")
                            .hasRole("MENTOR");
                }).sessionManagement(auth ->
                        auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint).accessDeniedHandler(customAccessDenied))
                .addFilterBefore(
                        jwtAuthTokenFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }
}
