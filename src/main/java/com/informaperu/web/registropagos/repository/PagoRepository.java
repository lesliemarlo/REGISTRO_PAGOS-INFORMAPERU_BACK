package com.informaperu.web.registropagos.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.informaperu.web.registropagos.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}

