package com.pronostico.entitiy;

public class Clima {

	private int idClima;
	private String clima;
	

	public Clima(int idClima, String clima) {

		this.idClima = idClima;
		this.clima = clima;
	}
	public long getIdClima() {
		return idClima;
	}
	public void setIdClima(int idClima) {
		this.idClima = idClima;
	}
	public String getClima() {
		return clima;
	}
	public void setClima(String clima) {
		this.clima = clima;
	}
	
	
}
