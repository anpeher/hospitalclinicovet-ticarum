package com.hospitalclinicovet.Repositorio;


import com.hospitalclinicovet.Modelo.Mascota.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio donde se gestionan las operaciones CRUD de la entidad {@link Mascota}
 */
@Repository
public interface RepositorioMascota extends JpaRepository<Mascota, Long> {
    /**
     * Encuentra una mascota por su codigo de identificacion.
     * @param codigoIdentificacion el codigo de identificacion de la mascota, es único.
     * @return Un optional con una mascota si es que encuentra alguna o vacío si no encuentra ninguna.
     */
    Optional<Mascota> findByCodigoIdentificacion(String codigoIdentificacion);
}
