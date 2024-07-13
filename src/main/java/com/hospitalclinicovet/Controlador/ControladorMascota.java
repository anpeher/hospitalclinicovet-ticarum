package com.hospitalclinicovet.Controlador;


import com.hospitalclinicovet.dto.MascotaDTO;
import com.hospitalclinicovet.modelo.Mascota;
import com.hospitalclinicovet.servicio.MascotaServicio;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/mascota")
@AllArgsConstructor
public class ControladorMascota {

    private final MascotaServicio mascotaServicio;

    @PostMapping
    public ResponseEntity guardarMascota(@Valid @RequestBody MascotaDTO mascotaDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            Mascota mascota = mascotaServicio.agregarMascota(mascotaDTO);
            return new ResponseEntity<>(mascota, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{idMascota}")
    public ResponseEntity obtenerMascota(@PathVariable("idMascota") Long id){
        try {
            return new ResponseEntity<>(mascotaServicio.ObtenerMascota(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idMascota}")
    public ResponseEntity eliminarMascota(@PathVariable("idMascota") Long id){
        try {
            if (mascotaServicio.eliminarMascota(id)) {
                return ResponseEntity.ok().build();
            } else {
                return new ResponseEntity<>("La mascota no esta activa", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
