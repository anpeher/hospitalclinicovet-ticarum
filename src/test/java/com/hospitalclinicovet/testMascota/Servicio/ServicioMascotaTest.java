package com.hospitalclinicovet.testMascota.Servicio;

import com.hospitalclinicovet.Excepciones.ResourceNotFoundException;
import com.hospitalclinicovet.dto.Mascota.MascotaDTO;
import com.hospitalclinicovet.Modelo.Mascota.Mascota;
import com.hospitalclinicovet.Repositorio.RepositorioMascota;
import com.hospitalclinicovet.Servicio.Mascota.ServicioMascotaApl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de prueba para el servicio de mascotas.
 */
public class ServicioMascotaTest {

    @Mock
    private RepositorioMascota repositorioMascota;

    @InjectMocks
    private ServicioMascotaApl servicioMascota;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que verifica la eliminación de una mascota no existente.
     */
    @Test
    void testEliminarMascotaNoExistente() {
        long id = 1L;

        when(repositorioMascota.existsById(id)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> servicioMascota.eliminarMascota(id));

        assertEquals("La mascota no existe.", exception.getMessage());
    }

    /**
     * Prueba que verifica la eliminación de una mascota inactiva.
     */
    @Test
    void testEliminarMascotaInactiva() {
        Long id = 1L;
        Mascota mascota = new Mascota();
        mascota.setActiva(false);

        when(repositorioMascota.existsById(id)).thenReturn(true);
        when(repositorioMascota.findById(id)).thenReturn(Optional.of(mascota));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> servicioMascota.eliminarMascota(id));

        assertEquals("La mascota no está activa.", exception.getMessage());
    }

    /**
     * Prueba que verifica la eliminación de una mascota existente y activa.
     */
    @Test
    void testEliminarMascotaExistenteYActiva() {
        Long id = 1L;
        Mascota mascota = new Mascota();
        mascota.setActiva(true);

        when(repositorioMascota.existsById(id)).thenReturn(true);
        when(repositorioMascota.findById(id)).thenReturn(Optional.of(mascota));

        servicioMascota.eliminarMascota(id);

        assertFalse(mascota.isActiva());
        verify(repositorioMascota).save(mascota);
    }

    /**
     * Prueba que verifica la obtención de una mascota existente y activa.
     */
    @Test
    void testObtenerMascotaExistenteYActiva() {
        Long id = 1L;
        Mascota mascota = new Mascota();
        mascota.setEspecie("pajaro");
        mascota.setRaza("colibri");
        mascota.setFechaNacimiento(LocalDate.parse("2022-06-07"));
        mascota.setCodigoIdentificacion("234ASD");
        mascota.setDniResponsable("45675467S");
        mascota.setActiva(true);

        when(repositorioMascota.existsById(id)).thenReturn(true);
        when(repositorioMascota.findById(id)).thenReturn(Optional.of(mascota));

        Optional<Mascota> mascotaResultadoOptional = servicioMascota.obtenerMascota(id);
        Mascota mascotaResultado = null;
        if(mascotaResultadoOptional.isPresent()) {
            mascotaResultado = mascotaResultadoOptional.get();
        }

        assertNotNull(mascotaResultado);
        assertEquals("pajaro", mascotaResultado.getEspecie());
        assertEquals("colibri", mascotaResultado.getRaza());
        assertEquals(LocalDate.parse("2022-06-07"), mascotaResultado.getFechaNacimiento());
        assertEquals("234ASD", mascotaResultado.getCodigoIdentificacion());
        assertEquals("45675467S", mascotaResultado.getDniResponsable());

        verify(repositorioMascota).existsById(id);
        verify(repositorioMascota).findById(id);
    }

    /**
     * Prueba que verifica la creación correcta de una mascota.
     */
    @Test
    void CrearMascotaCorrectamente() {
        MascotaDTO mascotaDTO = new MascotaDTO();
        mascotaDTO.setEspecie("pajaro");
        mascotaDTO.setRaza("colibri");
        mascotaDTO.setFechaNacimiento("2022-06-07");
        mascotaDTO.setCodigoIdentificacion("234ASD");
        mascotaDTO.setDniResponsable("45675467S");

        Mascota mascota = new Mascota();
        mascota.setEspecie("pajaro");
        mascota.setRaza("colibri");
        mascota.setFechaNacimiento(LocalDate.parse("2022-06-07"));
        mascota.setCodigoIdentificacion("234ASD");
        mascota.setDniResponsable("45675467S");

        when(repositorioMascota.findByCodigoIdentificacion("234ASD")).thenReturn(Optional.empty());
        when(repositorioMascota.save(any(Mascota.class))).thenReturn(mascota);

        Mascota mascotaResultado = servicioMascota.agregarMascota(mascotaDTO);

        assertNotNull(mascotaResultado);
        assertEquals("pajaro", mascotaResultado.getEspecie());
        assertEquals("colibri", mascotaResultado.getRaza());
        assertEquals(LocalDate.parse("2022-06-07"), mascotaResultado.getFechaNacimiento());
        assertEquals("234ASD", mascotaResultado.getCodigoIdentificacion());
        assertEquals("45675467S", mascotaResultado.getDniResponsable());

        verify(repositorioMascota, times(1)).findByCodigoIdentificacion("234ASD");
        verify(repositorioMascota, times(1)).save(any(Mascota.class));
    }

    /**
     * Prueba que verifica el error al crear una mascota con el mismo codigo de identificacion.
     */
    @Test
    void CrearMascotaConElMismoCodigoDeIdentificacion() {
        MascotaDTO mascotaDTO = new MascotaDTO();
        mascotaDTO.setEspecie("pajaro");
        mascotaDTO.setRaza("colibri");
        mascotaDTO.setFechaNacimiento("2022-06-07");
        mascotaDTO.setCodigoIdentificacion("234ASD");
        mascotaDTO.setDniResponsable("45675467S");

        Mascota mascota = new Mascota();
        mascota.setCodigoIdentificacion("234ASD");

        when(repositorioMascota.findByCodigoIdentificacion("234ASD")).thenReturn(Optional.of(mascota));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> servicioMascota.agregarMascota(mascotaDTO));

        assertEquals("El codigo de identificacion ya existe.", exception.getMessage());

        verify(repositorioMascota, times(1)).findByCodigoIdentificacion("234ASD");
    }

    /**
     * Prueba que verifica el error al crear una mascota con un formato de fecha incorrecto.
     */
    @Test
    public void CrearMascotaConFormatoDeFechaIncorrecto() {
        MascotaDTO mascotaDTO = new MascotaDTO();
        mascotaDTO.setEspecie("pajaro");
        mascotaDTO.setRaza("colibri");
        mascotaDTO.setFechaNacimiento("2026-07");
        mascotaDTO.setCodigoIdentificacion("234ASD");
        mascotaDTO.setDniResponsable("45675467S");

        when(repositorioMascota.findByCodigoIdentificacion("234ASD")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> servicioMascota.agregarMascota(mascotaDTO));

        assertEquals("Formato de fecha inválido. Debe ser yyyy-MM-dd", exception.getMessage());

        verify(repositorioMascota, times(1)).findByCodigoIdentificacion("234ASD");
    }

    /**
     * Prueba que verifica el error al listar ingresos de una mascota no existente.
     */
    @Test
    void ListarIngresoMascotasNoExistente() {
        long id = 1L;

        when(repositorioMascota.existsById(id)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> servicioMascota.listarIngresoMascotas(id));

        assertEquals("La mascota no existe.", exception.getMessage());

        verify(repositorioMascota, times(1)).existsById(id);
    }
}

