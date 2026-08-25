package org.ra.qltt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.ra.qltt.model.dto.request.RoundCriterionRequestDTO;
import org.ra.qltt.model.dto.response.RoundCriterionResponseDTO;
import org.ra.qltt.model.entity.RoundCriteria;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoundCriteriaMapper {

    RoundCriterionResponseDTO roundCriteriaToResponseDTO(
            RoundCriteria roundCriteria
    );

    List<RoundCriterionResponseDTO> roundCriteriasToResponseDTOs(
            List<RoundCriteria> roundCriterias
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RoundCriteria requestToRoundCriteria(
            RoundCriterionRequestDTO request
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateRoundCriteria(
            RoundCriterionRequestDTO request,
            @MappingTarget RoundCriteria roundCriteria
    );
}