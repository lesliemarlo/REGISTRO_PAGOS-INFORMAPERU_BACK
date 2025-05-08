package com.informaperu.web.registropagos.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Datos del cliente
	@JsonProperty("clientes_dni")
	@Column(name = "clientes_dni")
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
	
	public String getClienteNumeroProducto() {
		return clienteNumeroProducto != null ? clienteNumeroProducto.replaceAll("[\\r\\n]", "") : null;

	}


}
