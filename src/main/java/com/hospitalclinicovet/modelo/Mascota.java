package com.hospitalclinicovet.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "la especie es obligatoria")
    @Size(max = 100, message = "el campo especie no puede contener más de 100 palabras")
    @Pattern(regexp = "[A-Za-z- ñ]+", message = "El campo especie solo piede contener letras, espacios en blanco y el caracter -")
    @Column(nullable = false)
    private String especie;

    @NotBlank(message = "la raza es obligatoria")
    @Size(max = 100, message = "el campo raza no puede contener más de 100 palabras")
    @Pattern(regexp = "[A-Za-z- ñ]+", message = "El campo raza solo piede contener letras, espacios en blanco y el caracter -")
    @Column(nullable = false)
    private String raza;

    @Transient
    private int edad;

    @NotBlank(message = "la fecha de nacimiento es obligatoria")
    @Past(message = "la fecha de nacimiento no puede ser de una fecha futura")
    @Pattern(regexp = "^20\\d{2}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])", message = "formato fecha invalido")
    @Column(name = "fecha_nacimiento",nullable = false)
    private LocalDate fechaNacimiento;

    @NotBlank(message = "el codigo de identificaciónes obligatorio")
    @Size(max = 100, message = "el campo codigo identificación no puede contener más de 100 palabras")
    @Pattern(regexp = "\\d{3}[A-Z]{3}", message = "formato codigo de identificacion invalido, debe ser tres numeros y tres letras en mayúscula")
    @Column(name = "codigo_identificacion", nullable = false, unique = true)
    private String codigoIdentificacion;

    @NotBlank(message = "el dni del responsable obligatorio")
    @Size(max = 100, message = "el campo dni del responsable no puede contener más de 100 palabras")
    @Pattern(regexp = "\\d{8}[A-Z]", message = "formato dni incorrecto")
    @Column(name = "dni_responsable", nullable = false)
    private String dniResponsable;

    @Column(nullable = false)
    private boolean activa = true;

    public int getEdad() {
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
    }
}