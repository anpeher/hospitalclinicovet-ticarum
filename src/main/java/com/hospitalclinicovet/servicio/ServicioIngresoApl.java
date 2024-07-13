package com.hospitalclinicovet.servicio;

import com.hospitalclinicovet.modelo.Estado;
import com.hospitalclinicovet.modelo.Ingreso;
import com.hospitalclinicovet.repositorio.RepositorioIngreso;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioIngresoApl implements ServicioIngreso{

    private final RepositorioIngreso repositorioIngreso;

    @Override
    public List<Ingreso> listaIngresos() {
        return repositorioIngreso.findAll();
    }

    @Override
    public Ingreso nuevoIngreso(Ingreso ingreso) {
        return repositorioIngreso.save(ingreso);
    }

    @Override
    public Optional<Ingreso> ModificarIngreso(Long id, Ingreso ingreso) {
        return repositorioIngreso.findById(id).map(ingresoMemoria -> {
            ingresoMemoria.setEstado(ingreso.getEstado());
            ingresoMemoria.setFechaFinalizacion(ingreso.getFechaFinalizacion());
            return repositorioIngreso.save(ingresoMemoria);
        });
    }

    @Override
    public boolean eliminarIngreso(Long id) {
        Ingreso ingreso = repositorioIngreso.findById(id).orElseThrow(() ->  new RuntimeException("Ingreso no existe"));
        ingreso.setEstado(Estado.ANULADO);
        repositorioIngreso.save(ingreso);
        return true;
    }
}
