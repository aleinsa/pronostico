package com.pronostico.datos;

public enum PlanetasEnum {
	
	FERENGIS(1,"Ferengis"),
	BETASOIDES(2,"Betasoides"),
	VULCANO(3,"Vulcanos");
	
	private int idPlaneta;
	private String nombrePlaneta;
	
	private PlanetasEnum(int idPlaneta, String nombrePlaneta) {
		this.idPlaneta = idPlaneta;
		this.nombrePlaneta = nombrePlaneta;
	}

	public int getIdPlaneta() {
		return idPlaneta;
	}

	public String getNombrePlaneta() {
		return nombrePlaneta;
	}
}
