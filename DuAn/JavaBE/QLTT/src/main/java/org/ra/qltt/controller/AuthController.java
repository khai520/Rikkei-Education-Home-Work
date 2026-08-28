package org.ra.qltt.controller;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.request.UserLoginDTO;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.response.UserLoginResponseDTO;
import org.ra.qltt.model.dto.response.UserResponseDTO;
import org.ra.qltt.service.AuthService;
import org.ra.qltt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserLoginDTO userLoginDTO) {
        UserLoginResponseDTO userLoginResponseDTO = authService.login(userLoginDTO);
        return new ResponseEntity<>(ResponseWrapper.success(userLoginResponseDTO,
                "success.resource.login",
                HttpStatus.OK.value()) , HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {

        String username = authentication.getName();

        UserResponseDTO userResponseDTO =
                userService.findUserByUserName(username);

        return new ResponseEntity<>(
                ResponseWrapper.success(
                        userResponseDTO,
                        "success.resource.find",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

}
