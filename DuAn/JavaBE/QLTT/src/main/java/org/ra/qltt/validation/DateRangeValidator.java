package org.ra.qltt.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class DateRangeValidator
        implements ConstraintValidator<ValidDateRange, Object> {

    private String startDateField;
    private String endDateField;

    @Override
    public void initialize(ValidDateRange annotation) {
        this.startDateField = annotation.startDate();
        this.endDateField = annotation.endDate();
    }

    @Override
    public boolean isValid(
            Object object,
            ConstraintValidatorContext context
    ) {
        if (object == null) {
            return true;
        }

        try {
            Field startField = object.getClass()
                    .getDeclaredField(startDateField);

            Field endField = object.getClass()
                    .getDeclaredField(endDateField);

            startField.setAccessible(true);
            endField.setAccessible(true);

            LocalDate startDate = (LocalDate) startField.get(object);
            LocalDate endDate = (LocalDate) endField.get(object);

            if (startDate == null || endDate == null) {
                return true;
            }

            if (startDate.isAfter(endDate)) {

                context.disableDefaultConstraintViolation();

                context.buildConstraintViolationWithTemplate(
                                context.getDefaultConstraintMessageTemplate()
                        )
                        .addPropertyNode(startDateField)
                        .addConstraintViolation();

                return false;
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}