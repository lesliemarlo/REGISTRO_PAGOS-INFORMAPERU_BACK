package com.informaperu.web.registropagos.services;

import com.informaperu.web.registropagos.model.Encargado;
import com.informaperu.web.registropagos.model.Pago;

import java.util.List;

public interface PagoService {
    Pago registrarPago(Pago pago);
    List<Pago> obtenerTodos();
    Pago obtenerPorId(Long id);
    Pago actualizarPago(Long id, Pago pagoActualizado);
    void eliminarPago(Long id);
    Pago restore(Long id);
}
