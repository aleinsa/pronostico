package com.pronostico.entitiy;

/**
 * @author Alejandro Insaurralde
 * 
 * Clase response cuando se solicita 
 * los periodos de los distintos climas
 * 
 *
 */
public class ResponsePeriodos {
	
	private int periodo;
	private String clima;
	
	public ResponsePeriodos(int periodo, String clima) {
		super();
		this.periodo = periodo;
		this.clima = clima;
	}
	public int getPeriodo() {
		return periodo;
	}
	public String getClima() {
		return clima;
	}
	
	

}
