package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.config.MessageSourceConfig;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.model.dto.request.UserLoginDTO;
import org.ra.qltt.model.dto.response.UserLoginResponseDTO;
import org.ra.qltt.model.entity.Users;
import org.ra.qltt.repository.UserRepository;
import org.ra.qltt.security.UserPrinciple;
import org.ra.qltt.security.jwt.JwtProvider;
import org.ra.qltt.service.AuthService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtProvider jwtProvider;
    private final MessageSourceConfig  messageSourceConfig;

    @Override
    public UserLoginResponseDTO login(UserLoginDTO userLoginDTO) {

        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
        assert authentication != null;
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        assert userPrinciple != null;
        return UserLoginResponseDTO.builder()
                .fullName(userPrinciple.getUser().getFullName())
                .username(userPrinciple.getUsername())
                .type("Bearer")
                .token(jwtProvider.generateToken(userPrinciple))
                .role(userPrinciple.getUser().getRole())
                .build();
    }

    @Override
    public Users authenticationGetUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    String errorMessage = messageSourceConfig.messageSource()
                            .getMessage(
                                    "error.resource.not_found",
                                    new Object[]{"User", username},
                                    LocaleContextHolder.getLocale()
                            );

                    return new ResourceNotFoundException(errorMessage);
                });
    }


}
