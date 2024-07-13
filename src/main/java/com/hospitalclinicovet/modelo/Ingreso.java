package com.hospitalclinicovet.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ingreso")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ingreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta = LocalDate.now();

    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ALTA;

    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @Column(name = "dni_registrador", nullable = false)
    private String dniResponsable;

}

