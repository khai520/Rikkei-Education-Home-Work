package org.ra.qltt.model.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ra.qltt.model.dto.response.UserResponseDTO;
import org.ra.qltt.model.entity.Users;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO userToUserResponseDTO(Users user);
    List<UserResponseDTO> usersToUserResponseDTOs(List<Users> users);

    @Mapping(target = "passwordHash" , ignore = true)
    Users userDTOToUsers(UserResponseDTO userResponseDTO);
}
