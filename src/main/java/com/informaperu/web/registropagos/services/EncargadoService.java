package com.informaperu.web.registropagos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.informaperu.web.registropagos.model.Encargado;
import com.informaperu.web.registropagos.repository.EncargadoRepository;

@Service
public class EncargadoService {
    private final EncargadoRepository encargadoRepository;

    public EncargadoService(EncargadoRepository encargadoRepository) {
        this.encargadoRepository = encargadoRepository;
    }

    public List<Encargado> getAll() {
        return encargadoRepository.findAll();
    }
    
    public Encargado getById(Long id) {
        return encargadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Encargado no encontrado"));
    }


    public Encargado create(Encargado encargado) {
        return encargadoRepository.save(encargado);
    }

    public Encargado update(Long id, Encargado updated) {
        Encargado encargado = encargadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Encargado no encontrado"));

        encargado.setNombre(updated.getNombre());
        encargado.setUsername(updated.getUsername());
        encargado.setPasswordHash(updated.getPasswordHash());
        encargado.setUpdatedAt(java.time.LocalDateTime.now());
        return encargadoRepository.save(encargado);
    }

    public void delete(Long id) {
        encargadoRepository.deleteById(id);
    }
}

