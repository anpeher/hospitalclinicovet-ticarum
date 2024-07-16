package com.hospitalclinicovet.ReglasValidacion.DateValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Anotación personalizada para validar fechas.
 * Con esto se logra que la fecha con la que se llega no es ni nula ni vacía.
 * El formato de la fecha debe ser "yyyy-mm-dd" y que no sea una fecha futura.
 */
@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    /**
     * Mensaje de error si la validacion falla
     * @return mensaje informando del error cometido
     */
    String message() default "La fecha no puede ser nula, vacía o superior a la actual. Formato obligatorio yyyy-mm-dd";

    /**
     * Grupos de validación a los que pertenece esta anotación.
     *
     * @return los grupos de validación
     */
    Class<?>[] groups() default {};

    /**
     * Carga útil adicional.
     *
     * @return Carga útil adicional
     */
    Class<? extends Payload>[] payload() default {};
}
