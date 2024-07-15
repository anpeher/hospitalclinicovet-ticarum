package com.hospitalclinicovet.ReglasValidacion.DateValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

    @Override
    public void initialize(ValidDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String fecha, ConstraintValidatorContext context) {
        if (fecha == null){
            return false;
        }
        String fechaPattern = "^20\\d{2}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])$";
        Pattern pattern = Pattern.compile(fechaPattern);
        if (!pattern.matcher(fecha).matches()) {
            return false;
        }
        try {
            LocalDate fechaDate = LocalDate.parse(fecha);
            return !fechaDate.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
