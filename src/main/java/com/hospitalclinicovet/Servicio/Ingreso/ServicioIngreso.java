package com.hospitalclinicovet.Servicio.Ingreso;

import com.hospitalclinicovet.Excepciones.ResourceNotFoundException;
import com.hospitalclinicovet.dto.Ingreso.ModIngresoDTO;
import com.hospitalclinicovet.dto.Ingreso.NuevoIngresoDTO;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de ingreso donde se definen las operaciones.
 */
public interface ServicioIngreso {

    /**
     * Obtiene una lista de todos los ingresos.
     * @return lista de ingresos totales.
     */
    List<Ingreso> listaIngresos();

    /**
     * Crea un nuevo ingreso usando {@link NuevoIngresoDTO}
     * @param ingresoDTO json con los datos para crear un nuevo ingreso.
     * @return el nuevo ingreso creado.
     * @throws IllegalArgumentException si la mascota no se encuentra, no está activa, o el DNI no coincide con el del responsable de la mascota.
     */
    Ingreso nuevoIngreso(NuevoIngresoDTO ingresoDTO);

    /**
     * Modifica un Ingreso existente para cambiar el estado, la fecha de finalizacion o ambas. Utiliza el json personalizado {@link ModIngresoDTO}
     * @param id id del ingreso a cambiar.
     * @param modIngresoDTO los datos para modificar el ingreso.
     * @return el ingreso modificado o un Optional vacio si no se encuentra el ingreso.
     * @throws IllegalArgumentException si el estado es FINALIZADO y no tiene una fecha de finalización
     * @throws ResourceNotFoundException si el ingreso no existe.
     */
    Optional<Ingreso> modificarIngreso(Long id, ModIngresoDTO modIngresoDTO);

    /**
     * Cambia el estado de un Ingreso ha ANULADO.
     * @param id id del ingreso que se va a cambiar.
     * @throws ResourceNotFoundException si el ingreso no existe.
     */
    void eliminarIngreso(Long id);

}
