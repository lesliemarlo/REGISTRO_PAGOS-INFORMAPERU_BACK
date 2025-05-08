package com.informaperu.web.registropagos.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.informaperu.web.registropagos.model.Encargado;
import com.informaperu.web.registropagos.model.Estado;



public interface EncargadoRepository extends JpaRepository<Encargado, Long> {
    Optional<Encargado> findByUsername(String username);
    Optional<Encargado> findByUsernameAndPasswordHash(String username, String passwordHash);
    List<Encargado> findByEstado(Estado estado);

}
