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

}
