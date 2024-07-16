package com.hospitalclinicovet.testIngreso.Ingreso;

import com.hospitalclinicovet.Modelo.Ingreso.Estado;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.Modelo.Mascota.Mascota;
import com.hospitalclinicovet.Repositorio.RepositorioIngreso;
import com.hospitalclinicovet.Servicio.Ingreso.ServicioIngresoApl;

import com.hospitalclinicovet.Servicio.Mascota.ServicioMascota;
import com.hospitalclinicovet.dto.Ingreso.ModIngresoDTO;
import com.hospitalclinicovet.dto.Ingreso.NuevoIngresoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Clase de prueba para el servicio de ingresos.
 */
public class ServicioIngresoTest {

    @Mock
    private RepositorioIngreso repositorioIngreso;

    @Mock
    private ServicioMascota servicioMascota;

    @InjectMocks
    private ServicioIngresoApl ingresoServicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que verifica que el método listaIngresos retorna una lista de ingresos correctamente.
     */
    @Test
    void testListaIngresosRetornaListaIngresos() {
        List<Ingreso> ingresos = Arrays.asList(new Ingreso(), new Ingreso());
        given(repositorioIngreso.findAll()).willReturn(ingresos);

        List<Ingreso> resultado = ingresoServicio.listaIngresos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repositorioIngreso).findAll();
    }

    /**
     * Prueba que verifica que se crea un nuevo ingreso correctamente.
     */
    @Test
    void testNuevoIngresoCorrecto() {
        NuevoIngresoDTO ingresoDTO = new NuevoIngresoDTO();
        ingresoDTO.setIdMascota(1L);
        ingresoDTO.setFechaAlta("2024-07-01");
        ingresoDTO.setDni("12345478A");

        Mascota mascota = new Mascota();
        mascota.setId(1L);
        mascota.setActiva(true);
        mascota.setDniResponsable("12345478A");

        Ingreso ingreso = new Ingreso();

        given(servicioMascota.obtenerMascota(1L)).willReturn(Optional.of(mascota));
        given(repositorioIngreso.save(any(Ingreso.class))).willReturn(ingreso);

        Ingreso resultado = ingresoServicio.nuevoIngreso(ingresoDTO);

        assertNotNull(resultado);
        verify(servicioMascota, times(1)).obtenerMascota(1L);
        verify(repositorioIngreso, times(1)).save(any(Ingreso.class));
    }

    /**
     * Prueba que verifica que se lanza una excepción si el DNI del responsable es distinto.
     */
    @Test
    void testNuevoIngresoDniDistinto() {
        NuevoIngresoDTO ingresoDTO = new NuevoIngresoDTO();
        ingresoDTO.setIdMascota(1L);
        ingresoDTO.setDni("12345478A");

        Mascota mascota = new Mascota();
        mascota.setId(1L);
        mascota.setActiva(true);
        mascota.setDniResponsable("32345478D");

        given(servicioMascota.obtenerMascota(1L)).willReturn(Optional.of(mascota));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ingresoServicio.nuevoIngreso(ingresoDTO));

        assertEquals("Solo el responsable de la mascota puede generar el ingreso.", exception.getMessage());
        verify(servicioMascota, times(1)).obtenerMascota(1L);
        verify(repositorioIngreso, times(0)).save(any(Ingreso.class));
    }

    /**
     * Prueba que verifica que se lanza una excepción si la mascota no está activa.
     */
    @Test
    void testNuevoIngresoMascotaNoActiva() {
        NuevoIngresoDTO ingresoDTO = new NuevoIngresoDTO();
        ingresoDTO.setIdMascota(1L);

        Mascota mascota = new Mascota();
        mascota.setId(1L);
        mascota.setActiva(false);

        given(servicioMascota.obtenerMascota(1L)).willReturn(Optional.of(mascota));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ingresoServicio.nuevoIngreso(ingresoDTO));

        assertEquals("La mascota no está activa.", exception.getMessage());
        verify(servicioMascota, times(1)).obtenerMascota(1L);
        verify(repositorioIngreso, times(0)).save(any(Ingreso.class));
    }

    /**
     * Prueba que verifica que se modifica un ingreso correctamente.
     */
    @Test
    void testModificarIngresoCorrectamente() {
        Long id = 1L;
        ModIngresoDTO modIngresoDTO = new ModIngresoDTO();
        modIngresoDTO.setEstado("FINALIZADO");
        modIngresoDTO.setFechaFinalizacion("2023-01-01");

        Ingreso ingresoExistente = new Ingreso();
        ingresoExistente.setId(id);
        ingresoExistente.setEstado(Estado.HOSPITALIZACION);

        given(repositorioIngreso.findById(id)).willReturn(Optional.of(ingresoExistente));
        given(repositorioIngreso.save(any(Ingreso.class))).willReturn(ingresoExistente);

        Optional<Ingreso> resultado = ingresoServicio.modificarIngreso(id, modIngresoDTO);

        assertTrue(resultado.isPresent());
        assertEquals(Estado.FINALIZADO, resultado.get().getEstado());
        verify(repositorioIngreso, times(1)).findById(id);
        verify(repositorioIngreso, times(1)).save(any(Ingreso.class));
    }
}
