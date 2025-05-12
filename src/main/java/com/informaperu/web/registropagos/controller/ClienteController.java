package com.informaperu.web.registropagos.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.informaperu.web.registropagos.model.Cliente;
import com.informaperu.web.registropagos.services.ClienteService;
import com.informaperu.web.registropagos.services.CsvService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

   
    private final ClienteService clienteService;
    private final CsvService csvService;

    public ClienteController(ClienteService clienteService, CsvService csvService) {
        this.clienteService = clienteService;
        this.csvService = csvService;
    }

    @GetMapping
    public List<Cliente> obtenerTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        return cliente.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar-por-dni")
    public ResponseEntity<?> buscarPorDni(@RequestParam String dni) {
        List<Cliente> clientes = clienteService.buscarPorDni(dni);
        if (clientes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientes);
    }
    
    @PostMapping("/cargar-csv")
    public ResponseEntity<String> cargarCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo está vacío");
        }

        try {
            String resultado = csvService.procesarArchivoClientes(file);
            return ResponseEntity.ok(resultado);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al procesar el archivo: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error inesperado: " + e.getMessage());
        }
    }
}
