package com.hospitalclinicovet.Modelo.Mascota;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.Period;

/**
 * Entidad mascota con todos sus atributos.
 */
@Entity
@Table(name = "mascota")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Mascota {

    /**
     * Identificador único de la mascota.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Especie de la mascota.
     */
    @Column(nullable = false)
    private String especie;

    /**
     * Raza de la mascota.
     */
    @Column(nullable = false)
    private String raza;

    /**
     * Edad de la mascota, calculada usando la fecha de nacimiento.
     */
    @Transient
    private int edad;

    /**
     * Fecha de nacimiento de la mascota, se ha añadido al proyecto para obtener la propiedad calculada edad.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    /**
     * Código de identificación único de la mascota.
     */
    @Column(name = "codigo_identificacion", nullable = false, unique = true)
    private String codigoIdentificacion;

    /**
     * DNI del responsable de la mascota.
     */
    @Column(name = "dni_responsable", nullable = false)
    private String dniResponsable;

    /**
     * Indica si la mascota está activa, por defecto si lo está. En caso de no estarlo no se pueden hacer operaciones con ellas.
     */
    @Column(nullable = false)
    private boolean activa = true;

    /**
     * Calcula la edad de la mascota a partir de la fecha de nacimiento.
     *
     * @return la edad de la mascota en años.
     */
    public int getEdad() {
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
    }
}