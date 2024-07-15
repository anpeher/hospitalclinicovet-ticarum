package com.hospitalclinicovet.dto.Ingreso;

import com.hospitalclinicovet.ReglasValidacion.DateValidation.ValidDate;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NuevoIngresoDTO {
    @NotNull(message = "La fecha de alta es obligatoria")
    @NotBlank(message = "La fecha de alta es obligatoria")
    @ValidDate
    @Pattern(regexp = "^20\\d{2}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])$", message = "Formato de fecha inválido, debe ser yyyy-MM-dd")
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
