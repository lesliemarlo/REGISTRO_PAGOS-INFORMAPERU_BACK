package com.informaperu.web.registropagos;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class TestGoogleDriveAuth {
    public static void main(String[] args) {
        try {
            // Cargar credenciales
            ClassPathResource resource = new ClassPathResource("credenciales.json");
            if (!resource.exists()) {
                throw new IOException("El archivo credenciales.json no se encuentra en src/main/resources");
            }
            GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream())
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));

            // Construir el servicio Drive
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Drive driveService = new Drive.Builder(
                    httpTransport,
                    GsonFactory.getDefaultInstance(),
                    new HttpCredentialsAdapter(credentials))
                    .setApplicationName("informaperu_pagos")
                    .build();

            // Probar una operación simple
            System.out.println("Conexión exitosa! Nombre de la aplicación: " + driveService.getApplicationName());
        } catch (IOException | GeneralSecurityException e) {
            System.err.println("Error al autenticar con Google Drive: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
