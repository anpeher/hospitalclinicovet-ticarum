package com.hospitalclinicovet.servicio.Ingreso;

import com.hospitalclinicovet.dto.Ingreso.ModIngresoDTO;
import com.hospitalclinicovet.dto.Ingreso.NuevoIngresoDTO;
import com.hospitalclinicovet.modelo.Ingreso.Ingreso;

import java.util.List;
import java.util.Optional;

public interface ServicioIngreso {

    List<Ingreso> listaIngresos();

    Ingreso nuevoIngreso(NuevoIngresoDTO ingresoDTO);

    Optional<Ingreso> ModificarIngreso(Long id, ModIngresoDTO modIngresoDTO);

    boolean eliminarIngreso(Long id);

}
