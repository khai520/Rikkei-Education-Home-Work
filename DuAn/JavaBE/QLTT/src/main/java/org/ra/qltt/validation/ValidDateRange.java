package org.ra.qltt.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface ValidDateRange {

    String message() default "Ngày bắt đầu không được lớn hơn ngày kết thúc";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}