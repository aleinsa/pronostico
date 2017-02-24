package com.pronostico.entitiy;

import java.text.DecimalFormat;
import com.pronostico.util.CalcularPronostico;

public class PuntosEjeXY {
	
	private int dia;
	private double coordX;
	private double coordY;
	private double perimetroTriangulo;
	
	
	public PuntosEjeXY(Planeta planet, int dia){
		this.dia = dia;
		this.coordX = CalcularPronostico.getCoordX(planet, dia);
		this.coordY = CalcularPronostico.getCoordY(planet, dia);
		
	}
	
	public double[] getPuntosXY() {
		double[] puntosXY = { coordX , coordY };
		return puntosXY;
	}

	public int getDia() {
		return dia;
	}

	public double getCoordX() {
		return coordX;
	}

	public double getCoordY() {
		return coordY;
	}
	
	public double getPerimetroTriangulo() {
		return perimetroTriangulo;
	}

	public void setPerimetroTriangulo(double perimetroTriangulo) {
		this.perimetroTriangulo = perimetroTriangulo;
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return "" + df.format(this.coordX) +"," + df.format(this.coordY) + "";
	}

}
