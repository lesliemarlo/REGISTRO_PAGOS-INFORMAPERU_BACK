package com.informaperu.web.registropagos.controller;


import org.springframework.web.bind.annotation.*;

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
}
