package com.hospitalclinicovet.servicio.Ingreso;

import com.hospitalclinicovet.dto.Ingreso.ModIngresoDTO;
import com.hospitalclinicovet.dto.Ingreso.NuevoIngresoDTO;
import com.hospitalclinicovet.modelo.Ingreso.Estado;
import com.hospitalclinicovet.modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.modelo.Mascota.Mascota;
import com.hospitalclinicovet.repositorio.RepositorioIngreso;
import com.hospitalclinicovet.servicio.Mascota.MascotaServicio;
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

        Mascota mascota = mascotaServicio.obtenerMascota(ingresoDTO.getIdMascota())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));
        if(!mascota.isActiva()){
            throw new IllegalArgumentException("La mascota no está activa.");
        }
        Ingreso ingreso = getIngreso(ingresoDTO, mascota);
        return repositorioIngreso.save(ingreso);
    }

    @Override
    public Optional<Ingreso> ModificarIngreso(Long id, ModIngresoDTO modIngresoDTO) {
        if (!existeIngreso(id)) {
            throw new IllegalArgumentException("El ingreso no existe.");
        }
        return repositorioIngreso.findById(id).map(ingresoMemoria -> {
            if (modIngresoDTO.getFechaFinalizacion() != null) {
                ingresoMemoria.setFechaFinalizacion(stringToDate(modIngresoDTO.getFechaFinalizacion()));
            }
            if (modIngresoDTO.getEstado() != null){
                if (!EnumSet.of(Estado.ALTA, Estado.HOSPITALIZACION, Estado.FINALIZADO).contains(modIngresoDTO.getEstado())) {
                    throw new IllegalArgumentException("Estado inválido.");
                }
                if (modIngresoDTO.getEstado() == Estado.FINALIZADO && ingresoMemoria.getFechaFinalizacion() == null) {
                    throw new IllegalArgumentException("Un ingreso al que deseas cambiar el estado ha \"FINALIZADO\" debe tener una fecha de finalización.");
                }
                ingresoMemoria.setEstado(modIngresoDTO.getEstado());
            }
            return repositorioIngreso.save(ingresoMemoria);
        });

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
    private boolean existeIngreso(Long id){
        if (repositorioIngreso.existsById(id)) {
            return true;
        } else {
            throw new IllegalArgumentException("El ingreso no existe.");
        }
    }

    private static LocalDate stringToDate(String fechaString){
        try {
            return LocalDate.parse(fechaString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Debe ser yyyy-MM-dd");
        }
    }
}
