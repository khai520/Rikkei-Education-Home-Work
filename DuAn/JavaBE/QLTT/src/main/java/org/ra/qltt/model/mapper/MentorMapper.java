package org.ra.qltt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.ra.qltt.model.dto.request.MentorRequestDTO;
import org.ra.qltt.model.dto.request.MentorUpdateRequestDTO;
import org.ra.qltt.model.dto.response.MentorResponseDTO;
import org.ra.qltt.model.entity.Mentors;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MentorMapper {

    @Mapping(source = "id", target = "mentorId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    @Mapping(source = "user.role", target = "role")
    @Mapping(source = "user.active", target = "active")
    MentorResponseDTO mentorToMentorResponseDTO(
            Mentors mentor
    );

    List<MentorResponseDTO> mentorsToMentorResponseDTOs(
            List<Mentors> mentors
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Mentors mentorRequestToMentors(
            MentorRequestDTO request
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateMentorFromDTO(
            MentorUpdateRequestDTO request,
            @MappingTarget Mentors mentor
    );
}