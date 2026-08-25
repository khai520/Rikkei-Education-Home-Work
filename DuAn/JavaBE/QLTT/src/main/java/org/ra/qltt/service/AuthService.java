package org.ra.qltt.service;


import org.ra.qltt.model.dto.request.UserLoginDTO;
import org.ra.qltt.model.dto.response.UserLoginResponseDTO;
import org.ra.qltt.model.entity.Users;

public interface AuthService {
    UserLoginResponseDTO login(UserLoginDTO userLoginDTO);
    Users authenticationGetUser();
}
