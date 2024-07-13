package com.hospitalclinicovet.repositorio;

import com.hospitalclinicovet.modelo.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioMascota extends JpaRepository<Mascota, Long> {
}
