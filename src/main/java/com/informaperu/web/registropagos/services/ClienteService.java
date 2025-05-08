package com.informaperu.web.registropagos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.informaperu.web.registropagos.model.Cliente;
import com.informaperu.web.registropagos.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> buscarPorDni(String dni) {
        return clienteRepository.findByClienteDni(dni);
    }
}