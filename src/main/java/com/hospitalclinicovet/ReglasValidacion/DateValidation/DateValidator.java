package com.hospitalclinicovet.ReglasValidacion.DateValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

    @Override
    public void initialize(ValidDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String fecha, ConstraintValidatorContext context) {
        try {
            LocalDate fechaDate = LocalDate.parse(fecha);
            return !fechaDate.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
