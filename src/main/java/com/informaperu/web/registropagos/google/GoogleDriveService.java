package com.informaperu.web.registropagos.google;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Service
public class GoogleDriveService {
    private static final String APPLICATION_NAME = "informaperu_pagos";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String FOLDER_ID = "1Q4CZ3dzrCCIducVvQGJAIq2SDDi5DqSZ";

    private Drive driveService;
    private final Dotenv dotenv;

    public GoogleDriveService() {
        this.dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
    }

    @PostConstruct
    public void init() throws IOException, GeneralSecurityException {
        try {
            String credentialsJson = buildCredentialsJson();
            System.out.println("Credenciales JSON generadas:\n" + credentialsJson); // Para depuración
            
            InputStream in = new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8));
            GoogleCredential credential = GoogleCredential.fromStream(in)
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));

            this.driveService = new Drive.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (Exception e) {
            System.err.println("Error al inicializar Google Drive Service:");
            e.printStackTrace();
            throw e;
        }
    }

    private String buildCredentialsJson() {
        // Obtener y limpiar la clave privada
        String privateKey = dotenv.get("GOOGLE_PRIVATE_KEY", "")
                .replace("\\n", "\n")  // Convierte \\n a saltos de línea reales
                .replace("\n", "\\n")  // Escapa los saltos de línea para JSON
                .replace("\"", "\\\""); // Escapa las comillas

        // Verificar que la clave privada no esté vacía
        if (privateKey.isEmpty()) {
            throw new RuntimeException("La clave privada (GOOGLE_PRIVATE_KEY) no está configurada");
        }

        return String.format(
            "{\n" +
            "  \"type\": \"%s\",\n" +
            "  \"project_id\": \"%s\",\n" +
            "  \"private_key_id\": \"%s\",\n" +
            "  \"private_key\": \"%s\",\n" +
            "  \"client_email\": \"%s\",\n" +
            "  \"client_id\": \"%s\",\n" +
            "  \"auth_uri\": \"%s\",\n" +
            "  \"token_uri\": \"%s\",\n" +
            "  \"auth_provider_x509_cert_url\": \"%s\",\n" +
            "  \"client_x509_cert_url\": \"%s\",\n" +
            "  \"universe_domain\": \"%s\"\n" +
            "}",
            "service_account", // Tipo fijo como service_account
            dotenv.get("GOOGLE_PROJECT_ID", ""),
            dotenv.get("GOOGLE_PRIVATE_KEY_ID", ""),
            privateKey,
            dotenv.get("GOOGLE_CLIENT_EMAIL", ""),
            dotenv.get("GOOGLE_CLIENT_ID", ""),
            dotenv.get("GOOGLE_AUTH_URI", "https://accounts.google.com/o/oauth2/auth"),
            dotenv.get("GOOGLE_TOKEN_URI", "https://oauth2.googleapis.com/token"),
            dotenv.get("GOOGLE_AUTH_PROVIDER_CERT_URL", "https://www.googleapis.com/oauth2/v1/certs"),
            dotenv.get("GOOGLE_CLIENT_CERT_URL", ""),
            dotenv.get("GOOGLE_UNIVERSE_DOMAIN", "googleapis.com")
        );
    }

    // Resto de los métodos permanecen igual...
    public String uploadFile(MultipartFile file) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(FOLDER_ID));
        
        FileContent mediaContent = new FileContent(file.getContentType(), convert(file));
        
        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id,webViewLink")
                .execute();
                
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

    public File getFile(String fileId) throws IOException {
        return driveService.files().get(fileId).execute();
    }

    public void deleteFile(String fileId) throws IOException {
        driveService.files().delete(fileId).execute();
    }
}