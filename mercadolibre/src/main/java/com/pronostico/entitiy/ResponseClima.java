package com.pronostico.entitiy;

public class ResponseClima {
	
	private int dia;
	private String clima;
	
	public ResponseClima(int dia, String clima) {
		this.dia = dia;
		this.clima = clima;
	}
	
	public int getDia() {
		return dia;
	}
	public String getClima() {
		return clima;
	}


}
