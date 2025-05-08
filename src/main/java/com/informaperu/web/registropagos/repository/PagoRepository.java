package com.informaperu.web.registropagos.repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.informaperu.web.registropagos.model.Estado;
import com.informaperu.web.registropagos.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
	List<Pago> findByEstado(Estado estado);

}

