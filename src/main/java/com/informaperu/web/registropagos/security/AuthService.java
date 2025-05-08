package com.informaperu.web.registropagos.security;

import org.springframework.stereotype.Service;
import com.informaperu.web.registropagos.model.Encargado;
import com.informaperu.web.registropagos.model.Estado;
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

            if (encargado.getEstado() != Estado.HABILITADO) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario deshabilitado");
            }

            return new LoginResponse(true, "Login exitoso", encargado);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en el servidor de autenticación", ex);
        }
    }

}
