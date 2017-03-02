package com.pronostico.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.pronostico.entitiy.Planeta;

/**
 * @author Alejandro Insaurralde
 * 
 * Clase del tipo RowMapper en donde retorna 
 * un objeto Planeta al consultar por BBDD
 *
 */
public class PlanetaMapper implements RowMapper<Planeta>{

	@Override
	public Planeta mapRow(ResultSet rs, int rowNum) throws SQLException {
		Planeta planeta = new Planeta(rs.getInt("id_planeta"), rs.getInt("radio"), rs.getInt("velocidad"), rs.getInt("rotacion"));
		return planeta;
	}

}
