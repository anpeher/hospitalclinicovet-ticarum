package com.hospitalclinicovet.dto.Ingreso;


import com.hospitalclinicovet.ReglasValidacion.EstadoValidation.ValidEstado;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModIngresoDTO {


    @ValidEstado
    private String estado;

    @Pattern(regexp = "^20\\d{2}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])$", message = "Formato de fecha inválido, debe ser yyyy-MM-dd")
    private String fechaFinalizacion;
}
