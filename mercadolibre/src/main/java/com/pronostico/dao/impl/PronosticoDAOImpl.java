package com.pronostico.dao.impl;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.pronostico.dao.PronosticoDAO;
import com.pronostico.entitiy.Planeta;
import com.pronostico.entitiy.ResponseClima;
import com.pronostico.entitiy.ResponsePeriodos;

@Repository
public class PronosticoDAOImpl implements PronosticoDAO {
	
	

	public PronosticoDAOImpl() {}

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public PronosticoDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}

	@Transactional
	public void save(List<Planeta> planetas) throws DataAccessException {

		for (Planeta planeta : planetas) {
			String sqlPlanetaClima = "INSERT INTO PRONOSTICO.T_PLANETA_CLIMA (ID_PLANETA,ID_CLIMA,DIA,COORDENADAX,COORDENADAY,PERIMETRO_TRIANGULO) VALUES (?,?,?,?,?,?)";
			jdbcTemplate.update(sqlPlanetaClima,
					new Object[] { planeta.getIdPlaneta(), planeta.getClima().getIdClima(),
							planeta.getPuntosEjeXY().getDia(), planeta.getPuntosEjeXY().getCoordX(),
							planeta.getPuntosEjeXY().getCoordY(), null });
		}
		String sqlSistemaSolar = "INSERT INTO PRONOSTICO.T_CLIMA_SISTEMA_SOLAR (ID_CLIMA,DIA,PERIMETRO_TRIANGULO) VALUES (?,?,?)";
		jdbcTemplate.update(sqlSistemaSolar,new Object[] {planetas.get(0).getClima().getIdClima(),planetas.get(0).getPuntosEjeXY().getDia(), null });
	}

	@Transactional
	public void save(List<Planeta> planetas, double perimetro) {

		for (Planeta planeta : planetas) {
			String sql = "INSERT INTO PRONOSTICO.T_PLANETA_CLIMA (ID_PLANETA,ID_CLIMA,DIA,COORDENADAX,COORDENADAY,PERIMETRO_TRIANGULO) VALUES (?,?,?,?,?,?)";
			jdbcTemplate.update(sql,
					new Object[] { planeta.getIdPlaneta(), planeta.getClima().getIdClima(),
							planeta.getPuntosEjeXY().getDia(), planeta.getPuntosEjeXY().getCoordX(),
							planeta.getPuntosEjeXY().getCoordY(), perimetro });
		}
		String sqlSistemaSolar = "INSERT INTO PRONOSTICO.T_CLIMA_SISTEMA_SOLAR (ID_CLIMA,DIA,PERIMETRO_TRIANGULO) VALUES (?,?,?)";
		jdbcTemplate.update(sqlSistemaSolar,new Object[] { planetas.get(0).getClima().getIdClima(),planetas.get(0).getPuntosEjeXY().getDia(), perimetro });

	}

	public ResponseClima getClima(int dia) {

		String sql = "SELECT SL.DIA AS DIA,CL.CLIMA AS CLIMA "
				+ "FROM PRONOSTICO.T_CLIMAS CL,PRONOSTICO.T_CLIMA_SISTEMA_SOLAR SL "
				+ "WHERE CL.ID_CLIMA = SL.ID_CLIMA  AND SL.DIA = ? ";

		ResponseClima responseClima = jdbcTemplate.queryForObject(sql, new Object[] { dia }, new ResponseClimaMapper());

		return responseClima;
	}

	public List<Planeta> getPlanetas() {

		return null;
	}

	public ResponsePeriodos getDiaMasLluvioso() {
		String sql = "SELECT COUNT(SL.DIA) AS PERIODOS, CL.CLIMA FROM PRONOSTICO.T_CLIMA_SISTEMA_SOLAR SL, PRONOSTICO.T_CLIMAS CL "
				+ "WHERE SL.ID_CLIMA = CL.ID_CLIMA " + "AND ID_CLIMA = 1 ;";

		ResponsePeriodos responsePeriodos = jdbcTemplate.queryForObject(sql, new ResponsePeriodosMapper());

		return responsePeriodos;

	}

	public ResponsePeriodos getPeriodosSequia() {
		String sql = "SELECT COUNT(SL.DIA) AS PERIODOS, CL.CLIMA FROM PRONOSTICO.T_CLIMA_SISTEMA_SOLAR SL, PRONOSTICO.T_CLIMAS CL "
				+ "WHERE SL.ID_CLIMA = CL.ID_CLIMA " + "AND CL.ID_CLIMA = 1";

		ResponsePeriodos responsePeriodos = jdbcTemplate.queryForObject(sql, new ResponsePeriodosMapper());

		return responsePeriodos;
	}

	public ResponsePeriodos getPeriodosLluvia() {
		String sql = "SELECT COUNT(SL.DIA) AS PERIODOS, CL.CLIMA FROM PRONOSTICO.T_CLIMA_SISTEMA_SOLAR SL, PRONOSTICO.T_CLIMAS CL "
				+ "WHERE SL.ID_CLIMA = CL.ID_CLIMA " + "AND CL.ID_CLIMA = 2 ";

		ResponsePeriodos responsePeriodos = jdbcTemplate.queryForObject(sql, new ResponsePeriodosMapper());

		return responsePeriodos;
	}

	public ResponsePeriodos getPeriodosOptimo() {
		String sql = "SELECT COUNT(SL.DIA) AS PERIODOS, CL.CLIMA FROM PRONOSTICO.T_CLIMA_SISTEMA_SOLAR SL, PRONOSTICO.T_CLIMAS CL "
				+ "WHERE SL.ID_CLIMA = CL.ID_CLIMA " + "AND CL.ID_CLIMA = 3 ";

		ResponsePeriodos responsePeriodos = jdbcTemplate.queryForObject(sql, new ResponsePeriodosMapper());

		return responsePeriodos;
	}

	public ResponsePeriodos getPeriodosNoLluvia() {
		String sql = "SELECT COUNT(SL.DIA) AS PERIODOS, CL.CLIMA FROM PRONOSTICO.T_CLIMA_SISTEMA_SOLAR SL, PRONOSTICO.T_CLIMAS CL "
				+ "WHERE SL.ID_CLIMA = CL.ID_CLIMA " + "AND CL.ID_CLIMA = 4";

		ResponsePeriodos responsePeriodos = jdbcTemplate.queryForObject(sql, new ResponsePeriodosMapper());

		return responsePeriodos;
	}

}
