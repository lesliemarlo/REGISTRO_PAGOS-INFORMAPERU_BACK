package com.informaperu.web.registropagos.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//ClientController.java
import org.springframework.web.bind.annotation.*;

import com.informaperu.web.registropagos.model.ClientDTO;
import com.informaperu.web.registropagos.services.ClientService;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{dni}")
    public ResponseEntity<ClientDTO> buscarClientePorDni(@PathVariable("dni") String dni) {
        Optional<ClientDTO> clienteOptional = clientService.buscarPorCodigoContacto(dni);

        if (clienteOptional.isPresent()) {
            // Si se encuentra el cliente, retorna el cliente con código HTTP 200 OK
            return ResponseEntity.ok(clienteOptional.get());
        } else {
            // Si no se encuentra el cliente, retorna un código HTTP 404 (No encontrado)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // O puedes devolver un mensaje personalizado aquí si lo deseas
        }
    }
}
