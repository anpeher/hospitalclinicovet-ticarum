package com.hospitalclinicovet.testIngreso.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalclinicovet.Controlador.ControladorIngreso;
import com.hospitalclinicovet.Modelo.Ingreso.Estado;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.Modelo.Mascota.Mascota;
import com.hospitalclinicovet.Servicio.Ingreso.ServicioIngreso;
import com.hospitalclinicovet.dto.Ingreso.ModIngresoDTO;
import com.hospitalclinicovet.dto.Ingreso.NuevoIngresoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControladorIngreso.class)
public class ControladorIngresoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioIngreso servicioIngreso;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ControladorIngreso(servicioIngreso)).build();
    }

    @Test
    void testGenerarIngresoCorrectamente() throws Exception {
        NuevoIngresoDTO ingresoDTO = new NuevoIngresoDTO();
        ingresoDTO.setDni("12345678A");
        ingresoDTO.setFechaAlta("2024-07-12");
        ingresoDTO.setIdMascota(1L);

        Ingreso ingreso = new Ingreso();
        ingreso.setId(1L);
        ingreso.setFechaAlta(LocalDate.parse("2024-07-12"));
        ingreso.setFechaFinalizacion(null);
        ingreso.setEstado(Estado.ALTA);
        Mascota mascota = new Mascota();
        mascota.setId(1L);
        mascota.setEspecie("Perro");
        mascota.setRaza("GoldenR");
        mascota.setFechaNacimiento(LocalDate.parse("2020-04-10"));
        mascota.setCodigoIdentificacion("123ABC");
        mascota.setDniResponsable("12345678A");
        mascota.setActiva(true);
        ingreso.setMascota(mascota);
        ingreso.setDniResponsable("12345678A");

        given(servicioIngreso.nuevoIngreso(any(NuevoIngresoDTO.class))).willReturn(ingreso);

        mockMvc.perform(post("/ingreso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingresoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fechaAlta").value("2024-07-12"))
                .andExpect(jsonPath("$.fechaFinalizacion").isEmpty())
                .andExpect(jsonPath("$.estado").value("ALTA"))
                .andExpect(jsonPath("$.mascota.id").value(1L))
                .andExpect(jsonPath("$.dniResponsable").value("12345678A"));
    }

    @Test
    void testGenerarIngresoMalFormatoDNI() throws Exception {
        NuevoIngresoDTO ingresoDTO = new NuevoIngresoDTO();
        ingresoDTO.setDni("123478A");
        ingresoDTO.setFechaAlta("2024-07-12");
        ingresoDTO.setIdMascota(1L);

        given(servicioIngreso.nuevoIngreso(any(NuevoIngresoDTO.class))).willThrow(new IllegalArgumentException());

        mockMvc.perform(post("/ingreso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingresoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("formato dni incorrecto"));

    }

    @Test
    void testEliminarIngresoCorrectamente() throws Exception {
        Long ingresoId = 1L;
        doNothing().when(servicioIngreso).eliminarIngreso(ingresoId);

        mockMvc.perform(delete("/ingreso/{idIngreso}", ingresoId))
                .andExpect(status().isOk());
    }

    @Test
    void testListarIngresos() throws Exception {
        given(servicioIngreso.listaIngresos()).willReturn(Arrays.asList(new Ingreso(), new Ingreso()));

        mockMvc.perform(get("/ingreso")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testModificarIngresoEstado() throws Exception {
        ModIngresoDTO modIngresoDTO = new ModIngresoDTO();
        modIngresoDTO.setEstado("ALTA");

        Ingreso ingreso = new Ingreso();
        ingreso.setId(1L);
        ingreso.setFechaAlta(LocalDate.parse("2024-07-12"));
        ingreso.setFechaFinalizacion(null);
        ingreso.setEstado(Estado.ALTA);
        Mascota mascota = new Mascota();
        mascota.setId(1L);
        mascota.setEspecie("Perro");
        mascota.setRaza("GoldenR");
        mascota.setFechaNacimiento(LocalDate.parse("2020-04-10"));
        mascota.setCodigoIdentificacion("123ABC");
        mascota.setDniResponsable("12345678A");
        mascota.setActiva(true);
        ingreso.setMascota(mascota);
        ingreso.setDniResponsable("12345678A");

        given(servicioIngreso.modificarIngreso(anyLong(), any(ModIngresoDTO.class))).willReturn(Optional.of(ingreso));

        mockMvc.perform(put("/ingreso/{idIngreso}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modIngresoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ALTA"));
    }

    @Test
    void testModificarIngresoEstadoIncorrecto() throws Exception {
        ModIngresoDTO modIngresoDTO = new ModIngresoDTO();
        modIngresoDTO.setEstado("fallo");

        given(servicioIngreso.modificarIngreso(anyLong(), any(ModIngresoDTO.class))).willThrow(new IllegalArgumentException());

        mockMvc.perform(put("/ingreso/{idIngreso}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modIngresoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Estado inválido"));
    }


}