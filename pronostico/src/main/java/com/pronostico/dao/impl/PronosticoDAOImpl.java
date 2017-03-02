package com.pronostico.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.pronostico.dao.PronosticoDAO;
import com.pronostico.entitiy.Planeta;
import com.pronostico.entitiy.ResponseClima;
import com.pronostico.entitiy.ResponsePeriodos;

/**
 * @author Alejandro Insaurralde
 * 
 * Clase DAO que administra base de datos
 *
 */
@Repository
public class PronosticoDAOImpl implements PronosticoDAO {
	
	Logger logger = Logger.getLogger(PronosticoDAOImpl.class.getName());
	public PronosticoDAOImpl() {}
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public PronosticoDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	@Transactional
	public void save(List<Planeta> planetas) throws DataAccessException,SQLException {

		logger.info("insertando datos en BBDD");

			for (Planeta planeta : planetas) {
				
				String sqlPlanetaClima = "INSERT INTO PRONOSTICO.T_PLANETA_CLIMA (ID_PLANETA,ID_CLIMA,DIA,COORDENADAX,COORDENADAY) VALUES (?,?,?,?,?)";
				
				jdbcTemplate.update(sqlPlanetaClima,new Object[] { planeta.getIdPlaneta(),
																   planeta.getClima().getIdClima(),
																   planeta.getPuntosEjeXY().getDia(), 
																   planeta.getPuntosEjeXY().getCoordX(),
																   planeta.getPuntosEjeXY().getCoordY()});
			}
			
			String sqlSistemaSolar = "INSERT INTO PRONOSTICO.T_CLIMA_SISTEMA_SOLAR (ID_CLIMA,DIA,PERIMETRO_TRIANGULO) VALUES (?,?,?)";
			
			jdbcTemplate.update(sqlSistemaSolar,new Object[] {planetas.get(0).getClima().getIdClima(),
															  planetas.get(0).getPuntosEjeXY().getDia(),
															  null });
	
	}

	@Transactional
	public void save(List<Planeta> planetas, double perimetro) throws DataAccessException,SQLException  {
		
		logger.info("insertando datos en BBDD");

		for (Planeta planeta : planetas) {
			
			String sql = "INSERT INTO PRONOSTICO.T_PLANETA_CLIMA (ID_PLANETA,ID_CLIMA,DIA,COORDENADAX,COORDENADAY) VALUES (?,?,?,?,?)";
			
			jdbcTemplate.update(sql,new Object[] { planeta.getIdPlaneta(), 
												   planeta.getClima().getIdClima(),
												   planeta.getPuntosEjeXY().getDia(), 
												   planeta.getPuntosEjeXY().getCoordX(),
												   planeta.getPuntosEjeXY().getCoordY() });
		}
		
		String sqlSistemaSolar = "INSERT INTO PRONOSTICO.T_CLIMA_SISTEMA_SOLAR (ID_CLIMA,DIA,PERIMETRO_TRIANGULO) VALUES (?,?,?)";
		
		jdbcTemplate.update(sqlSistemaSolar,new Object[] { planetas.get(0).getClima().getIdClima(),
														   planetas.get(0).getPuntosEjeXY().getDia(),
														   perimetro });

	}

	public ResponseClima getClima(int dia) throws SQLException  {
		
		logger.info("Consultando clima en el dia: "+ dia + " en BBDD");

		String sql = "SELECT SL.DIA AS DIA,CL.CLIMA AS CLIMA "
				   + "FROM PRONOSTICO.T_CLIMAS CL,PRONOSTICO.T_CLIMA_SISTEMA_SOLAR SL "
				   + "WHERE CL.ID_CLIMA = SL.ID_CLIMA  AND SL.DIA = ? ";

		ResponseClima responseClima = jdbcTemplate.queryForObject(sql, new Object[] { dia }, new ResponseClimaMapper());

		return responseClima;
	}



	public int getDiaMasLluvioso() throws SQLException {
		
		logger.info("Consultando el dia mas lluvioso en BBDD");
		
		String sql =  "SELECT DIA FROM PRONOSTICO.T_CLIMA_SISTEMA_SOLAR WHERE PERIMETRO_TRIANGULO = "
																			+"(SELECT MAX(PERIMETRO_TRIANGULO) FROM PRONOSTICO.T_CLIMA_SISTEMA_SOLAR)";

		int dia= jdbcTemplate.queryForObject(sql, Integer.class);

		return dia;

	}

