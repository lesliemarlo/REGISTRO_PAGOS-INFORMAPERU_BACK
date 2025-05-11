package com.informaperu.web.registropagos.google;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.api.client.json.jackson2.*;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;

import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

import jakarta.annotation.PostConstruct;

@Service
public class GoogleDriveService {
    private static final String APPLICATION_NAME = "informaperu_pagos";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credenciales.json"; // ponlo en resources
    private static final String FOLDER_ID = "1Q4CZ3dzrCCIducVvQGJAIq2SDDi5DqSZ";
    
    private Drive driveService;
    
    @PostConstruct
    public void init() throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential.fromStream(
                new ClassPathResource("credenciales.json").getInputStream())
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        
        driveService = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    
    public String uploadFile(MultipartFile file) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(FOLDER_ID));
        
        FileContent mediaContent = new FileContent(file.getContentType(), convert(file));
        
        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
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
    public File getFile(String fileId) throws IOException {
        return driveService.files().get(fileId).execute();
    }
    
    /**
     * Eliminar un archivo por su ID
     */
    public void deleteFile(String fileId) throws IOException {
        driveService.files().delete(fileId).execute();
    }
}