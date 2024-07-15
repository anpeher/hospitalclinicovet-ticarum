package com.hospitalclinicovet.Servicio.Ingreso;

import com.hospitalclinicovet.Excepciones.ResourceNotFoundException;
import com.hospitalclinicovet.dto.Ingreso.ModIngresoDTO;
import com.hospitalclinicovet.dto.Ingreso.NuevoIngresoDTO;
import com.hospitalclinicovet.Modelo.Ingreso.Estado;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.Modelo.Mascota.Mascota;
import com.hospitalclinicovet.Repositorio.RepositorioIngreso;
import com.hospitalclinicovet.Servicio.Mascota.MascotaServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioIngresoApl implements ServicioIngreso {

    private final RepositorioIngreso repositorioIngreso;
    private MascotaServicio mascotaServicio;

    @Override
    public List<Ingreso> listaIngresos() {return repositorioIngreso.findAll();}

    @Override
    public Ingreso nuevoIngreso(NuevoIngresoDTO ingresoDTO) {

        Mascota mascota = mascotaServicio.ObtenerMascota(ingresoDTO.getIdMascota())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));
        if(!mascota.isActiva()){
            throw new IllegalArgumentException("La mascota no está activa.");
        }
        return repositorioIngreso.save(getIngreso(ingresoDTO, mascota));
    }

    @Override
    public Optional<Ingreso> ModificarIngreso(Long id, ModIngresoDTO modIngresoDTO) {
        return Optional.ofNullable(repositorioIngreso.findById(id).map(ingreso -> {
            if (modIngresoDTO.getFechaFinalizacion() != null) {
                ingreso.setFechaFinalizacion(stringToDate(modIngresoDTO.getFechaFinalizacion()));
            }
            if (modIngresoDTO.getEstado() != null) {
                Estado estadoJSON = Estado.valueOf(modIngresoDTO.getEstado());
                if (estadoJSON == Estado.FINALIZADO && ingreso.getFechaFinalizacion() == null) {
                    throw new IllegalArgumentException("Un ingreso en estado FINALIZADO debe tener una fecha de finalización.");
                }
                ingreso.setEstado(estadoJSON);
            }
            return repositorioIngreso.save(ingreso);
        }).orElseThrow(() -> new ResourceNotFoundException("El ingreso no existe.")));
    }

    @Override
    public void eliminarIngreso(Long id) {
        Ingreso ingreso = repositorioIngreso.findById(id).orElseThrow(() ->  new IllegalArgumentException("Ingreso no existe"));
        ingreso.setEstado(Estado.ANULADO);
        repositorioIngreso.save(ingreso);
    }

    private static Ingreso getIngreso(NuevoIngresoDTO ingresoDTO, Mascota mascota) {
        Ingreso ingreso = new Ingreso();
        ingreso.setEstado(Estado.ALTA);
        ingreso.setFechaAlta(stringToDate(ingresoDTO.getFechaAlta()));
        ingreso.setMascota(mascota);
        ingreso.setDniResponsable(mascota.getDniResponsable());
        return ingreso;
    }


    private static LocalDate stringToDate(String fechaString){
        try {
            return LocalDate.parse(fechaString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Debe ser yyyy-MM-dd");
        }
    }
}
