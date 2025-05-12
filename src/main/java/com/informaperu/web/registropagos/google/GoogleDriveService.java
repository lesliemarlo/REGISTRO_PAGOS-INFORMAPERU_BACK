package com.informaperu.web.registropagos.google;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import jakarta.annotation.PostConstruct;

@Service
public class GoogleDriveService {
    private static final String APPLICATION_NAME = "informaperu_pagos";
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credenciales.json";
    private static final String RENDER_CREDENTIALS_PATH = "/etc/secrets/credenciales.json";
    private static final String FOLDER_ID = "1Q4CZ3dzrCCIducVvQGJAIq2SDDi5DqSZ";

    private Drive driveService;

    @PostConstruct
    public void init() throws IOException, GeneralSecurityException {
        try {
            InputStream credentialsStream;

            File renderCredentials = new File(RENDER_CREDENTIALS_PATH);
            if (renderCredentials.exists()) {
                System.out.println("Usando credenciales de Render en: " + RENDER_CREDENTIALS_PATH);
                credentialsStream = new FileInputStream(renderCredentials);
            } else {
                System.out.println("Usando credenciales locales de resources");
                ClassPathResource resource = new ClassPathResource("credenciales.json");
                if (!resource.exists()) {
                    throw new IOException("El archivo credenciales.json no se encuentra en src/main/resources");
                }
                credentialsStream = resource.getInputStream();
            }

            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));

            driveService = new Drive.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    new HttpCredentialsAdapter(credentials))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (Exception e) {
            System.err.println("Error al inicializar GoogleDriveService: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Fallo en la inicialización de GoogleDriveService", e);
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(FOLDER_ID));

        FileContent mediaContent = new FileContent(file.getContentType(), convert(file));

        com.google.api.services.drive.model.File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id,webViewLink")
                .execute();

        // Hacer público el archivo
        Permission permission = new Permission()
                .setType("anyone")
                .setRole("reader");

        driveService.permissions().create(uploadedFile.getId(), permission).execute();

        return uploadedFile.getWebViewLink();
    }

    private java.io.File convert(MultipartFile file) throws IOException {
        java.io.File convFile = java.io.File.createTempFile("temp", null);
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    /**
     * Obtener un archivo por su ID
     */
    public com.google.api.services.drive.model.File getFile(String fileId) throws IOException {
        return driveService.files().get(fileId).execute();
    }

    /**
     * Eliminar un archivo por su ID
     */
    public void deleteFile(String fileId) throws IOException {
        driveService.files().delete(fileId).execute();
    }
}