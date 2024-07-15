package com.hospitalclinicovet.Controlador;


import com.hospitalclinicovet.Excepciones.ResourceNotFoundException;
import com.hospitalclinicovet.dto.Mascota.MascotaDTO;
import com.hospitalclinicovet.dto.Respuesta.Message;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.Modelo.Mascota.Mascota;
import com.hospitalclinicovet.Servicio.Mascota.MascotaServicio;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/mascota")
@AllArgsConstructor
public class ControladorMascota {

    private final MascotaServicio mascotaServicio;

    @PostMapping
    public ResponseEntity guardarMascota(@Valid @RequestBody MascotaDTO mascotaDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Message(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        try {
            Mascota mascota = mascotaServicio.agregarMascota(mascotaDTO);
            return new ResponseEntity<>(mascota, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{idMascota}")
    public ResponseEntity obtenerMascota(@PathVariable("idMascota") Long id){
        try {
            return new ResponseEntity<>(mascotaServicio.obtenerMascota(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{idIngreso}/ingreso")
    public ResponseEntity ingresosMascota(@PathVariable("idIngreso") Long id){
        try {
            List<Ingreso> Ingresos = mascotaServicio.listarIngresoMascotas(id);
            if (Ingresos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(Ingresos,HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idMascota}")
    public ResponseEntity eliminarMascota(@PathVariable("idMascota") Long id){
        try {
            mascotaServicio.eliminarMascota(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }
}
