// ClientDTO.java
package com.informaperu.web.registropagos.model;

public class ClientDTO {
	

	private String nombre_contacto;
    private String nombre_campana;
    private String codigo_producto;

    
    private String codigo_contacto;
    
    
    public String getCodigo_contacto() {
		return codigo_contacto;
	}

	public void setCodigo_contacto(String codigo_contacto) {
		this.codigo_contacto = codigo_contacto;
	}
	
	
    public String getNombre_contacto() {
        return nombre_contacto;
    }

    public void setNombre_contacto(String nombre_contacto) {
        this.nombre_contacto = nombre_contacto;
    }

    public String getNombre_campana() {
        return nombre_campana;
    }

    public void setNombre_campana(String nombre_campana) {
        this.nombre_campana = nombre_campana;
    }

    public String getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }
}
