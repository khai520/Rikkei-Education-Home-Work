package org.ra.qltt.service;

import org.ra.qltt.model.dto.response.UserResponseDTO;

public interface UserService {
    UserResponseDTO findUserById(Long id);
}
