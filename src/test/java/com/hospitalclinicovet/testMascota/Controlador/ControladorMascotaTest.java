package com.hospitalclinicovet.testMascota.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalclinicovet.Controlador.ControladorMascota;
import com.hospitalclinicovet.Excepciones.ResourceNotFoundException;
import com.hospitalclinicovet.dto.Mascota.MascotaDTO;
import com.hospitalclinicovet.Modelo.Mascota.Mascota;
import com.hospitalclinicovet.Servicio.Mascota.MascotaServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControladorMascota.class)
public class ControladorMascotaTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MascotaServicio mascotaServicio;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ControladorMascota(mascotaServicio)).build();
    }

    @Test
    void testCrearMascotaCorrectamente() throws Exception {
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

        given(mascotaServicio.agregarMascota(any(MascotaDTO.class))).willReturn(mascota);

        mockMvc.perform(post("/mascota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mascotaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.especie").value("pajaro"))
                .andExpect(jsonPath("$.raza").value("colibri"))
                .andExpect(jsonPath("$.fechaNacimiento").value("2022-06-07"))
                .andExpect(jsonPath("$.codigoIdentificacion").value("234ASD"))
                .andExpect(jsonPath("$.dniResponsable").value("45675467S"));
    }

    @Test
    void testCrearMascotaMalCodigo() throws Exception {
        MascotaDTO mascotaDTO = new MascotaDTO();
        mascotaDTO.setEspecie("pajaro");
        mascotaDTO.setRaza("colibri");
        mascotaDTO.setFechaNacimiento("2022-06-07");
        mascotaDTO.setCodigoIdentificacion("234ASrtrD");
        mascotaDTO.setDniResponsable("45675467S");

        given(mascotaServicio.agregarMascota(any(MascotaDTO.class)))
                .willThrow(new IllegalArgumentException());

        mockMvc.perform(post("/mascota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mascotaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("formato codigo de identificacion invalido, debe ser tres numeros y tres letras en mayúscula"));
    }

    @Test
    void testBuscarMascotaCorrectamente() throws Exception {
        Long id = 1L;
        Mascota mascota = new Mascota();
        mascota.setId(id);
        mascota.setEspecie("pajaro");
        mascota.setRaza("colibri");
        mascota.setFechaNacimiento(LocalDate.parse("2022-06-07"));
        mascota.setCodigoIdentificacion("234ASD");
        mascota.setDniResponsable("45675467S");

        given(mascotaServicio.ObtenerMascota(id)).willReturn(Optional.of(mascota));

        mockMvc.perform(get("/mascota/{idMascota}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.especie").value("pajaro"))
                .andExpect(jsonPath("$.raza").value("colibri"))
                .andExpect(jsonPath("$.fechaNacimiento").value("2022-06-07"))
                .andExpect(jsonPath("$.codigoIdentificacion").value("234ASD"))
                .andExpect(jsonPath("$.dniResponsable").value("45675467S"));
    }

    @Test
    void testBuscarMascotaInexistente() throws Exception {
        Long id = 50L;

        given(mascotaServicio.ObtenerMascota(id)).willThrow(new ResourceNotFoundException("La mascota no existe."));

        mockMvc.perform(get("/mascota/{idMascota}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("La mascota no existe."));
    }

    @Test
    void testMascotaConIngresoInexistente() throws Exception {
        Long id = 1L;

        given(mascotaServicio.ListarIngresoMascotas(id)).willReturn(Collections.emptyList());

        mockMvc.perform(get("/mascota/{idIngreso}/ingreso", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarCorrectamenteMascota() throws Exception {
        Long id = 1L;

        doNothing().when(mascotaServicio).eliminarMascota(id);

        mockMvc.perform(delete("/mascota/{idMascota}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
