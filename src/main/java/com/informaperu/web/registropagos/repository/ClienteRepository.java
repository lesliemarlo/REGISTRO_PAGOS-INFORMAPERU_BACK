package com.informaperu.web.registropagos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.informaperu.web.registropagos.model.Cliente;
import com.informaperu.web.registropagos.model.Encargado;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	 List<Cliente> findByClienteDni(String clientesDni);
}
