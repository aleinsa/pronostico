package com.pronostico.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.pronostico.entitiy.ResponsePeriodos;

	/**
	 * @author Alejandro Insaurralde
	 * 
	 * Clase del tipo RowMapper en donde retorna 
	 * un objeto ResponseClimaMapper al consultar por BBDD
	 *
	 */
	public class ResponsePeriodosMapper implements RowMapper<ResponsePeriodos> {
		public ResponsePeriodos mapRow(ResultSet rs, int arg1) throws SQLException {
			ResponsePeriodos responsePeriodos = new ResponsePeriodos(rs.getInt("periodos"), rs.getString("clima"));
			return responsePeriodos;
		}

	}

