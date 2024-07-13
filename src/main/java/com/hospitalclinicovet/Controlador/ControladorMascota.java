package com.hospitalclinicovet.Controlador;


import com.hospitalclinicovet.dto.MascotaDTO;
import com.hospitalclinicovet.modelo.Mascota;
import com.hospitalclinicovet.servicio.MascotaServicio;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@RestController
@RequestMapping("/mascota")
@AllArgsConstructor
public class ControladorMascota {

    private final MascotaServicio mascotaServicio;

    @PostMapping
    public ResponseEntity guardarMascota(@Valid @RequestBody MascotaDTO mascotaDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(Objects.requireNonNull(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        Mascota mascota = new Mascota();
        mascota.setEspecie(mascotaDTO.getEspecie());
        mascota.setRaza(mascotaDTO.getRaza());
        mascota.setCodigoIdentificacion(mascotaDTO.getCodigoIdentificacion());
        mascota.setDniResponsable(mascotaDTO.getDniResponsable());
        mascota.setActiva(mascotaDTO.isActiva());

        try {
            LocalDate fechaNacimiento = LocalDate.parse(mascotaDTO.getFechaNacimiento(), DateTimeFormatter.ISO_LOCAL_DATE);
            mascota.setFechaNacimiento(fechaNacimiento);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>("Formato de fecha inválido, debe ser yyyy-MM-dd", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mascotaServicio.agregarMascota(mascota), HttpStatus.OK);
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
