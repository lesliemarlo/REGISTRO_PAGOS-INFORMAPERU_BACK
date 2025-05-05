package com.informaperu.web.registropagos.controller;

import org.springframework.web.bind.annotation.*;

import com.informaperu.web.registropagos.security.AuthService;
import com.informaperu.web.registropagos.security.LoginRequest;
import com.informaperu.web.registropagos.security.LoginResponse;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }
    
    @GetMapping("/formulario")
    public String mostrarFormulario(HttpSession session) {
        Object usuario = session.getAttribute("usuario"); // Aquí debería ser algo como "usuario" o "dni"
        if (usuario == null) {
            return "redirect:/login"; // Si no hay sesión, los rediriges al login
        }
        return "formulario"; // Devuelves la página del formulario si todo está ok
    }


    // Aquí está el método para cerrar sesión
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // Invalida la sesión, cerrándola
        return "redirect:/login"; // Redirige a la página de login
    }
}
