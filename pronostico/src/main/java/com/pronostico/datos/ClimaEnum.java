package com.pronostico.datos;

public enum ClimaEnum {
	
	SEQUIA(1,"SEQUIA"),
	LLUVIA(2,"LLUVIA"),
	OPTIMO(3,"OPTIMO"),
	NOLLUVIA(4,"NOLLUVIA");
	
	private int idClima;
	private String clima;
	
	private ClimaEnum(int idClima, String clima) {
		this.idClima = idClima;
		this.clima = clima;
	}

	public int getIdClima() {
		return idClima;
	}

	public String getClima() {
		return clima;
	}

}
