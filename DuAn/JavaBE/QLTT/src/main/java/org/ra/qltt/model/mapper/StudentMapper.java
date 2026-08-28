package org.ra.qltt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.ra.qltt.model.dto.request.StudentRequestDTO;
import org.ra.qltt.model.dto.request.StudentUpdateRequestDTO;
import org.ra.qltt.model.dto.response.StudentResponseDTO;
import org.ra.qltt.model.entity.Students;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(source = "id", target = "studentId")

    // Users
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    @Mapping(source = "user.role", target = "role")
    @Mapping(source = "user.active", target = "active")

    // Students
    @Mapping(source = "studentCode", target = "studentCode")
    @Mapping(source = "major", target = "major")
    @Mapping(source = "className", target = "className")
    @Mapping(source = "dob", target = "dob")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    StudentResponseDTO studentToStudentResponseDTO(Students student);


    List<StudentResponseDTO> studentsToStudentResponseDTOs(
            List<Students> students
    );


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Students studentRequestToStudents(StudentRequestDTO request);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "studentCode" , ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateStudentFromDTO(
            StudentUpdateRequestDTO request,
            @MappingTarget Students student
    );
}