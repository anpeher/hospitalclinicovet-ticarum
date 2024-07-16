package com.hospitalclinicovet.Controlador;


import com.hospitalclinicovet.Excepciones.ResourceNotFoundException;
import com.hospitalclinicovet.dto.Mascota.MascotaDTO;
import com.hospitalclinicovet.dto.Respuesta.Message;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.Modelo.Mascota.Mascota;
import com.hospitalclinicovet.Servicio.Mascota.ServicioMascota;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Controlador de mascotas", description = "Endpoints para guardar la información sobre una mascota registrada en el hospital clínico")
public class ControladorMascota {

    private final ServicioMascota servicioMascota;


    @Operation(summary = "Registro de una nueva mascota",
            description = "Se registra una mascota en el hospital guardando sus datos personales. Su código de identificación debe ser único y por defecto la mascota está activa.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Mascota registrada correctamente",
                            content = @Content(schema = @Schema(implementation = Mascota.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud mal escrita",
                            content = @Content(schema = @Schema(implementation = Message.class)))
            })
    @PostMapping
    public ResponseEntity guardarMascota(@Valid @RequestBody MascotaDTO mascotaDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) { //se validan los datos del json y en caso de fallo salta con un bad request
            return new ResponseEntity<>(new Message(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        try {
            Mascota mascota = servicioMascota.agregarMascota(mascotaDTO);
            return new ResponseEntity<>(mascota, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Obtener los datos de una mascota",
            description = "A traves del id de la mascota obtenemos su información. Si no está activa como pone el enunciado no se devolverá la información, pero se tratará como un error de BADREQUEST por si en un futuro se decide que si se puede acceder a esa información. Esto lo hacemos con todos los endpoints",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Mascota obtenida correctamente",
                            content = @Content(schema = @Schema(implementation = Mascota.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud mal escrita o la mascota no está activa",
                            content = @Content(schema = @Schema(implementation = Message.class))),
                    @ApiResponse(responseCode = "404", description = "No existe la mascota",
                            content = @Content(schema = @Schema(implementation = Message.class)))
            })
    @GetMapping("/{idMascota}")
    public ResponseEntity obtenerMascota(@PathVariable("idMascota") Long id){
        try {
            return new ResponseEntity<>(servicioMascota.obtenerMascota(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener todos los ingresos de una mascota",
            description = "A traves del id de la mascota obtenemos todas los registro de ingreso de una mascota.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ingresos de la mascota obtenidos correctamente",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Ingreso.class)))),
                    @ApiResponse(responseCode = "204", description = "Ingresos de la mascota obtenidos correctamente, pero no tiene ninguno",
                            content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Solicitud mal escrita o la mascota no está activa",
                            content = @Content(schema = @Schema(implementation = Message.class))),
                    @ApiResponse(responseCode = "404", description = "No existe la mascota",
                            content = @Content(schema = @Schema(implementation = Message.class)))
            })
    @GetMapping("/{idIngreso}/ingreso")
    public ResponseEntity ingresosMascota(@PathVariable("idIngreso") Long id){
        try {
            List<Ingreso> Ingresos = servicioMascota.listarIngresoMascotas(id);
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

    @Operation(summary = "Se hacen inaccesibles los datos de una mascota",
            description = "A traves del id de la mascota cambiamos la variable activa a false haciendo que no se puedan realizar operacion con ellas pero manteniéndola en la memoria.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "variable activa cambiada a false",
                            content = @Content()),
                    @ApiResponse(responseCode = "400", description = "La mascota no está activa",
                            content = @Content(schema = @Schema(implementation = Message.class))),
                    @ApiResponse(responseCode = "404", description = "No existe la mascota",
                            content = @Content(schema = @Schema(implementation = Message.class)))
            })
    @DeleteMapping("/{idMascota}")
    public ResponseEntity eliminarMascota(@PathVariable("idMascota") Long id){
        try {
            servicioMascota.eliminarMascota(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }
}
