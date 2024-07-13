package com.hospitalclinicovet.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class NuevoIngresoDTO {
    private LocalDate fechaAlta;
    private Long idMascota;

}
