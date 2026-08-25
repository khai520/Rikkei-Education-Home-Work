package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ra.qltt.config.MessageSourceConfig;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.model.dto.response.UserResponseDTO;
import org.ra.qltt.model.entity.Users;
import org.ra.qltt.model.mapper.UserMapper;
import org.ra.qltt.repository.UserRepository;
import org.ra.qltt.service.UserService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MessageSourceConfig messageSourceConfig;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO findUserById(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> {
            String errorMessage = messageSourceConfig.messageSource()
                    .getMessage("error.resource.not_found", new Object[]{"User", id}, LocaleContextHolder.getLocale());
            return new ResourceNotFoundException(errorMessage);
        });
        System.out.println("===== CHECK =====");
        System.out.println("user.isActive() = " + user.isActive());

        UserResponseDTO dto = userMapper.userToUserResponseDTO(user);

        System.out.println("dto.isActive() = " + dto.isActive());
        return userMapper.userToUserResponseDTO(user);
    }
}
