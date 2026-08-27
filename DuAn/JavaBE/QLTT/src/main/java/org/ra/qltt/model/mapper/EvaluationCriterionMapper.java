package org.ra.qltt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.ra.qltt.model.dto.request.EvaluationCriterionRequestDTO;
import org.ra.qltt.model.dto.response.EvaluationCriterionResponseDTO;
import org.ra.qltt.model.entity.EvaluationCriteria;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EvaluationCriterionMapper {

    @Mapping(source = "id" , target = "criterionId")
    EvaluationCriterionResponseDTO criterionToResponseDTO(
            EvaluationCriteria criterion
    );

    List<EvaluationCriterionResponseDTO> criteriaToResponseDTOs(
            List<EvaluationCriteria> criteria
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roundCriteria", ignore = true)
    @Mapping(target = "assessmentResults", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    EvaluationCriteria requestToCriterion(
            EvaluationCriterionRequestDTO request
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roundCriteria", ignore = true)
    @Mapping(target = "assessmentResults", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateCriterion(
            EvaluationCriterionRequestDTO request,
            @MappingTarget EvaluationCriteria criterion
    );
}