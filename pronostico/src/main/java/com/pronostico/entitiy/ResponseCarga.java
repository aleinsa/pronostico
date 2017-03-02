package com.pronostico.entitiy;

/**
 * @author Alejandro Insaurralde
 *
 */
public class ResponseCarga {
	
	private String codigo ;
	private String mensaje;
	
	public ResponseCarga(String codigo, String mensaje) {
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getMensaje() {
		return mensaje;
	}


}
