package com.hospitalclinicovet.Servicio.Ingreso;

import com.hospitalclinicovet.dto.Ingreso.ModIngresoDTO;
import com.hospitalclinicovet.dto.Ingreso.NuevoIngresoDTO;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;

import java.util.List;
import java.util.Optional;

public interface ServicioIngreso {

    List<Ingreso> listaIngresos();

    Ingreso nuevoIngreso(NuevoIngresoDTO ingresoDTO);

    Optional<Ingreso> modificarIngreso(Long id, ModIngresoDTO modIngresoDTO);

    void eliminarIngreso(Long id);

}
