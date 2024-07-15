package com.hospitalclinicovet.dto.Ingreso;

import com.hospitalclinicovet.ReglasValidacion.DateValidation.ValidDate;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NuevoIngresoDTO {

    @ValidDate
    private String fechaAlta;
    @NotNull(message = "El id de la mascota es obligatorio")
    @Min(value = 1, message = "el id mínimo es 1")
    private Long idMascota;
    @NotNull(message = "el dni del responsable obligatorio")
    @NotBlank(message = "el dni del responsable obligatorio")
    @Size(max = 100, message = "el campo dni del responsable no puede contener más de 100 palabras")
    @Pattern(regexp = "\\d{8}[A-Z]", message = "formato dni incorrecto")
    private String dni;

}
