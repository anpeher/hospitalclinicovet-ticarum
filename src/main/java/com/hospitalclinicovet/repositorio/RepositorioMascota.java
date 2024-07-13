package com.hospitalclinicovet.repositorio;

import com.hospitalclinicovet.modelo.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioMascota extends JpaRepository<Mascota, Long> {
    Optional<Mascota> findByCodigoIdentificacion(String codigoIdentificacion);
}
