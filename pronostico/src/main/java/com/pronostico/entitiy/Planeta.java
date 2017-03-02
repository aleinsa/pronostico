package com.pronostico.entitiy;

/**
 * @author Alejandro Insaurralde
 * 
 */
public class Planeta {
	
	private int idPlaneta;
	private String nombrePlaneta;
	private int velocidad;
	private int radio;
	private int sentido;
	private Clima clima;
	private PuntosEjeXY puntosEjeXY;
	
	
	public Planeta(int idPlaneta,int radio,int velocidad,int sentido){
		this.idPlaneta = idPlaneta;
		this.radio=radio;
		this.velocidad=velocidad;
		this.sentido=sentido;
	}
	
	public int getIdPlaneta() {
		return idPlaneta;
	}
	public void setIdPlaneta(int idPlaneta) {
		this.idPlaneta = idPlaneta;
	}
	public String getNombrePlaneta() {
		return nombrePlaneta;
	}
	public void setNombrePlaneta(String nombrePlaneta) {
		this.nombrePlaneta = nombrePlaneta;
	}
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	public int getRadio() {
		return radio;
	}
	public void setRadio(int radio) {
		this.radio = radio;
	}
	public int getSentido() {
		return sentido;
	}
	public void setSentido(int sentido) {
		this.sentido = sentido;
	}
	
	public Clima getClima() {
		return clima;
	}

	public void setClima(Clima clima) {
		this.clima = clima;
	}

	public double[] getPuntosXY() {
		return puntosEjeXY.getPuntosXY();
	}

	public PuntosEjeXY getPuntosEjeXY() {
		return puntosEjeXY;
	}

	public void setPuntosEjeXY(PuntosEjeXY puntosEjeXY) {
		this.puntosEjeXY = puntosEjeXY;
	}
	
	
}
