package com.hospitalclinicovet.ReglasValidacion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "La fecha seleccionada es superior a la actual, por tanto es invalida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
