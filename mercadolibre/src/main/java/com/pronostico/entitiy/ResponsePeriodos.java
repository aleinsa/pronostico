package com.pronostico.entitiy;

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
