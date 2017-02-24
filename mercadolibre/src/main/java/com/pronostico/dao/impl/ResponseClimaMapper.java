package com.pronostico.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.pronostico.entitiy.ResponseClima;

public class ResponseClimaMapper implements RowMapper<ResponseClima>{
	public ResponseClima mapRow(ResultSet rs, int arg1) throws SQLException {
		ResponseClima responseClima = new ResponseClima(rs.getInt("dia"), rs.getString("clima"));
		return responseClima;
	}

}
