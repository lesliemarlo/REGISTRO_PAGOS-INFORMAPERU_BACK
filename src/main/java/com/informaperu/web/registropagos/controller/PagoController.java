package com.informaperu.web.registropagos.controller;

import com.informaperu.web.registropagos.model.Pago;
import com.informaperu.web.registropagos.model.Encargado;
import com.informaperu.web.registropagos.model.Estado;
import com.informaperu.web.registropagos.services.PagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/registrar")
    public Pago registrarPago(@RequestBody Pago pago) {
        return pagoService.registrarPago(pago);
    }

    @GetMapping
    public List<Pago> obtenerTodos() {
        // Solo devolvemos los que no han sido "eliminados" lógicamente
        return pagoService.obtenerTodos().stream()
                .filter(p -> p.getEstado() == Estado.HABILITADO)
                .toList();
    }

    @GetMapping("/buscarPor/{id}")
    public Pago obtenerPorId(@PathVariable Long id) {
        Pago pago = pagoService.obtenerPorId(id);
        if (pago.getEstado() == Estado.DESHABILITADO) {
            throw new RuntimeException("El pago con ID " + id + " ha sido eliminado.");
        }
        return pago;
    }

    @PutMapping("/actualizar/{id}")
    public Pago actualizarPago(@PathVariable Long id, @RequestBody Pago pago) {
        return pagoService.actualizarPago(id, pago);
    }

    @DeleteMapping("/deshabilitar/{id}")
    public void eliminarPago(@PathVariable Long id) {
        Pago pago = pagoService.obtenerPorId(id);
        pago.setEstado(Estado.DESHABILITADO);
        pagoService.actualizarPago(id, pago);
    }
    
  //restore
    @PutMapping("/restore/{id}")
    public Pago restore(@PathVariable("id") Long id) {
        return pagoService.restore(id);
    }
}
