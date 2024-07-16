package com.hospitalclinicovet.Servicio.Mascota;

import com.hospitalclinicovet.Excepciones.ResourceNotFoundException;
import com.hospitalclinicovet.dto.Mascota.MascotaDTO;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.Modelo.Mascota.Mascota;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de mascota donde se definen las operaciones.
 */
public interface ServicioMascota {

    /**
     * Agrega una nueva mascota recibiendo los datos del formato JSON {@link MascotaDTO}.
     * La mascota se crea por defecto con la variable activa a true pero si la pone a false no se pueden hacer mas operacion con ella. Aun así se retornará un código 400 bad request em vez del 404. Para decir que aunque no se pueda acceder a su información el registro sigue en memoria.
     * @param mascotaDTO datos de la mascota a crear.
     * @return la nueva mascota agregada.
     * @throws IllegalArgumentException si el código de identificación ya existe.
     */
    Mascota agregarMascota(MascotaDTO mascotaDTO);

    /**
     * Usando el id de una mascota obtienes su informacion.
     * @param id el id de la mascota
     * @return un Optional que contiene la mascota si se encuentra o vacio si no se encuentra.
     * @throws ResourceNotFoundException si la mascota no existe.
     * @throws IllegalArgumentException si la mascota no está activa.
     */
    Optional<Mascota> obtenerMascota(Long id);

    /**
     * Se obtiene una lista de ingresos de una mascota.
     * @param id el id de la mascota.
     * @return una lista con todos sus ingresos y la informacion de estos.
     * @throws ResourceNotFoundException si la mascota no existe.
     * @throws IllegalArgumentException si la mascota no está activa.
     */
    List<Ingreso> listarIngresoMascotas(Long id);

    /**
     * Cambia el valor de la variable activa a false haciendo que no sea inaccesible para realizar cualquier operacion con ella.
     * @param id el id de la mascota.
     * @throws ResourceNotFoundException si la mascota no existe.
     * @throws IllegalArgumentException si la mascota no está activa.
     */
    void eliminarMascota(Long id);

}
