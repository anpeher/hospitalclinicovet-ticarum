package com.hospitalclinicovet.Controlador;

import com.hospitalclinicovet.dto.NuevoIngresoDTO;
import com.hospitalclinicovet.modelo.Ingreso;
import com.hospitalclinicovet.servicio.ServicioIngreso;
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
    public ResponseEntity ModiificarIngreso(@PathVariable("idIngreso") Long id, @RequestBody Ingreso ingreso) {
        Optional<Ingreso> ingresoActualizado = servicioIngreso.ModificarIngreso(id, ingreso);
        return ingresoActualizado.map(ingreso1 -> new ResponseEntity<>(ingreso1, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity ListarIngresos(){
        return new ResponseEntity<>(servicioIngreso.listaIngresos(), HttpStatus.OK);
    }

    @DeleteMapping("/{idIngreso}")
    public ResponseEntity<Void> eliminarIngreso(@PathVariable("idIngreso") Long id){
        if (servicioIngreso.eliminarIngreso(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
