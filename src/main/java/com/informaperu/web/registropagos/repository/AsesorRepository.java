package com.informaperu.web.registropagos.repository;
import com.informaperu.web.registropagos.model.Estado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.informaperu.web.registropagos.model.Asesor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AsesorRepository extends JpaRepository<Asesor, Long> {
    List<Asesor> findByEncargadoId(Long encargadoId);
    List<Asesor> findByEncargadoIdAndEstado(Long encargadoId, Estado estado);

    List<Asesor> findByEstado(Estado estado);  // Para obtener todos los asesores eliminados

}