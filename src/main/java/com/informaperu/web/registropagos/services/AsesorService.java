package com.informaperu.web.registropagos.services;

import org.springframework.stereotype.Service;

import com.informaperu.web.registropagos.model.Asesor;
import com.informaperu.web.registropagos.model.Encargado;
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
    
    //CRUD
    public AsesorDTO createAsesor(Long encargadoId, Asesor asesor) {
        asesor.setEncargado(new Encargado());
        asesor.getEncargado().setId(encargadoId);
        Asesor saved = asesorRepository.save(asesor);
        return convertToDTO(saved);
    }

    public AsesorDTO updateAsesor(Long asesorId, Long encargadoId, Asesor updatedData) {
        Asesor asesor = asesorRepository.findById(asesorId)
            .orElseThrow(() -> new RuntimeException("Asesor no encontrado"));

        if (!asesor.getEncargado().getId().equals(encargadoId)) {
            throw new RuntimeException("No autorizado para editar este asesor");
        }

        asesor.setNombre(updatedData.getNombre());
        asesor.setDni(updatedData.getDni());
        asesor.setRango(updatedData.getRango());
        asesor.setUpdatedAt(java.time.LocalDateTime.now());

        return convertToDTO(asesorRepository.save(asesor));
    }

    public void deleteAsesor(Long asesorId, Long encargadoId) {
        Asesor asesor = asesorRepository.findById(asesorId)
            .orElseThrow(() -> new RuntimeException("Asesor no encontrado"));

        if (!asesor.getEncargado().getId().equals(encargadoId)) {
            throw new RuntimeException("No autorizado para eliminar este asesor");
        }

        asesorRepository.delete(asesor);
    }

}
