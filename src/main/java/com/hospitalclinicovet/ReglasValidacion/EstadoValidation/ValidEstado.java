package com.hospitalclinicovet.ReglasValidacion.EstadoValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación personalizada para validar estados.
 * Se comprueba que el estado que se recibe es uno de los 4 posibles.
 * Se utiliza en el put de Ingreso, al recibir el estado del cliente.
 */
@Documented
@Constraint(validatedBy = EstadoValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEstado {

    /**
     * Mensaje de error si la validacion falla
     * @return mensaje informando del error cometido
     */
    String message() default "Estado inválido";

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