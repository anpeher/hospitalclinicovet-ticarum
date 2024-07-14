package com.hospitalclinicovet.servicio.Mascota;

import com.hospitalclinicovet.dto.Mascota.MascotaDTO;
import com.hospitalclinicovet.modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.modelo.Mascota.Mascota;

import java.util.List;
import java.util.Optional;

public interface MascotaServicio {

    Mascota agregarMascota(MascotaDTO mascotaDTO);

    Optional<Mascota> ObtenerMascota(long id);

    List<Ingreso> ListarIngresoMascotas(long id);

    void eliminarMascota(long id);

}
