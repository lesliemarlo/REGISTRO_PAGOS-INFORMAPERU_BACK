package com.informaperu.web.registropagos.security;

import org.springframework.stereotype.Service;
import com.informaperu.web.registropagos.model.Encargado;
import com.informaperu.web.registropagos.repository.EncargadoRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class AuthService {
    private final EncargadoRepository encargadoRepository;

    public AuthService(EncargadoRepository encargadoRepository) {
        this.encargadoRepository = encargadoRepository;
    }

    public LoginResponse login(String username, String password) {
        try {
            Encargado encargado = encargadoRepository.findByUsernameAndPasswordHash(username, password)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));

            return new LoginResponse(true, "Login exitoso", encargado);
        } catch (ResponseStatusException ex) {
            throw ex; // Para que el controller lo maneje y Spring devuelva el código 401 o el que definas
        } catch (Exception ex) {
            // Si algo inesperado ocurre, se lanza un 500
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en el servidor de autenticación", ex);
        }
    }
}
