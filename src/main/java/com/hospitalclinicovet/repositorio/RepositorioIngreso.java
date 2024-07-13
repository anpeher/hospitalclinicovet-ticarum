package com.hospitalclinicovet.repositorio;

import com.hospitalclinicovet.modelo.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioIngreso extends JpaRepository<Ingreso, Long> {
}
