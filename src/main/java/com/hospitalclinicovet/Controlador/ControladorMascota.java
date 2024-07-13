package com.hospitalclinicovet.Controlador;


import com.hospitalclinicovet.modelo.Mascota;
import com.hospitalclinicovet.servicio.MascotaServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mascota")
@AllArgsConstructor
public class ControladorMascota {

    private final MascotaServicio mascotaServicio;

    @PostMapping
    public ResponseEntity guardarMascota(@RequestBody Mascota mascota){
        return new ResponseEntity(mascotaServicio.agregarMascota(mascota), HttpStatus.CREATED);
    }

    @GetMapping("/{idMascota}")
    public ResponseEntity obtenerMascota(@PathVariable("idMascota") Long id){
        return new ResponseEntity(mascotaServicio.ObtenerMascota(id), HttpStatus.OK);
    }

    @DeleteMapping("/{idMascota}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable("idMascota") Long id){
        if (mascotaServicio.eliminarMascota(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
