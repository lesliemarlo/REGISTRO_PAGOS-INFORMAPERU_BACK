package com.informaperu.web.registropagos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informaperu.web.registropagos.model.Encargado;
import com.informaperu.web.registropagos.model.Estado;
import com.informaperu.web.registropagos.model.Pago;
import com.informaperu.web.registropagos.services.EncargadoService;

@RestController
@RequestMapping("/api/encargados")
public class EncargadoController {
    private final EncargadoService encargadoService;

    public EncargadoController(EncargadoService encargadoService) {
        this.encargadoService = encargadoService;
    }

    @GetMapping
    public List<Encargado> getAll() {
        return encargadoService.getAll();
    }
    @GetMapping("/{id}")
    public Encargado getById(@PathVariable("id") Long id) {
        return encargadoService.getById(id);
    }
    
    @PostMapping("/create")
    public Encargado create(@RequestBody Encargado encargado) {
        return encargadoService.create(encargado);
    }

    @PutMapping("/update/{id}")
    public Encargado update(@PathVariable("id") Long id, @RequestBody Encargado encargado) {
        return encargadoService.update(id, encargado);
    }

    @DeleteMapping("/deshabilitar/{id}")
    public void delete(@PathVariable("id") Long id) {
        Encargado encargado = encargadoService.getById(id);
        encargado.setEstado(Estado.DESHABILITADO);
        encargadoService.update(id, encargado);
    }

    //restore
    @PutMapping("/restore/{id}")
    public Encargado restore(@PathVariable("id") Long id) {
        return encargadoService.restore(id);
    }

   
    
    
}

