package com.hospitalclinicovet.servicio;

import com.hospitalclinicovet.modelo.Mascota;

import java.util.List;
import java.util.Optional;

public interface MascotaServicio {

    Mascota agregarMascota(Mascota mascota);

    Optional<Mascota> ObtenerMascota(Long id);

    List<Mascota> ListarMascotas();

    boolean eliminarMascota(Long id);

    Optional<Mascota> obtenerMascota(Long id);
}
