package com.hospitalclinicovet.servicio;

import com.hospitalclinicovet.dto.NuevoIngresoDTO;
import com.hospitalclinicovet.modelo.Ingreso;

import java.util.List;
import java.util.Optional;

public interface ServicioIngreso {

    List<Ingreso> listaIngresos();

    Ingreso nuevoIngreso(NuevoIngresoDTO ingresoDTO);

    Optional<Ingreso> ModificarIngreso(Long id, Ingreso ingreso);

    boolean eliminarIngreso(Long id);

}
