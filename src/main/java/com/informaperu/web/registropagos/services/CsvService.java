package com.informaperu.web.registropagos.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

@Service
public class CsvService {

    private final JdbcTemplate jdbcTemplate;
    private final int batchSize;

    public CsvService(JdbcTemplate jdbcTemplate, @Value("${app.csv.batch-size:5000}") int batchSize) {
        this.jdbcTemplate = jdbcTemplate;
        this.batchSize = batchSize;
    }

    @Transactional
    public String procesarArchivoClientes(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo CSV está vacío");
        }

        long startTime = System.currentTimeMillis();
        int totalRecords = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withDelimiter(';')
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            // Validar encabezados
            List<String> requiredHeaders = List.of("clientes_dni", "cliente_nombre", "cliente_cartera", "cliente_numero_producto");
            if (!csvParser.getHeaderNames().containsAll(requiredHeaders)) {
                throw new IllegalArgumentException("El CSV no contiene todos los encabezados requeridos: " + requiredHeaders);
            }

            String sql = "INSERT INTO clientes (clientes_dni, cliente_nombre, cliente_cartera, cliente_numero_producto) VALUES (?, ?, ?, ?)";
            try (Connection conn = jdbcTemplate.getDataSource().getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                conn.setAutoCommit(false); // Deshabilitar autocommit

                for (CSVRecord record : csvParser) {
                    try {
                        if (record.size() < 4) {
                            System.err.println("Registro incompleto en línea " + record.getRecordNumber() + ": " + record);
                            continue;
                        }

                        pstmt.setString(1, record.get("clientes_dni"));
                        pstmt.setString(2, record.get("cliente_nombre"));
                        pstmt.setString(3, record.get("cliente_cartera"));
                        pstmt.setString(4, record.get("cliente_numero_producto"));
                        pstmt.addBatch();

                        totalRecords++;

                        if (totalRecords % batchSize == 0) {
                            pstmt.executeBatch();
                            conn.commit();
                            System.out.println("Lote de " + batchSize + " registros guardado. Total procesados: " + totalRecords);
                        }
                    } catch (Exception e) {
                        System.err.println("Error procesando registro " + totalRecords + " en línea " + record.getRecordNumber() + ": " + e.getMessage());
                    }
                }

                // Lote final
                if (totalRecords % batchSize != 0) {
                    pstmt.executeBatch();
                    conn.commit();
                    System.out.println("Lote final de " + (totalRecords % batchSize) + " registros guardado.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al insertar datos en la base de datos: " + e.getMessage(), e);
            }
        }

        long endTime = System.currentTimeMillis();
        return String.format("Procesados %d registros en %.2f segundos", 
                            totalRecords, (endTime - startTime) / 1000.0);
    }
}