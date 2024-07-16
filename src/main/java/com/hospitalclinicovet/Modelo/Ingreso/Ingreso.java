package com.hospitalclinicovet.Modelo.Ingreso;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hospitalclinicovet.Modelo.Mascota.Mascota;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidad que representa un ingreso de una mascota en el sistema.
 */
@Entity
@Table(name = "ingreso")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ingreso {
    /**
     * Identificador único de la mascota.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * fecha a la que entra al hospital.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta = LocalDate.now();

    /**
     * fecha en la que sale del hospital.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;

    /**
     * Estado del ingreso.
     */
    @Enumerated(EnumType.STRING)
    private Estado estado;

    /**
     * mascota asociada al ingreso.
     */
    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    /**
     * DNI de la persona responsable de la mascota
     */
    @Column(name = "dni_registrador", nullable = false)
    private String dniResponsable;

}

