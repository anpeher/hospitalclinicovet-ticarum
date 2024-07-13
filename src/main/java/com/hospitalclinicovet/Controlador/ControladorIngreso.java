package com.hospitalclinicovet.Controlador;

import com.hospitalclinicovet.dto.Ingreso.ModIngresoDTO;
import com.hospitalclinicovet.dto.Ingreso.NuevoIngresoDTO;
import com.hospitalclinicovet.modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.servicio.Ingreso.ServicioIngreso;
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
            return new ResponseEntity<>(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try{
            return new ResponseEntity<>(servicioIngreso.nuevoIngreso(ingresoDTO), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{idIngreso}")
    public ResponseEntity ModificarIngreso(@PathVariable("idIngreso") Long id, @RequestBody ModIngresoDTO modIngresoDTO) {
        try{
            Optional<Ingreso> ingresoActualizado = servicioIngreso.ModificarIngreso(id, modIngresoDTO);
            return ingresoActualizado.map(ingreso1 -> new ResponseEntity<>(ingreso1, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity ListarIngresos(){
        return new ResponseEntity<>(servicioIngreso.listaIngresos(), HttpStatus.OK);
    }

    @DeleteMapping("/{idIngreso}")
    public ResponseEntity eliminarIngreso(@PathVariable("idIngreso") Long id){
        try{
            servicioIngreso.eliminarIngreso(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
