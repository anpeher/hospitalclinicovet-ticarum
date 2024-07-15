package com.hospitalclinicovet.Servicio.Mascota;

import com.hospitalclinicovet.dto.Mascota.MascotaDTO;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.Modelo.Mascota.Mascota;

import java.util.List;
import java.util.Optional;

public interface MascotaServicio {

    Mascota agregarMascota(MascotaDTO mascotaDTO);

    Optional<Mascota> obtenerMascota(long id);

    List<Ingreso> listarIngresoMascotas(long id);

    void eliminarMascota(long id);

}
