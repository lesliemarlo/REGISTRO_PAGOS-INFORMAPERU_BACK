package com.informaperu.web.registropagos.controller;


import org.springframework.web.bind.annotation.*;

import com.informaperu.web.registropagos.model.Asesor;
import com.informaperu.web.registropagos.security.AsesorDTO;
import com.informaperu.web.registropagos.services.AsesorService;

import java.util.List;

@RestController
@RequestMapping("/api/asesores")
public class AsesorController {
    private final AsesorService asesorService;

    public AsesorController(AsesorService asesorService) {
        this.asesorService = asesorService;
    }

    @GetMapping("/by-encargado/{encargadoId}")
    public List<AsesorDTO> getAsesoresByEncargado(@PathVariable("encargadoId") Long encargadoId) {
        return asesorService.getAsesoresByEncargadoId(encargadoId);
    }
    
    @PostMapping("/create/{encargadoId}")
    public AsesorDTO createAsesor(@PathVariable("encargadoId") Long encargadoId, @RequestBody Asesor asesor) {
        return asesorService.createAsesor(encargadoId, asesor);
    }

    @PutMapping("/update/{asesorId}/encargado/{encargadoId}")
    public AsesorDTO updateAsesor(
        @PathVariable("asesorId") Long asesorId,
        @PathVariable("encargadoId") Long encargadoId,
        @RequestBody Asesor asesor
    ) {
        return asesorService.updateAsesor(asesorId, encargadoId, asesor);
    }


    @DeleteMapping("/deshabilitar/{asesorId}/encargado/{encargadoId}")
    public void deleteAsesor(@PathVariable("asesorId") Long asesorId, @PathVariable("encargadoId") Long encargadoId) {
        asesorService.deleteAsesor(asesorId, encargadoId);
    }

 // recuperar

    @PutMapping("/restore/{asesorId}/encargado/{encargadoId}")
    public AsesorDTO restoreAsesor(@PathVariable("asesorId") Long asesorId, @PathVariable("encargadoId") Long encargadoId) {
        return asesorService.restoreAsesor(asesorId, encargadoId);
    }


}
