package com.hospitalclinicovet.Modelo.Mascota;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;



import java.time.LocalDate;
import java.time.Period;


@Entity
@Table(name = "mascota")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String especie;

     @Column(nullable = false)
    private String raza;

    @Transient
    private int edad;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_nacimiento",nullable = false)
    private LocalDate fechaNacimiento;


    @Column(name = "codigo_identificacion", nullable = false, unique = true)
    private String codigoIdentificacion;


    @Column(name = "dni_responsable", nullable = false)
    private String dniResponsable;

    @Column(nullable = false)
    private boolean activa = true;

    public int getEdad() {
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
    }
}