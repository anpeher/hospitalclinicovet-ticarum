package com.hospitalclinicovet.dto.Ingreso;

import com.hospitalclinicovet.modelo.Ingreso.Estado;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModIngresoDTO {

    @NotBlank(message = "El estado no puede estar en blanco")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @NotBlank(message = "La fecha de finalizacion no puede estar en blanco")
    @Pattern(regexp = "^20\\d{2}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])$", message = "Formato de fecha inválido, debe ser yyyy-MM-dd")
    private String fechaFinalizacion;
}
