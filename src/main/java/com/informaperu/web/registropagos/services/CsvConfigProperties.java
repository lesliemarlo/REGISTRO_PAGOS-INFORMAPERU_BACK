package com.informaperu.web.registropagos.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tu-app.csv")
public class CsvConfigProperties {
    private int batchSize = 1000; // Valor por defecto

    // Getter y Setter
    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
