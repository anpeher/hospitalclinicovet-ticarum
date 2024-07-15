package com.hospitalclinicovet.Repositorio;

import com.hospitalclinicovet.Modelo.Ingreso.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioIngreso extends JpaRepository<Ingreso, Long> {
    List<Ingreso> findByMascotaId(Long idMascota);
}
