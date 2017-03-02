package com.pronostico.core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.pronostico.dao.PronosticoDAO;
import com.pronostico.datos.ClimaEnum;
import com.pronostico.entitiy.Clima;
import com.pronostico.entitiy.Planeta;
import com.pronostico.entitiy.PuntosEjeXY;
import com.pronostico.entitiy.ResponseClima;
import com.pronostico.entitiy.ResponsePeriodos;
import com.pronostico.util.CalcularPronostico;

/**
 * @author Alejandro Insaurralde
 * 
 * Clase que calcula y consulta los
 * pronosticos y guarda en base de datos
 *
 */
public class PronosticoCore {
	
	Logger logger = Logger.getLogger(PronosticoCore.class.getName());
	
	@Autowired
	public PronosticoDAO pronosticoDAO;
	
	private Planeta ferengi;
	private Planeta vulcano;
	private Planeta betasoide;
	private final double[] SOL = {0,0};
	

	/**
	 * Metodo que realiza los calculos de pronosticos
	 * en la cargar inicial
	 * @param anios
	 * @param precision
	 * @throws SQLException 
	 */
	public void pronosticar(int anios,int precision) throws SQLException {
		
		ferengi = pronosticoDAO.getFerengi();
		betasoide= pronosticoDAO.getBetasoide();
		vulcano = pronosticoDAO.getVulcano();

		int diezAnios = 360 * anios;
		for (int dia = 0; dia <= diezAnios; dia++) {
			List<Planeta> planetas = new ArrayList<>();
			logger.info("Pronosticando dia: " + dia);
			setCoordenadasXY(dia);
			if (losPuntosAlineados(precision)) {
				logger.info("Los planetas estan en linea");
				if (isSolAlinedosConPlanetas(precision)) {
					logger.info("Los planetas estan alienados con el SOL");
					Clima clima = new Clima(ClimaEnum.SEQUIA.getIdClima(), ClimaEnum.SEQUIA.getClima());
					setClima(clima);
					setListPlanetas(planetas);
					pronosticoDAO.save(planetas);
				} else {
					logger.info("Los planetas no estan alienados con el SOL");
					Clima clima = new Clima(ClimaEnum.OPTIMO.getIdClima(), ClimaEnum.OPTIMO.getClima());
					setClima(clima);
					setListPlanetas(planetas);
					pronosticoDAO.save(planetas);
				}
			} else {
				logger.info("Los planetas forman un triangulo");
				if (isSolEnElTriangulo()) {
					logger.info("El SOL esta dentro del triangulo");
					Clima clima = new Clima(ClimaEnum.LLUVIA.getIdClima(), ClimaEnum.LLUVIA.getClima());
					setClima(clima);
					setListPlanetas(planetas);
					double perimetro = CalcularPronostico.perimetroTriangulo(vulcano.getPuntosXY(), ferengi.getPuntosXY(), betasoide.getPuntosXY());
					pronosticoDAO.save(planetas,perimetro);
				} else {
					Clima clima = new Clima(ClimaEnum.NOLLUVIA.getIdClima(), ClimaEnum.NOLLUVIA.getClima());
					setClima(clima);
					setListPlanetas(planetas);
					pronosticoDAO.save(planetas);
					logger.info("El SOL esta fuera del triangulo");
				}
			}
		}	
	}
	
	/**
	 * Metodo que setea el clima 
	 * @param clima
	 */
	private void setClima(Clima clima) {
		vulcano.setClima(clima);
		betasoide.setClima(clima);
		ferengi.setClima(clima);
	}
	

	/**
	 * Metodo que setea las coordanadas de los planetas
	 * segun el dia  
	 * @param dia
	 */
	private void setCoordenadasXY(int dia){
		
		vulcano.setPuntosEjeXY(new PuntosEjeXY(vulcano, dia));
		betasoide.setPuntosEjeXY(new PuntosEjeXY(betasoide, dia));
		ferengi.setPuntosEjeXY(new PuntosEjeXY(ferengi, dia));
	}

	/**
	 * @param planetas
	 */
	private void setListPlanetas(List<Planeta> planetas) {
		planetas.add(vulcano);
		planetas.add(ferengi);
		planetas.add(betasoide);
	}

	/**
	 * Metodo que verifica si los planetas estan
	 * alineados con el sol
	 * @param precision
	 * @return boolean
	 */
	private boolean isSolAlinedosConPlanetas(int precision) {
		boolean op1 = CalcularPronostico.losPuntosEstanAlineados(SOL, ferengi.getPuntosXY(), betasoide.getPuntosXY(), precision);
		boolean op2 = CalcularPronostico.losPuntosEstanAlineados(SOL, vulcano.getPuntosXY(), ferengi.getPuntosXY(), precision);
		return op1 && op2;
	}

	/**
	 * Metodo que verifica si los planetas estan alineados
	 * @param precision
	 * @return boolean
	 */
	private boolean losPuntosAlineados(int precision) {
		return CalcularPronostico.losPuntosEstanAlineados(betasoide.getPuntosXY(), vulcano.getPuntosXY(), ferengi.getPuntosXY(), precision);
	}

	/**
	 * Metodo que verifica si el sol esta
	 * dentro del triangulo
	 * @return boolean
	 */
	private boolean isSolEnElTriangulo() {
		return CalcularPronostico.triangleInPointByOrientation(ferengi.getPuntosXY(), vulcano.getPuntosXY(), betasoide.getPuntosXY(), SOL);
	}
	
	/**
	 * Metodo que consulta en clima en BBDD
	 * @param dia
	 * @return ResponseClima
	 * @throws SQLException 
	 */
	public ResponseClima getClima(int dia) throws SQLException{
		return pronosticoDAO.getClima(dia);
	}
	/**
	 * Metodo que consulta el dia mas lluvioso en BBDD
	 * @return dia
	 * @throws SQLException 
	 */
	public int getDiaMasLluvioso() throws SQLException{
		return pronosticoDAO.getDiaMasLluvioso();
	}
	/**
	 * Metodo que consulta periodos de sequia en BBDD
	 * @return ResponsePeriodos
	 * @throws SQLException 
	 */
	public ResponsePeriodos getPeriodosSequia() throws SQLException{
		return pronosticoDAO.getPeriodosSequia();
	}
	/**
	 * Metodo que consulta periodos de lluvia en BBDD
	 * @return ResponsePeriodos
	 * @throws SQLException 
	 */
	public ResponsePeriodos getPeriodosLluvia() throws SQLException{
		return pronosticoDAO.getPeriodosLluvia();
	}
	/**
	 * Metodo que consulta periodos de optimas
	 * condiciones en BBDD
	 * @return
	 * @throws SQLException 
	 */
	public ResponsePeriodos getPeriodosOptimo() throws SQLException{
		return pronosticoDAO.getPeriodosOptimo();
	}
	/**
	 * Metodo que consulta periodos que no llovio en BBDD
	 * @return
	 * @throws SQLException 
	 */
	public ResponsePeriodos getPeriodosNoLluvia() throws SQLException{
		return pronosticoDAO.getPeriodosNoLluvia();
	}
	/**
	 * Metodo que que borra las tablas y las
	 * vuelve a crear con los insert de los planetas
	 * y los climas
	 * @throws SQLException
	 */
	public void dropCreateTables() throws SQLException{
		pronosticoDAO.dropCreateTables();;
	}
	

}
