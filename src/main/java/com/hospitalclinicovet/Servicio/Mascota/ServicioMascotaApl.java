package com.hospitalclinicovet.Servicio.Mascota;

import com.hospitalclinicovet.Excepciones.ResourceNotFoundException;
import com.hospitalclinicovet.Servicio.Ingreso.ServicioIngreso;
import com.hospitalclinicovet.dto.Mascota.MascotaDTO;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.Modelo.Mascota.Mascota;
import com.hospitalclinicovet.Repositorio.RepositorioIngreso;
import com.hospitalclinicovet.Repositorio.RepositorioMascota;
import jakarta.transaction.Transactional;
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
public class ServicioMascotaApl implements ServicioMascota {

    private final RepositorioMascota repositorioMascota;
    private final RepositorioIngreso repositorioIngreso;

    /**
     * Agrega una nueva mascota recibiendo los datos del formato JSON {@link MascotaDTO}.
     * La mascota se crea por defecto con la variable activa a true pero si la pone a false no se pueden hacer mas operacion con ella. Aun así se retornará un código 400 bad request em vez del 404. Para decir que aunque no se pueda acceder a su información el registro sigue en memoria.
     * @param mascotaDTO datos de la mascota a crear.
     * @return la nueva mascota agregada.
     * @throws IllegalArgumentException si el código de identificación ya existe.
     */
    @Override
    public Mascota agregarMascota(MascotaDTO mascotaDTO) {
        Optional<Mascota> existingMascota = repositorioMascota.findByCodigoIdentificacion(mascotaDTO.getCodigoIdentificacion());
        if (existingMascota.isPresent()) {
            throw new IllegalArgumentException("El código de identificación ya existe.");
        }
        return repositorioMascota.save(getMascota(mascotaDTO));
    }

    /**
     * Usando el id de una mascota obtienes su informacion.
     * @param id el id de la mascota
     * @return un Optional que contiene la mascota si se encuentra o vacio si no se encuentra.
     * @throws ResourceNotFoundException si la mascota no existe.
     * @throws IllegalArgumentException si la mascota no está activa.
     */
    @Override
    public Optional<Mascota> obtenerMascota(Long id) {
        return Optional.of(mascotaValida(id));
    }

    /**
     * Se obtiene una lista de ingresos de una mascota.
     * @param id el id de la mascota.
     * @return una lista con todos sus ingresos y la informacion de estos.
     * @throws ResourceNotFoundException si la mascota no existe.
     * @throws IllegalArgumentException si la mascota no está activa.
     */
    @Override
    public List<Ingreso> listarIngresoMascotas(Long id) {
        mascotaValida(id);
        return repositorioIngreso.findByMascotaId(id);
    }

    /**
     * Cambia el valor de la variable activa a false haciendo que no sea inaccesible para realizar cualquier operacion con ella.
     * @param id el id de la mascota.
     * @throws ResourceNotFoundException si la mascota no existe.
     * @throws IllegalArgumentException si la mascota no está activa.
     */
    @Override
    @Transactional
    public void eliminarMascota(Long id) {
        Mascota mascota = mascotaValida(id);
        mascota.setActiva(false);
        repositorioMascota.save(mascota);
    }

    /**
     * Función que utiliza el resto de operaciones al principio para saber si la mascota existe y esta activa
     * @param id el id de la mascota
     * @return true si existe y esta activa y false en caso contrario.
     */
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

    /**
     * Utiliza la informacion de {@link MascotaDTO} para crear una mascota
     * @param mascotaDTO clase con la informacion para crear una mascota
     * @return la nueva mascota creada.
     */
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
