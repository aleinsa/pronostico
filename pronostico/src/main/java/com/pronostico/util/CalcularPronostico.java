package com.pronostico.util;

import com.pronostico.entitiy.Planeta;

/**
 * @author Alejandro Insaurralde
 * 
 * Clase que calcula el pronostico segun
 * la alineacion de los planetas
 *
 */
public class CalcularPronostico {
	
	
	/**
	 * retorna la coordenada X en el plano
	 * @param planeta
	 * @param dia
	 * @return coordX
	 */
	public static double getCoordX (Planeta planeta, int dia) {
		// x = r * cons ( g& )  => g& = v& * t& =>  x= r* cons ( v& * t& )
		return getCoordX (planeta.getRadio(), planeta.getVelocidad(), planeta.getSentido(), dia);
	}
	/**
	 * retorna la coordenada X en el plano
	 * @param radio
	 * @param velocidad
	 * @param direccion
	 * @param dia
	 * @return coordX
	 */
	public static double getCoordX (int radio, int velocidad, int direccion, int dia) {
		// x = r * cons ( g& )  => g& = v& * t& =>  x= r* cons ( v& * t& )
		double x = radio * Math.cos(velocidad * dia * direccion);
		return x;
	}
	
	/**
	 * retorna la coordenada Y en el plano
	 * @param planet
	 * @param day
	 * @return coordY
	 */
	public static double getCoordY (Planeta planet, int day) {
		// x = r * sin ( g& )  => g& = v& * t& =>  x= r* sen ( v& * t& )
		return getCoordY (planet.getRadio(), planet.getVelocidad(), planet.getSentido(), day);
	}
	/**
	 *  retorna la coordenada Y en el plano
	 * @param radio
	 * @param velocidad
	 * @param direccion
	 * @param dia
	 * @return coordY
	 */
	public static double getCoordY (int radio, int velocidad, int direccion, int dia) {
		// x = r * sin ( g& )  => g& = v& * t& =>  x= r* sen ( v& * t& )
		double x = radio * Math.sin(velocidad * dia * direccion);
		return x;
	}
	
	/**
	 * Retorna el perimetro del trianglo
	 * @param a
	 * @param b
	 * @param c
	 * @return perimeter
	 */
	public static double perimetroTriangulo(double[] a, double[] b, double[] c){
		// P = L1 + L2 + L3
		double ab = distanciaEntrePuntos(a,b);
		double bc = distanciaEntrePuntos(b,c);
		double ca = distanciaEntrePuntos(c,a);
		double perimeter = ab + bc + ca;
		return perimeter;
	}
	
	/**
	 * Retorna la distancia entre dos puntos
	 * @param a
	 * @param b
	 * @return diastancia
	 */
	public static double distanciaEntrePuntos(double[] a, double[] b){
		// d^2 = (xa-xb)^2 + (ya-yb)^2 
		return Math.sqrt( Math.pow( (a[0] - b[0]), 2 ) + Math.pow( (a[1] - b[1]), 2 ) );
	}
	
	
	/**
	 * Retorna la orientacion en un triangulo
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static int orientacionTriangulo(double[] a, double[] b, double[] c){
		// (A1.x - A3.x) * (A2.y - A3.y) - (A1.y - A3.y) * (A2.x - A3.x)
		double x = (a[0]-c[0])*(b[1]-c[1]) - (a[1]-c[1])*(b[0]-c[0]);
		return (x >= 0.0 ? 1:-1);
	}
	
	/**
	 * Retorna true si el punto p está en el área
	 * @param a
	 * @param b
	 * @param c
	 * @param p
	 * @return true or false
	 */
	public static boolean triangleInPointByOrientation(double[] a, double[] b, double[] c, double[] p){
		int abc = orientacionTriangulo(a, b, c);
		int abp = orientacionTriangulo(a, b, p);
		int bcp = orientacionTriangulo(b, c, p);
		int cap = orientacionTriangulo(c, a, p);
		if(abc==1 && abp==1 && bcp==1 && cap==1) {
			return true;
		} else if (abc==-1 && abp==-1 && bcp==-1 && cap==-1) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Retorna true si los puntos estan alineados
	 * @param a
	 * @param b
	 * @param c
	 * @return true or false
	 */
	public static boolean losPuntosEstanAlineados(double[] a, double[] b, double[] c, int precision) {
		// P1 = bx - ax / by - ay   ^   P2 = cx - bx / cy - by  => P1 = P2
		// 
		double ba= b[1]-a[1];
		double cb = c[1]-b[1];
		if(ba==0 || cb==0) {
			return (ba==0 && cb==0) ;
		}
		double p1 = (b[0]-a[0])/ba;
		double p2 = (c[0]-b[0])/cb;
		int decimals = 10;
		if(precision >= 0 && precision < 4) {
			decimals = (int) Math.pow(10,precision);
		} 
		p1 = Math.rint(p1*decimals)/decimals;
        p2 = Math.rint(p2*decimals)/decimals;
		return  p1 == p2; 
	}

}
