package com.hospitalclinicovet.servicio;

import com.hospitalclinicovet.modelo.Ingreso;
import com.hospitalclinicovet.modelo.Mascota;

import java.util.List;
import java.util.Optional;

public interface ServicioIngreso {

    List<Ingreso> listaIngresos();

    Ingreso nuevoIngreso(Ingreso ingreso);

    Optional<Ingreso> ModificarIngreso(Long id, Ingreso ingreso);

    boolean eliminarIngreso(Long id);

}
