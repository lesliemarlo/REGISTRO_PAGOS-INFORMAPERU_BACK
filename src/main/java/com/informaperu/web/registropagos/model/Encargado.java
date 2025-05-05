package com.informaperu.web.registropagos.model;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "encargados")
@Data
public class Encargado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    @Column(nullable = false)
    private String nombre;
    
    @OneToMany(mappedBy = "encargado", fetch = FetchType.LAZY)
    private List<Asesor> asesores;
    
    @Column(name = "created_at", updatable = false)
    private java.sql.Timestamp createdAt;
    
    @Column(name = "updated_at")
    private java.sql.Timestamp updatedAt;
}