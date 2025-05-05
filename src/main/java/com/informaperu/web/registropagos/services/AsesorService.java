package com.informaperu.web.registropagos.services;

import org.springframework.stereotype.Service;

import com.informaperu.web.registropagos.model.Asesor;
import com.informaperu.web.registropagos.repository.AsesorRepository;
import com.informaperu.web.registropagos.security.AsesorDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsesorService {
    private final AsesorRepository asesorRepository;

    public AsesorService(AsesorRepository asesorRepository) {
        this.asesorRepository = asesorRepository;
    }

    public List<AsesorDTO> getAsesoresByEncargadoId(Long encargadoId) {
        List<Asesor> asesores = asesorRepository.findByEncargadoId(encargadoId);
        return asesores.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private AsesorDTO convertToDTO(Asesor asesor) {
        AsesorDTO dto = new AsesorDTO();
        dto.setId(asesor.getId());
        dto.setDni(asesor.getDni());
        dto.setNombre(asesor.getNombre());
        dto.setRango(asesor.getRango());
        return dto;
    }
}
