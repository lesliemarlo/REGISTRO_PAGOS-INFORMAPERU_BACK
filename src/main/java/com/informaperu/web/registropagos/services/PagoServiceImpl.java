package com.informaperu.web.registropagos.services;

import com.informaperu.web.registropagos.model.Encargado;
import com.informaperu.web.registropagos.model.Estado;
import com.informaperu.web.registropagos.model.Pago;
import com.informaperu.web.registropagos.repository.PagoRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public Pago registrarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    @Override
    public Pago obtenerPorId(Long id) {
        return pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
    }

    @Override
    public Pago actualizarPago(Long id, Pago pagoActualizado) {
        Pago pagoExistente = pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));

        // Actualizamos campos (todos menos el ID, claro)
        pagoExistente.setClienteDni(pagoActualizado.getClienteDni());
        pagoExistente.setClienteNombre(pagoActualizado.getClienteNombre());
        pagoExistente.setClienteCartera(pagoActualizado.getClienteCartera());
        pagoExistente.setClienteNumeroProducto(pagoActualizado.getClienteNumeroProducto());

        pagoExistente.setAsesorDni(pagoActualizado.getAsesorDni());
        pagoExistente.setAsesorNombre(pagoActualizado.getAsesorNombre());
        pagoExistente.setAsesorRango(pagoActualizado.getAsesorRango());

        pagoExistente.setEncargadoUsername(pagoActualizado.getEncargadoUsername());
        pagoExistente.setEncargadoNombre(pagoActualizado.getEncargadoNombre());

        pagoExistente.setFechaVoucher(pagoActualizado.getFechaVoucher());
        pagoExistente.setTipoPago(pagoActualizado.getTipoPago());
        pagoExistente.setImporte(pagoActualizado.getImporte());
        pagoExistente.setEmpresa(pagoActualizado.getEmpresa());
        pagoExistente.setVoucherLink(pagoActualizado.getVoucherLink());
        pagoExistente.setEstado(pagoActualizado.getEstado());

        return pagoRepository.save(pagoExistente);
    }

    @Override
    public void eliminarPago(Long id) {
        Pago pago = pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
        pagoRepository.delete(pago);
    }
    
  
    //restore
    public Pago restore(Long id) {
        Pago pago =pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Encargado no encontrado"));

        if (pago.getEstado() != Estado.DESHABILITADO && pago.getEstado() != Estado.DESHABILITADO) {
            throw new RuntimeException("El encargado no está en estado recuperable");
        }

        pago.setEstado(Estado.HABILITADO);
        pago.setFechaAct(LocalDateTime.now());
        return pagoRepository.save(pago);
    }
}
