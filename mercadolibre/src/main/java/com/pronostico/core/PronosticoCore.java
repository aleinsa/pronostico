package com.pronostico.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.pronostico.dao.PronosticoDAO;
import com.pronostico.datos.ClimaEnum;
import com.pronostico.datos.PlanetasEnum;
import com.pronostico.entitiy.Clima;
import com.pronostico.entitiy.Planeta;
import com.pronostico.entitiy.PuntosEjeXY;
import com.pronostico.entitiy.ResponseClima;
import com.pronostico.entitiy.ResponsePeriodos;
import com.pronostico.util.CalcularPronostico;

public class PronosticoCore {
	
	public PronosticoCore() {}

	Logger logger = Logger.getLogger(PronosticoCore.class);
	
	@Autowired
	public PronosticoDAO pronosticoDAO;
	
	private final double[] SOL = {0,0};
	private Planeta vulcano = new Planeta(PlanetasEnum.VULCANO.getIdPlaneta(),1000,5,1);
	private Planeta ferengi = new Planeta(PlanetasEnum.FERENGIS.getIdPlaneta(),500,1,-1);
	private Planeta betasoide = new Planeta(PlanetasEnum.BETASOIDES.getIdPlaneta(),2000,3,-1);
	
	public void pronosticar(int anios,int precision) {

		int diezAnios = 360 * anios;
		for (int dia = 0; dia <= diezAnios; dia++) {
			List<Planeta> planetas = new ArrayList<Planeta>();
			logger.info("Pronosticando dia: " + dia);
			setCoordenadasXY(dia);
			if (losPuntosAlineados(precision)) {
				logger.debug("Los planetas estan en linea");
				if (isSolAlinedosConPlanetas(precision)) {
					logger.debug("Los planetas estan alienados con el SOL");
					Clima clima = new Clima(ClimaEnum.SEQUIA.getIdClima(), ClimaEnum.SEQUIA.getClima());
					setClima(clima);
					setListPlanetas(planetas);
					pronosticoDAO.save(planetas);
				} else {
					logger.debug("Los planetas no estan alienados con el SOL");
					Clima clima = new Clima(ClimaEnum.OPTIMO.getIdClima(), ClimaEnum.OPTIMO.getClima());
					setClima(clima);
					setListPlanetas(planetas);
					pronosticoDAO.save(planetas);
				}
			} else {
				logger.debug("Los planetas forman un triangulo");
				if (isSolEnElTriangulo()) {
					logger.debug("El SOL esta dentro del triangulo");
					Clima clima = new Clima(ClimaEnum.LLUVIA.getIdClima(), ClimaEnum.LLUVIA.getClima());
					setClima(clima);
					setListPlanetas(planetas);
					double perimetro = CalcularPronostico.trianglePerimeter(vulcano.getPuntosXY(), ferengi.getPuntosXY(), betasoide.getPuntosXY());
					pronosticoDAO.save(planetas,perimetro);
				} else {
					Clima clima = new Clima(ClimaEnum.NOLLUVIA.getIdClima(), ClimaEnum.NOLLUVIA.getClima());
					setClima(clima);
					setListPlanetas(planetas);
					pronosticoDAO.save(planetas);
					logger.debug("El SOL esta fuera del triangulo");
				}
			}
		}	
	}
	
	private void setClima(Clima clima) {
		vulcano.setClima(clima);
		betasoide.setClima(clima);
		ferengi.setClima(clima);
	}
	

	private void setCoordenadasXY(int dia){
		vulcano.setPuntosEjeXY(new PuntosEjeXY(vulcano, dia));
		betasoide.setPuntosEjeXY(new PuntosEjeXY(betasoide, dia));
		ferengi.setPuntosEjeXY(new PuntosEjeXY(ferengi, dia));
	}

	private void setListPlanetas(List<Planeta> planetas) {
		planetas.add(vulcano);
		planetas.add(ferengi);
		planetas.add(betasoide);
	}

	private boolean isSolAlinedosConPlanetas(int precision) {
		boolean op1 = CalcularPronostico.areThePointsAlined(SOL, ferengi.getPuntosXY(), betasoide.getPuntosXY(), precision);
		boolean op2 = CalcularPronostico.areThePointsAlined(SOL, vulcano.getPuntosXY(), ferengi.getPuntosXY(), precision);
		return op1 && op2;
	}

	private boolean losPuntosAlineados(int precision) {
		return CalcularPronostico.areThePointsAlined(betasoide.getPuntosXY(), vulcano.getPuntosXY(), ferengi.getPuntosXY(), precision);
	}

	private boolean isSolEnElTriangulo() {
		return CalcularPronostico.triangleInPointByOrientation(ferengi.getPuntosXY(), vulcano.getPuntosXY(), betasoide.getPuntosXY(), SOL);
	}
	
	public ResponseClima getClima(int dia){
		return pronosticoDAO.getClima(dia);
	}
	public ResponsePeriodos getDiaMasLluvioso(){
		return pronosticoDAO.getDiaMasLluvioso();
	}
	public ResponsePeriodos getPeriodosSequia(){
		return pronosticoDAO.getPeriodosSequia();
	}
	public ResponsePeriodos getPeriodosLluvia(){
		return pronosticoDAO.getPeriodosLluvia();
	}
	public ResponsePeriodos getPeriodosOptimo(){
		return pronosticoDAO.getPeriodosOptimo();
	}
	public ResponsePeriodos getPeriodosNoLluvia(){
		return pronosticoDAO.getPeriodosNoLluvia();
	}
	

}
