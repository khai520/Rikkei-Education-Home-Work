package org.ra.qltt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.ra.qltt.model.dto.request.AssessmentResultRequestDTO;
import org.ra.qltt.model.dto.response.AssessmentResultResponseDTO;
import org.ra.qltt.model.entity.AssessmentResults;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssessmentResultMapper {

    AssessmentResultResponseDTO resultToResponseDTO(
            AssessmentResults result
    );

    List<AssessmentResultResponseDTO> resultsToResponseDTOs(
            List<AssessmentResults> results
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "evaluationDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AssessmentResults requestToResult(
            AssessmentResultRequestDTO request
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "evaluationDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateResult(
            AssessmentResultRequestDTO request,
            @MappingTarget AssessmentResults result
    );
}