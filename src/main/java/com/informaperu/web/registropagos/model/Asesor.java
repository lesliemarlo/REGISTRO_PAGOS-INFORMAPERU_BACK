package com.informaperu.web.registropagos.model;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "asesores")
@Data
public class Asesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String dni;
    
    @Column(nullable = false)
    private String nombre;
    
    private String rango;
    
    @ManyToOne
    @JoinColumn(name = "encargado_id", nullable = false)
    @JsonIgnore
    private Encargado encargado;
    
    @Column(name = "created_at", updatable = false)
    private java.sql.Timestamp createdAt;
    
    @Column(name = "updated_at")
    private java.sql.Timestamp updatedAt;
}