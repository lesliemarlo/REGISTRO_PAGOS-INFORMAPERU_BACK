package com.informaperu.web.registropagos.security;

import com.informaperu.web.registropagos.model.Encargado;

public class LoginResponse {
    private boolean success;
    private String message;
    private Encargado encargado;

    // Constructores
    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public LoginResponse(boolean success, String message, Encargado encargado) {
        this.success = success;
        this.message = message;
        this.encargado = encargado;
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Encargado getEncargado() {
        return encargado;
    }

    public void setEncargado(Encargado encargado) {
        this.encargado = encargado;
    }
}
