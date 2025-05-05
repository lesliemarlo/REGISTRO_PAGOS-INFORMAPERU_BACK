package com.informaperu.web.registropagos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informaperu.web.registropagos.model.Pago;
import com.informaperu.web.registropagos.repository.PagoRepository;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public Pago registrarPago(Pago pago) {
        return pagoRepository.save(pago);
    }
}
