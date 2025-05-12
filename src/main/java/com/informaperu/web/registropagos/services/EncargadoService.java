package com.informaperu.web.registropagos.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.informaperu.web.registropagos.model.Asesor;
import com.informaperu.web.registropagos.model.Encargado;
import com.informaperu.web.registropagos.model.Estado;
import com.informaperu.web.registropagos.repository.EncargadoRepository;
import com.informaperu.web.registropagos.security.AsesorDTO;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EncargadoService {
    private final EncargadoRepository encargadoRepository;

    public EncargadoService(EncargadoRepository encargadoRepository) {
        this.encargadoRepository = encargadoRepository;
    }

    @Transactional(readOnly = true)
    public List<Encargado> getAll() {
        return encargadoRepository.findByEstado(Estado.HABILITADO);
    }

    @Transactional(readOnly = true)
    public Encargado getById(Long id) {
        return encargadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Encargado no encontrado"));
    }

    @Transactional
    public Encargado create(Encargado encargado) {
        return encargadoRepository.save(encargado);
    }

    @Transactional(timeout = 10)
    public Encargado update(Long id, Encargado updated) {
        Encargado encargado = encargadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Encargado no encontrado"));

        encargado.setNombre(updated.getNombre());
        encargado.setUsername(updated.getUsername());
        encargado.setUpdatedAt(LocalDateTime.now());
        return encargadoRepository.save(encargado);
    }

    @Transactional
    public void deshabilitar(Long id) {
        Encargado encargado = getById(id);
        encargado.setEstado(Estado.DESHABILITADO);
        encargado.setUpdatedAt(LocalDateTime.now());
        encargadoRepository.save(encargado);
    }

    @Transactional
    public void delete(Long id) {
        encargadoRepository.deleteById(id);
    }

    @Transactional
    public Encargado restore(Long id) {
        Encargado encargado = encargadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Encargado no encontrado"));

        if (encargado.getEstado() != Estado.DESHABILITADO) {
            throw new RuntimeException("No está en estado recuperable");
        }

        encargado.setEstado(Estado.HABILITADO);
        encargado.setUpdatedAt(LocalDateTime.now());
        return encargadoRepository.save(encargado);
    }
}