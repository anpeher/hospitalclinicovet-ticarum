package com.hospitalclinicovet.Controlador;

import com.hospitalclinicovet.Excepciones.ResourceNotFoundException;
import com.hospitalclinicovet.dto.Ingreso.ModIngresoDTO;
import com.hospitalclinicovet.dto.Ingreso.NuevoIngresoDTO;
import com.hospitalclinicovet.dto.Respuesta.Message;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.Servicio.Ingreso.ServicioIngreso;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/ingreso")
@AllArgsConstructor
public class ControladorIngreso {

    private final ServicioIngreso servicioIngreso;


    @PostMapping
    public ResponseEntity generarIngreso(@Valid @RequestBody NuevoIngresoDTO ingresoDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Message(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        try{
            return new ResponseEntity<>(servicioIngreso.nuevoIngreso(ingresoDTO), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{idIngreso}")
    public ResponseEntity modificarIngreso(@PathVariable("idIngreso") Long id,@Valid @RequestBody ModIngresoDTO modIngresoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Message(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        try {
            Optional<Ingreso> ingreso = servicioIngreso.modificarIngreso(id, modIngresoDTO);
            return ResponseEntity.ok(ingreso);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity listarIngresos(){
        return new ResponseEntity<>(servicioIngreso.listaIngresos(), HttpStatus.OK);
    }

    @DeleteMapping("/{idIngreso}")
    public ResponseEntity eliminarIngreso(@PathVariable("idIngreso") Long id){
        try{
            servicioIngreso.eliminarIngreso(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