	public ResponsePeriodos getPeriodosSequia() throws SQLException {
		
		logger.info("Consultando periodos de sequia en BBDD");
		
		String sql = "SELECT COUNT(SL.DIA) AS PERIODOS, CL.CLIMA "
				   + "FROM PRONOSTICO.T_CLIMA_SISTEMA_SOLAR SL, PRONOSTICO.T_CLIMAS CL "
				   + "WHERE SL.ID_CLIMA = CL.ID_CLIMA "
				   + "AND CL.ID_CLIMA = 1";

		ResponsePeriodos responsePeriodos = jdbcTemplate.queryForObject(sql, new ResponsePeriodosMapper());

		return responsePeriodos;
	}

	public ResponsePeriodos getPeriodosLluvia() throws SQLException {
		
		logger.info("Consultando periodos de lluvia en BBDD");
		
		String sql = "SELECT COUNT(SL.DIA) AS PERIODOS, CL.CLIMA "
				   + "FROM PRONOSTICO.T_CLIMA_SISTEMA_SOLAR SL, PRONOSTICO.T_CLIMAS CL "
				   + "WHERE SL.ID_CLIMA = CL.ID_CLIMA "
				   + "AND CL.ID_CLIMA = 2 ";

		ResponsePeriodos responsePeriodos = jdbcTemplate.queryForObject(sql, new ResponsePeriodosMapper());

		return responsePeriodos;
	}

	public ResponsePeriodos getPeriodosOptimo() throws SQLException {
		
		logger.info("Consultando periodos optimos en BBDD");
		
		String sql = "SELECT COUNT(SL.DIA) AS PERIODOS, CL.CLIMA "
				   + "FROM PRONOSTICO.T_CLIMA_SISTEMA_SOLAR SL, PRONOSTICO.T_CLIMAS CL "
				   + "WHERE SL.ID_CLIMA = CL.ID_CLIMA "
				   + "AND CL.ID_CLIMA = 3 ";

		ResponsePeriodos responsePeriodos = jdbcTemplate.queryForObject(sql, new ResponsePeriodosMapper());

		return responsePeriodos;
	}

	public ResponsePeriodos getPeriodosNoLluvia() throws SQLException {
		
		logger.info("Consultando periodos que no llovio en BBDD");
		
		String sql = "SELECT COUNT(SL.DIA) AS PERIODOS, CL.CLIMA "
				   + "FROM PRONOSTICO.T_CLIMA_SISTEMA_SOLAR SL, PRONOSTICO.T_CLIMAS CL "
				   + "WHERE SL.ID_CLIMA = CL.ID_CLIMA "
				   + "AND CL.ID_CLIMA = 4";

		ResponsePeriodos responsePeriodos = jdbcTemplate.queryForObject(sql, new ResponsePeriodosMapper());

		return responsePeriodos;
	}

	@Override
	public Planeta getFerengi() throws SQLException {
		
		logger.info("Obteniendo informacion del planeta ferengi en BBDD");
		
		 String sql = "select id_planeta, velocidad,radio,rotacion from pronostico.t_planetas where nombre_planeta = ? ";
	     Planeta ferengi = jdbcTemplate.queryForObject(sql, new Object[]{"Ferengi"},new PlanetaMapper());
		 return ferengi;
		
	}

	@Override
	public Planeta getVulcano() throws SQLException{
		
		logger.info("Obteniendo informacion del planeta vulcano en BBDD");
		
		 String sql = "select id_planeta, velocidad,radio,rotacion from pronostico.t_planetas where nombre_planeta = ? ";
	     Planeta vulcano = jdbcTemplate.queryForObject(sql, new Object[]{"Vulcano"},new PlanetaMapper());
		 return vulcano;
	}

	@Override
	public Planeta getBetasoide() throws SQLException {
		
		logger.info("Obteniendo informacion del planeta betasoide en BBDD");
		
		 String sql = "select id_planeta, velocidad,radio,rotacion from pronostico.t_planetas where nombre_planeta = ? ";
	     Planeta betasoide = jdbcTemplate.queryForObject(sql, new Object[]{"Betasoide"},new PlanetaMapper());
		 return betasoide;
	}

	@Override
	public void dropCreateTables() throws SQLException {
		
		logger.info("haciendo drop de tablas en BBDD");
		
	    ClassPathResource resource = new ClassPathResource("drop_tables.sql");
	    ScriptUtils.executeSqlScript(dataSource.getConnection(), new EncodedResource(resource, "UTF-8"));
	    
	    logger.info("creando las tablas BBDD");
	    
	    resource = new ClassPathResource("create_tables.sql");
	    ScriptUtils.executeSqlScript(dataSource.getConnection(), new EncodedResource(resource, "UTF-8"));
	    
	    logger.info("insertando climas y planetas en  BBDD");
	    
	    resource = new ClassPathResource("insert_data.sql");
	    ScriptUtils.executeSqlScript(dataSource.getConnection(), new EncodedResource(resource, "UTF-8"));
	    
	}

}
