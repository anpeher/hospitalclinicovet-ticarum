package com.hospitalclinicovet.ReglasValidacion.EstadoValidation;

import com.hospitalclinicovet.Modelo.Ingreso.Estado;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador de estados para la anotación {@link ValidEstado}.
 * Se comprueba que el estado que se recibe es uno de los 4 posibles.
 * Se utiliza en el put de Ingreso, al recibir el estado del cliente.
 */
public class EstadoValidator implements ConstraintValidator<ValidEstado, String> {

    /**
     * Inicializa el validador. No es necesario utilizarlo
     *
     * @param constraintAnnotation la anotación que se usa en este validator
     */
    @Override
    public void initialize(ValidEstado constraintAnnotation) {
    }

    /**
     * Se comprueba que el estado ingresado es uno de los 4 psoibles casos.
     * Se aceptan nulos porque puedes dejar el campo en nulo.
     * @param estado el estado que entra por pantalla
     * @param context es el contexto de la validacion
     * @return true si es nulo o es uno de los 4 posibles estados. En caso contrario devuelve false.
     */
    @Override
    public boolean isValid(String estado, ConstraintValidatorContext context) {
        if (estado == null) {
            return true;
        }
        try {
            Estado.valueOf(estado);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
