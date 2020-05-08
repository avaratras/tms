package com.hcm.tms.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UniqueValidator.class)
@Target( { TYPE})
@Retention(RUNTIME)
public @interface Unique {
    String message() default "Duplicate input: input already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends FieldValueExists> service();

    String serviceQualifier() default "";
    String fieldName();
}