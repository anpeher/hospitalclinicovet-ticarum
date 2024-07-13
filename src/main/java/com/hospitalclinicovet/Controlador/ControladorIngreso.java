package com.hospitalclinicovet.Controlador;

import com.hospitalclinicovet.dto.NuevoIngresoDTO;
import com.hospitalclinicovet.modelo.Estado;
import com.hospitalclinicovet.modelo.Ingreso;
import com.hospitalclinicovet.modelo.Mascota;
import com.hospitalclinicovet.servicio.MascotaServicio;
import com.hospitalclinicovet.servicio.ServicioIngreso;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ingreso")
@AllArgsConstructor
public class ControladorIngreso {

    private final ServicioIngreso servicioIngreso;
    private MascotaServicio mascotaServicio;

    @PostMapping
    public ResponseEntity generarIngreso(@RequestBody NuevoIngresoDTO ingresoDTO){
        Ingreso ingreso = new Ingreso();
        ingreso.setFechaAlta(ingresoDTO.getFechaAlta());
        ingreso.setEstado(Estado.ALTA);
        Mascota mascota = mascotaServicio.obtenerMascota(ingresoDTO.getIdMascota())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        ingreso.setMascota(mascota);
        ingreso.setDniResponsable(mascota.getDniResponsable());
        return new ResponseEntity<>(servicioIngreso.nuevoIngreso(ingreso), HttpStatus.CREATED);
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
