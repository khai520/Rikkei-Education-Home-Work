package org.ra.qltt.controller;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.config.MessageSourceConfig;
import org.ra.qltt.model.dto.request.UserLoginDTO;
import org.ra.qltt.model.dto.response.ResponseWrapper;
import org.ra.qltt.model.dto.response.UserLoginResponseDTO;
import org.ra.qltt.repository.UserRepository;
import org.ra.qltt.service.AuthService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MessageSourceConfig messageSourceConfig;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserLoginDTO userLoginDTO) {
        UserLoginResponseDTO userLoginResponseDTO = authService.login(userLoginDTO);
        String successMessage = messageSourceConfig.messageSource()
                .getMessage("success.resource.login",
                        new Object[]{"Login",
                        userLoginResponseDTO},
                        LocaleContextHolder.getLocale());
        return new ResponseEntity<>(ResponseWrapper.success(userLoginResponseDTO,
                successMessage,
                HttpStatus.OK.value()) , HttpStatus.OK);
    }



}
