package com.hospitalclinicovet.servicio;

import com.hospitalclinicovet.dto.MascotaDTO;
import com.hospitalclinicovet.modelo.Mascota;
import com.hospitalclinicovet.repositorio.RepositorioMascota;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioMascotaApl implements MascotaServicio {

    private final RepositorioMascota repositorioMascota;

    @Override
    public Mascota agregarMascota(MascotaDTO mascotaDTO) {
        Optional<Mascota> existingMascota = repositorioMascota.findByCodigoIdentificacion(mascotaDTO.getCodigoIdentificacion());
        if (existingMascota.isPresent()) {
            throw new IllegalArgumentException("El código de identificación ya existe.");
        }
        Mascota mascota = new Mascota();
        mascota.setEspecie(mascotaDTO.getEspecie());
        mascota.setRaza(mascotaDTO.getRaza());
        mascota.setCodigoIdentificacion(mascotaDTO.getCodigoIdentificacion());
        mascota.setDniResponsable(mascotaDTO.getDniResponsable());
        mascota.setActiva(mascotaDTO.isActiva());

        try {
            LocalDate fechaNacimiento = LocalDate.parse(mascotaDTO.getFechaNacimiento(), DateTimeFormatter.ISO_LOCAL_DATE);
            mascota.setFechaNacimiento(fechaNacimiento);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Debe ser yyyy-MM-dd");
        }
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
