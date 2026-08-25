package org.ra.qltt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.ra.qltt.model.dto.request.UserRequestDTO;
import org.ra.qltt.model.dto.response.UserResponseDTO;
import org.ra.qltt.model.entity.Users;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity -> Response
    UserResponseDTO userToUserResponseDTO(Users user);

    List<UserResponseDTO> usersToUserResponseDTOs(List<Users> users);

    // Request -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Users userRequestToUsers(UserRequestDTO request);

    // Update Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateUserFromDTO(
            UserRequestDTO request,
            @MappingTarget Users user
    );
}