package com.hospitalclinicovet.dto.Ingreso;


import com.hospitalclinicovet.ReglasValidacion.EstadoValidation.ValidEstado;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * Constructor JSON para recibir los datos para modificar un ingreso
 */
@Getter
@Setter
public class ModIngresoDTO {


    /**
     * nuevo estado del ingreso. Sigue una validación personalizada.
     */
    @ValidEstado
    private String estado;

    /**
     * fecha de finalización del ingreso. No se comprueba si es de una fecha pasada o futura porque la mascota podría salir y no registrasrse o rectificar la fecha.
     */
    @Pattern(regexp = "^20\\d{2}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])$", message = "Formato de fecha inválido, debe ser yyyy-MM-dd")
    private String fechaFinalizacion;
}
