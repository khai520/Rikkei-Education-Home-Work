package org.ra.qltt.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.request.UserRequestDTO;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.response.UserResponseDTO;
import org.ra.qltt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam(name = "roles", required = false) String roles){
        List<UserResponseDTO> users = userService.getUsers();
        if(roles != null && !roles.trim().isEmpty()){
            users = users.stream().filter(user -> user.getRole().contains(roles)).collect(Collectors.toList());
        }
        return ResponseEntity.ok(ResponseWrapper.success(users,"success.resource.all" ,HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        UserResponseDTO user = userService.findUserById(id);
        return ResponseEntity.ok(ResponseWrapper.success(user,"success.resource.find" ,HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO user = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(ResponseWrapper.success(user,"success.resource.create",HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequestDTO userRequestDTO , @PathVariable Long id){
        UserResponseDTO user = userService.updateUser(userRequestDTO,id);
        return ResponseEntity.ok(ResponseWrapper.success(user,"success.resource.update",HttpStatus.OK.value()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long id){
        UserResponseDTO user = userService.updateUserStatus(id);
        return ResponseEntity.ok(ResponseWrapper.success(user,"success.resource.update",HttpStatus.OK.value()));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id){
        UserResponseDTO user = userService.updateUserRole(id);
        return ResponseEntity.ok(ResponseWrapper.success(user,"success.resource.update",HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(ResponseWrapper.success(null,"success.resource.delete",HttpStatus.OK.value()));
    }
}
