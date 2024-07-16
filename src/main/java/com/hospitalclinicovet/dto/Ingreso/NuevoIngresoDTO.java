package com.hospitalclinicovet.dto.Ingreso;

import com.hospitalclinicovet.ReglasValidacion.DateValidation.ValidDate;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Constructor JSON para recibir los datos para crear un ingreso.
 */
@Getter
@Setter
public class NuevoIngresoDTO {

    /**
     * Su fecha de alta. Sigue una validación personalizada.
     */
    @ValidDate
    private String fechaAlta;

    /**
     * el id de la mascota para poder buscarla he ingresarla
     */
    @NotNull(message = "El id de la mascota es obligatorio")
    @Min(value = 1, message = "el id mínimo es 1")
    private Long idMascota;

    /**
     * dni del responsable de la mascota.
     */
    @NotNull(message = "el dni del responsable obligatorio")
    @NotBlank(message = "el dni del responsable obligatorio")
    @Size(max = 100, message = "el campo dni del responsable no puede contener más de 100 palabras")
    @Pattern(regexp = "\\d{8}[A-Z]", message = "formato dni incorrecto")
    private String dni;

}
