package com.hospitalclinicovet.Repositorio;

import com.hospitalclinicovet.Modelo.Mascota.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioMascota extends JpaRepository<Mascota, Long> {
    Optional<Mascota> findByCodigoIdentificacion(String codigoIdentificacion);
}
