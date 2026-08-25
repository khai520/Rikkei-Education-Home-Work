package org.ra.qltt.controller;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.response.ResponseWrapper;
import org.ra.qltt.model.dto.response.UserResponseDTO;
import org.ra.qltt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @RequestMapping("/api/auth/{id}")
    public ResponseEntity<?> me(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.findUserById(id);
        return new ResponseEntity<>(ResponseWrapper.success(userResponseDTO , "Lấy dữ liệu thành công",HttpStatus.OK.value()), HttpStatus.OK);
    }
}
