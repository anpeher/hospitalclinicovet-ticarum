package com.hospitalclinicovet.Repositorio;

import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio donde se gestionan las operaciones CRUD de la entidad {@link Ingreso}
 */
@Repository
public interface RepositorioIngreso extends JpaRepository<Ingreso, Long> {
    /**
     * Sirve para buscar todos los ingresos de una mascota utilizando su id
     *
     * @param idMascota id de la mascota
     * @return lista de ingresos de la mascota
     */
    List<Ingreso> findByMascotaId(Long idMascota);
}
