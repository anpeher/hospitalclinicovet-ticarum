package com.hospitalclinicovet.ReglasValidacion.EstadoValidation;

import com.hospitalclinicovet.Modelo.Ingreso.Estado;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EstadoValidator implements ConstraintValidator<ValidEstado, String> {

    @Override
    public void initialize(ValidEstado constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            Estado.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
