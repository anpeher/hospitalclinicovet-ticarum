package com.hospitalclinicovet.Controlador;

import com.hospitalclinicovet.Excepciones.ResourceNotFoundException;
import com.hospitalclinicovet.dto.Ingreso.ModIngresoDTO;
import com.hospitalclinicovet.dto.Ingreso.NuevoIngresoDTO;
import com.hospitalclinicovet.dto.Respuesta.Message;
import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import com.hospitalclinicovet.Servicio.Ingreso.ServicioIngreso;
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

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/ingreso")
@AllArgsConstructor
@Tag(name = "Controlador de Ingresos", description = "Endpoints para la creación y manejo de ingresos de una mascota en un hospital veterinario")
public class ControladorIngreso {

    private final ServicioIngreso servicioIngreso;

    @Operation(summary = "Generacion de un nuevo ingreso",
            description = "Genera un nuevo ingreso para una mascota existente y activa. Esta se crea con el estado de alta sin fecha de finalización y solo puede ser creada por el responsable de la mascota.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ingreso creado exitosamente",
                            content = @Content(schema = @Schema(implementation = Ingreso.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud mal escrita o la mascota no está activa",
                            content = @Content(schema = @Schema(implementation = Message.class)))
            })
    @PostMapping
    public ResponseEntity generarIngreso(@Valid @RequestBody NuevoIngresoDTO ingresoDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) { //se validan los datos del json y en caso de fallo salta con un bad request
            return new ResponseEntity<>(new Message(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        try{
            return new ResponseEntity<>(servicioIngreso.nuevoIngreso(ingresoDTO), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(summary = "Modifica un ingreso",
            description = "Modifica un ingreso existente cambiando su fecha de finalización y/o estado. No puede tener estado FINALIZADO sin tener una fecha de finalización",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ingreso modificado exitosamente",
                            content = @Content(schema = @Schema(implementation = Ingreso.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud mal escrita",
                            content = @Content(schema = @Schema(implementation = Message.class))),
                    @ApiResponse(responseCode = "404", description = "No existe el ingreso",
                            content = @Content(schema = @Schema(implementation = Message.class)))
            })
    @PutMapping("/{idIngreso}")
    //No se es necesario el id de la mascota y por tanto se omite
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

    @Operation(summary = "Muestra una lista de todos los ingresos",
            description = "Muestra una lista de todos los ingresos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista generada exitosamente",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Ingreso.class))))
            })
    @GetMapping
    public ResponseEntity listarIngresos(){
        return new ResponseEntity<>(servicioIngreso.listaIngresos(), HttpStatus.OK);
    }

    @Operation(summary = "Modifica el estado de un ingreso ha ANULADO",
            description = "Elimina un ingreso cambiado su estado ha ANULADO.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "estado de ingreso modificado ha ANULADO exitosamente",
                            content = @Content()),
                    @ApiResponse(responseCode = "404", description = "No existe el ingreso",
                            content = @Content(schema = @Schema(implementation = Message.class)))
            })
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
