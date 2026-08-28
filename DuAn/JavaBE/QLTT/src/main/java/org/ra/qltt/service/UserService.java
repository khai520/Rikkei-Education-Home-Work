package org.ra.qltt.service;

import org.ra.qltt.model.dto.request.UserRequestDTO;
import org.ra.qltt.model.dto.request.UserUpdateRequestDTO;
import org.ra.qltt.model.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getUsers();
    UserResponseDTO findUserById(Long id);
    UserResponseDTO findUserByUserName(String username);
    UserResponseDTO createUser( UserRequestDTO userRequestDTO);
    UserResponseDTO updateUser(UserUpdateRequestDTO userRequestDTO , Long id);
    UserResponseDTO updateUserStatus( Long id);
    UserResponseDTO updateUserRole (Long id);
    void deleteUser(Long id);
}
