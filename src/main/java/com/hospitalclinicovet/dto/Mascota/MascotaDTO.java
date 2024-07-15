package com.hospitalclinicovet.dto.Mascota;

import com.hospitalclinicovet.ReglasValidacion.DateValidation.ValidDate;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MascotaDTO {

    @NotNull(message = "la especie es obligatoria")
    @NotBlank(message = "la especie es obligatoria")
    @Size(max = 100, message = "el campo especie no puede contener más de 100 palabras")
    @Pattern(regexp = "[A-Za-z- ñ]+", message = "El campo especie solo piede contener letras, espacios en blanco y el caracter -")
    private String especie;

    @NotNull(message = "la raza es obligatoria")
    @NotBlank(message = "la raza es obligatoria")
    @Size(max = 100, message = "el campo raza no puede contener más de 100 palabras")
    @Pattern(regexp = "[A-Za-z- ñ]+", message = "El campo raza solo piede contener letras, espacios en blanco y el caracter -")
    private String raza;

    @Transient
    private int edad;

    @ValidDate
    private String fechaNacimiento;

    @NotNull(message = "el codigo de identificaciónes obligatorio")
    @NotBlank(message = "el codigo de identificaciónes obligatorio")
    @Size(max = 100, message = "el campo codigo identificación no puede contener más de 100 palabras")
    @Pattern(regexp = "\\d{3}[A-Z]{3}", message = "formato codigo de identificacion invalido, debe ser tres numeros y tres letras en mayúscula")
    private String codigoIdentificacion;

    @NotNull(message = "el dni del responsable obligatorio")
    @NotBlank(message = "el dni del responsable obligatorio")
    @Size(max = 100, message = "el campo dni del responsable no puede contener más de 100 palabras")
    @Pattern(regexp = "\\d{8}[A-Z]", message = "formato dni incorrecto")
    private String dniResponsable;

    private boolean activa = true;
}
