package org.ra.qltt.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ra.qltt.model.dto.request.AssessmentRoundRequestDTO;

public class DateRangeValidator
        implements ConstraintValidator<ValidDateRange, AssessmentRoundRequestDTO> {

    @Override
    public boolean isValid(
            AssessmentRoundRequestDTO request,
            ConstraintValidatorContext context
    ) {

        if (request == null
                || request.getStartDate() == null
                || request.getEndDate() == null) {

            return true;
        }

        return !request.getStartDate()
                .isAfter(request.getEndDate());
    }
}