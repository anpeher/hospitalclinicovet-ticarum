package com.hospitalclinicovet.dto.Mascota;

import com.hospitalclinicovet.ReglasValidacion.DateValidation.ValidDate;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Constructor JSON para crear una mascota enviado por el cliente.
 */
@Getter
@Setter
public class MascotaDTO {

    /**
     * La especia de la mascota
     */
    @NotNull(message = "la especie es obligatoria")
    @NotBlank(message = "la especie es obligatoria")
    @Size(max = 100, message = "el campo especie no puede contener más de 100 palabras")
    @Pattern(regexp = "[A-Za-z- ñ]+", message = "El campo especie solo piede contener letras, espacios en blanco y el caracter -")
    private String especie;

    /**
     * La raza de la mascota
     */
    @NotNull(message = "la raza es obligatoria")
    @NotBlank(message = "la raza es obligatoria")
    @Size(max = 100, message = "el campo raza no puede contener más de 100 palabras")
    @Pattern(regexp = "[A-Za-z- ñ]+", message = "El campo raza solo piede contener letras, espacios en blanco y el caracter -")
    private String raza;

    /**
     * Su fecha de nacimiento. Sigue una validación personalizada.
     */
    @ValidDate
    private String fechaNacimiento;

    /**
     * Su codigo de verificacion
     */
    @NotNull(message = "el codigo de identificaciónes obligatorio")
    @NotBlank(message = "el codigo de identificaciónes obligatorio")
    @Size(max = 100, message = "el campo codigo identificación no puede contener más de 100 palabras")
    @Pattern(regexp = "\\d{3}[A-Z]{3}", message = "formato codigo de identificacion invalido, debe ser tres numeros y tres letras en mayúscula")
    private String codigoIdentificacion;

    /**
     * el dni del responsable de la mascota
     */
    @NotNull(message = "el dni del responsable obligatorio")
    @NotBlank(message = "el dni del responsable obligatorio")
    @Size(max = 100, message = "el campo dni del responsable no puede contener más de 100 palabras")
    @Pattern(regexp = "\\d{8}[A-Z]", message = "formato dni incorrecto")
    private String dniResponsable;

    /**
     * boolean para saber si la mascota esta activa, si lo esta por defecto
     */
    private boolean activa = true;
}
