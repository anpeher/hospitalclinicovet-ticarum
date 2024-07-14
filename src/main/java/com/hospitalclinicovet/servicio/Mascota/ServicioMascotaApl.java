package com.hospitalclinicovet.servicio.Mascota;

import com.hospitalclinicovet.Excepciones.ResourceNotFoundException;
import com.hospitalclinicovet.dto.Mascota.MascotaDTO;
import com.hospitalclinicovet.modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.modelo.Mascota.Mascota;
import com.hospitalclinicovet.repositorio.RepositorioIngreso;
import com.hospitalclinicovet.repositorio.RepositorioMascota;
import jakarta.transaction.Transactional;
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
    private final RepositorioIngreso repositorioIngreso;

    @Override
    public Mascota agregarMascota(MascotaDTO mascotaDTO) {
        Optional<Mascota> existingMascota = repositorioMascota.findByCodigoIdentificacion(mascotaDTO.getCodigoIdentificacion());
        if (existingMascota.isPresent()) {
            throw new IllegalArgumentException("El código de identificación ya existe.");
        }
        return repositorioMascota.save(getMascota(mascotaDTO));
    }

    @Override
    public Optional<Mascota> ObtenerMascota(long id) {
        return Optional.of(mascotaValida(id));
    }

    @Override
    public List<Ingreso> ListarIngresoMascotas(long id) {
        mascotaValida(id);
        return repositorioIngreso.findByMascotaId(id);
    }

    @Override
    @Transactional
    public void eliminarMascota(long id) {
        Mascota mascota = mascotaValida(id);
        mascota.setActiva(false);
        repositorioMascota.save(mascota);
    }

    private Mascota mascotaValida(Long id){
        if (repositorioMascota.existsById(id)) {
            Optional<Mascota> mascota = repositorioMascota.findById(id);
            if (mascota.isPresent() && mascota.get().isActiva()) {
                return mascota.get();
            } else {
                throw new IllegalArgumentException("La mascota no está activa.");
            }
        } else {
            throw new ResourceNotFoundException("La mascota no existe.");
        }
    }

    private static Mascota getMascota(MascotaDTO mascotaDTO) {
        Mascota mascota = new Mascota();
        mascota.setEspecie(mascotaDTO.getEspecie());
        mascota.setRaza(mascotaDTO.getRaza());
        mascota.setCodigoIdentificacion(mascotaDTO.getCodigoIdentificacion());
        mascota.setDniResponsable(mascotaDTO.getDniResponsable());
        mascota.setActiva(mascotaDTO.isActiva());
        mascota.setFechaNacimiento(stringToDate(mascotaDTO.getFechaNacimiento()));
        return mascota;
    }

    private static LocalDate stringToDate(String fechaString){
    try {
        return LocalDate.parse(fechaString, DateTimeFormatter.ISO_LOCAL_DATE);
    } catch (DateTimeParseException e) {
        throw new IllegalArgumentException("Formato de fecha inválido. Debe ser yyyy-MM-dd");
    }
    }

}
