package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ra.qltt.exception.ResourceAlreadyExistsException;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.enums.UserRole;
import org.ra.qltt.model.dto.request.UserRequestDTO;
import org.ra.qltt.model.dto.request.UserUpdateRequestDTO;
import org.ra.qltt.model.dto.response.UserResponseDTO;
import org.ra.qltt.model.entity.Users;
import org.ra.qltt.model.mapper.UserMapper;
import org.ra.qltt.repository.UserRepository;
import org.ra.qltt.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDTO> getUsers(){
        List<Users> users = userRepository.findAll();
        return userMapper.usersToUserResponseDTOs(users);
    }

    @Override
    public UserResponseDTO findUserById(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(ResponseWrapper.getMessage("error.user.not_found"))
        );

        return userMapper.userToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO findUserByUserName(String username) {
        Users user = userRepository.findByUsername(username).orElseThrow(()->
            new ResourceNotFoundException(ResponseWrapper.getMessage("error.user.not_found"))
        );
        return userMapper.userToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

        Users user = userMapper.userRequestToUsers(userRequestDTO);

        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new ResourceAlreadyExistsException(
                    ResponseWrapper.getMessage("error.user.username_exists")
            );
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    ResponseWrapper.getMessage("error.user.email_exists")
            );
        }
        user.setPasswordHash(new BCryptPasswordEncoder().encode(userRequestDTO.getPassword()));
        Users createdUser = userRepository.save(user);
        return userMapper.userToUserResponseDTO(createdUser);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(UserUpdateRequestDTO userRequestDTO , Long id) {
        Users user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ResponseWrapper.getMessage("error.user.not_found"))
        );

        if (userRepository.existsByEmailAndIdNot(userRequestDTO.getEmail() , id)) {
            throw new ResourceAlreadyExistsException(
                    ResponseWrapper.getMessage("error.user.email_exists")
            );
        }
        userMapper.updateUserFromDTO(userRequestDTO, user);
        Users updatedUser = userRepository.save(user);

        return userMapper.userToUserResponseDTO(updatedUser);
    }

    @Override
    public UserResponseDTO updateUserStatus(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(ResponseWrapper.getMessage("error.user.not_found"))
        );
        user.setActive(!user.isActive());
        Users updatedUser = userRepository.save(user);
        return userMapper.userToUserResponseDTO(updatedUser);
    }

    @Override
    public UserResponseDTO updateUserRole(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(ResponseWrapper.getMessage("error.user.not_found"))
                );
        if ("ADMIN".equals(user.getRole())
                && !user.getUsername().equals(user.getRole())) {

            throw new AccessDeniedException(
                    "ADMIN không được thay đổi vai trò của ADMIN khác"
            );
        }
        if (user.getUsername().equals(user.getRole())) {
            throw new AccessDeniedException(
                    "Không được tự thay đổi vai trò của chính mình"
            );
        }
        user.setRole(user.getRole().contains(UserRole.STUDENT.name()) ? UserRole.MENTOR.name() : UserRole.STUDENT.name());

        Users updatedUser = userRepository.save(user);

        return userMapper.userToUserResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        Users user = userRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(ResponseWrapper.getMessage("error.user.not_found"))
                );

        userRepository.delete(user);

    }

}
