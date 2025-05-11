package com.informaperu.web.registropagos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datos del cliente
    @JsonProperty("cliente_dni")
    @Column(name = "cliente_dni")
    private String clienteDni;

    @JsonProperty("cliente_nombre")
    @Column(name = "cliente_nombre")
    private String clienteNombre;

    @JsonProperty("cliente_cartera")
    @Column(name = "cliente_cartera")
    private String clienteCartera;

    @JsonProperty("cliente_numero_producto")
    @Column(name = "cliente_numero_producto")
    private String clienteNumeroProducto;

    // Datos del asesor
    @JsonProperty("asesor_dni")
    @Column(name = "asesor_dni")
    private String asesorDni;

    @JsonProperty("asesor_nombre")
    @Column(name = "asesor_nombre")
    private String asesorNombre;

    @JsonProperty("asesor_rango")
    @Column(name = "asesor_rango")
    private String asesorRango;

    // Datos del encargado
    @JsonProperty("encargado_username")
    @Column(name = "encargado_username")
    private String encargadoUsername;

    @JsonProperty("encargado_nombre")
    @Column(name = "encargado_nombre")
    private String encargadoNombre;

    // Datos del pago
    @JsonProperty("fecha_voucher")
    @Column(name = "fecha_voucher")
    private LocalDate fechaVoucher;

    @JsonProperty("tipo_pago")
    @Column(name = "tipo_pago")
    @Enumerated(EnumType.STRING)
    private TipoPago tipoPago;

    @JsonProperty("importe")
    @Column(name = "importe")
    private BigDecimal importe;

    
    
    @JsonProperty("empresa")
    @Column(name = "empresa")
    @Enumerated(EnumType.STRING)
    private Empresa empresa;

    @JsonProperty("voucher_link")
    @Column(name = "voucher_link")
    private String voucherLink;

    @JsonProperty("fecha_registro")
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @JsonProperty("fecha_act")
    @Column(name = "fecha_act")
    private LocalDateTime fechaAct;

    @PrePersist
    public void prePersist() {
        fechaRegistro = LocalDateTime.now();
        fechaAct = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaAct = LocalDateTime.now();
    }

    public enum TipoPago {
        POLITICA, EXCEPCION, PARCIAL, CONVENIO
    }

    public enum Empresa {
    	PROEMPRESA, INCLUSIVA
    }
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado = Estado.HABILITADO;

}
