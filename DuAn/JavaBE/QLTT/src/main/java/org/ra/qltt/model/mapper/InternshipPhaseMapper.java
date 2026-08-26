package org.ra.qltt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.ra.qltt.model.dto.request.InternshipPhaseRequestDTO;
import org.ra.qltt.model.dto.response.InternshipPhaseResponseDTO;
import org.ra.qltt.model.entity.InternshipPhases;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InternshipPhaseMapper {

    @Mapping(source = "id" , target = "phaseId")
    InternshipPhaseResponseDTO internshipPhaseToResponseDTO(
            InternshipPhases phase
    );

    List<InternshipPhaseResponseDTO> internshipPhasesToResponseDTOs(
            List<InternshipPhases> phases
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    InternshipPhases requestToInternshipPhase(
            InternshipPhaseRequestDTO request
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateInternshipPhase(
            InternshipPhaseRequestDTO request,
            @MappingTarget InternshipPhases phase
    );
}