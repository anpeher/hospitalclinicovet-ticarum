package com.hospitalclinicovet.servicio;

import com.hospitalclinicovet.dto.MascotaDTO;
import com.hospitalclinicovet.modelo.Ingreso;
import com.hospitalclinicovet.modelo.Mascota;

import java.util.List;
import java.util.Optional;

public interface MascotaServicio {

    Mascota agregarMascota(MascotaDTO mascotaDTO);

    Optional<Mascota> ObtenerMascota(long id);

    List<Ingreso> ListarIngresoMascotas(long id);

    boolean eliminarMascota(long id);

    Optional<Mascota> obtenerMascota(long id);
}
