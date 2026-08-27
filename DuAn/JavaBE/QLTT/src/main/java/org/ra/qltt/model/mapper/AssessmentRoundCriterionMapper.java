package org.ra.qltt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ra.qltt.model.dto.response.AssessmentRoundCriterionDTO;
import org.ra.qltt.model.entity.RoundCriteria;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssessmentRoundCriterionMapper {
    @Mapping(
            target = "criterionId",
            source = "criterion.id"
    )
    @Mapping(
            target = "criterionName",
            source = "criterion.criteriaName"
    )
    @Mapping(
            target = "weight",
            source = "weight"
    )
    AssessmentRoundCriterionDTO roundCriteriaToResponseDTO(
            RoundCriteria roundCriteria
    );

    List<AssessmentRoundCriterionDTO> roundCriteriaToResponseDTOs(
            List<RoundCriteria> roundCriteria);
}
