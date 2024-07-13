package com.hospitalclinicovet.servicio;

import com.hospitalclinicovet.dto.NuevoIngresoDTO;
import com.hospitalclinicovet.modelo.Estado;
import com.hospitalclinicovet.modelo.Ingreso;
import com.hospitalclinicovet.modelo.Mascota;
import com.hospitalclinicovet.repositorio.RepositorioIngreso;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioIngresoApl implements ServicioIngreso{

    private final RepositorioIngreso repositorioIngreso;
    private MascotaServicio mascotaServicio;

    @Override
    public List<Ingreso> listaIngresos() {
        return repositorioIngreso.findAll();
    }

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
    public Optional<Ingreso> ModificarIngreso(Long id, Ingreso ingreso) {
        return repositorioIngreso.findById(id).map(ingresoMemoria -> {
            ingresoMemoria.setEstado(ingreso.getEstado());
            ingresoMemoria.setFechaFinalizacion(ingreso.getFechaFinalizacion());
            return repositorioIngreso.save(ingresoMemoria);
        });
    }

    @Override
    public boolean eliminarIngreso(Long id) {
        Ingreso ingreso = repositorioIngreso.findById(id).orElseThrow(() ->  new RuntimeException("Ingreso no existe"));
        ingreso.setEstado(Estado.ANULADO);
        repositorioIngreso.save(ingreso);
        return true;
    }

    private static Ingreso getIngreso(NuevoIngresoDTO ingresoDTO, Mascota mascota) {
        Ingreso ingreso = new Ingreso();
        ingreso.setEstado(Estado.ALTA);
        try {
            LocalDate fechaAlta = LocalDate.parse(ingresoDTO.getFechaAlta(), DateTimeFormatter.ISO_LOCAL_DATE);
            ingreso.setFechaAlta(fechaAlta);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Debe ser yyyy-MM-dd");
        }

        ingreso.setMascota(mascota);
        ingreso.setDniResponsable(mascota.getDniResponsable());
        return ingreso;
    }
}
