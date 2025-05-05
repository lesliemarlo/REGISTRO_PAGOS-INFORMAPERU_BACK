package com.informaperu.web.registropagos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.informaperu.web.registropagos.model.Asesor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AsesorRepository extends JpaRepository<Asesor, Long> {
    List<Asesor> findByEncargadoId(Long encargadoId);
}