package com.hospitalclinicovet.servicio;

import com.hospitalclinicovet.modelo.Mascota;
import com.hospitalclinicovet.repositorio.RepositorioMascota;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioMascotaApl implements MascotaServicio {

    private final RepositorioMascota repositorioMascota;

    @Override
    public Mascota agregarMascota(Mascota mascota) {
        return repositorioMascota.save(mascota);
    }

    @Override
    public Optional<Mascota> ObtenerMascota(Long id) {
        return Optional.of(repositorioMascota.findById(id).orElseThrow(() ->  new RuntimeException("Mascota no existe")));
    }

    @Override
    public List<Mascota> ListarMascotas() {
        return null;
    }

    @Override
    public boolean eliminarMascota(Long id) {
        Mascota mascota = repositorioMascota.findById(id).orElseThrow(() ->  new RuntimeException("Mascota no existe"));
        mascota.setActiva(false);
        repositorioMascota.save(mascota);
        return true;
    }

    @Override
    public Optional<Mascota> obtenerMascota(Long id) {
        return Optional.of(repositorioMascota.findById(id).orElseThrow(() ->  new RuntimeException("Mascota no existe")));
    }
}
