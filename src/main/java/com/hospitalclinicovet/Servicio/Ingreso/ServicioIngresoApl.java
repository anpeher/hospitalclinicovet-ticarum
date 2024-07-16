package com.hospitalclinicovet.Servicio.Ingreso;

import com.hospitalclinicovet.Excepciones.ResourceNotFoundException;
import com.hospitalclinicovet.dto.Ingreso.ModIngresoDTO;
import com.hospitalclinicovet.dto.Ingreso.NuevoIngresoDTO;
import com.hospitalclinicovet.Modelo.Ingreso.Estado;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.Modelo.Mascota.Mascota;
import com.hospitalclinicovet.Repositorio.RepositorioIngreso;
import com.hospitalclinicovet.Servicio.Mascota.ServicioMascota;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

/**
 * Implementacion del servicio de ingreso del interfaz {@link ServicioIngreso}. Gestiona todas las operaciones relacionadas con los ingresos.
 */
@Service
@AllArgsConstructor
public class ServicioIngresoApl implements ServicioIngreso {

    private RepositorioIngreso repositorioIngreso;
    private ServicioMascota servicioMascota;

    /**
     * Obtiene una lista de todos los ingresos.
     * @return lista de ingresos totales.
     */
    @Override
    public List<Ingreso> listaIngresos() {return repositorioIngreso.findAll();}

    /**
     * Crea un nuevo ingreso usando {@link NuevoIngresoDTO}
     * @param ingresoDTO json con los datos para crear un nuevo ingreso.
     * @return el nuevo ingreso creado.
     * @throws IllegalArgumentException si la mascota no se encuentra, no está activa, o el DNI no coincide con el del responsable de la mascota.
     */
    @Override
    public Ingreso nuevoIngreso(NuevoIngresoDTO ingresoDTO) {

        Mascota mascota = servicioMascota.obtenerMascota(ingresoDTO.getIdMascota())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));
        if(!mascota.isActiva()){
            throw new IllegalArgumentException("La mascota no está activa.");
        }
        if(!mascota.getDniResponsable().equals(ingresoDTO.getDni())){
            throw new IllegalArgumentException("Solo el responsable de la mascota puede generar el ingreso.");
        }
        return repositorioIngreso.save(getIngreso(ingresoDTO, mascota));
    }

    /**
     * Modifica un Ingreso existente para cambiar el estado, la fecha de finalizacion o ambas. Utiliza el json personalizado {@link ModIngresoDTO}
     * @param id id del ingreso a cambiar.
     * @param modIngresoDTO los datos para modificar el ingreso.
     * @return el ingreso modificado o un Optional vacio si no se encuentra el ingreso.
     * @throws IllegalArgumentException si el estado es FINALIZADO y no tiene una fecha de finalización
     * @throws ResourceNotFoundException si el ingreso no existe.
     */
    @Override
    public Optional<Ingreso> modificarIngreso(Long id, ModIngresoDTO modIngresoDTO) {
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

    /**
     * Cambia el estado de un Ingreso ha ANULADO.
     * @param id id del ingreso que se va a cambiar.
     * @throws ResourceNotFoundException si el ingreso no existe.
     */
    @Override
    public void eliminarIngreso(Long id) {
        Ingreso ingreso = repositorioIngreso.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Ingreso no existe"));
        ingreso.setEstado(Estado.ANULADO);
        repositorioIngreso.save(ingreso);
    }

    /**
     * Se utiliza los datos de {@link NuevoIngresoDTO} para crear un nuevo ingreso.
     * @param ingresoDTO los datos para crear el nuevo ingreso.
     * @param mascota a mascota necesaria para crear un ingreso.
     * @return la nueva entidad ingreso creada.
     */
    private static Ingreso getIngreso(NuevoIngresoDTO ingresoDTO, Mascota mascota) {
        Ingreso ingreso = new Ingreso();
        ingreso.setEstado(Estado.ALTA);
        ingreso.setFechaAlta(stringToDate(ingresoDTO.getFechaAlta()));
        ingreso.setMascota(mascota);
        ingreso.setDniResponsable(mascota.getDniResponsable());
        return ingreso;
    }


    /**
     * Transforma un String en una fecha LocalDate.
     * @param fechaString fecha a convertir.
     * @return fecha convertida a LocalDate.
     * @throws IllegalArgumentException si el formato de la fecha es incorrecto.
     */
    private static LocalDate stringToDate(String fechaString){
        try {
            return LocalDate.parse(fechaString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Debe ser yyyy-MM-dd");
        }
    }
}
