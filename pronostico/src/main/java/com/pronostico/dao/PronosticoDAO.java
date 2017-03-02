package com.pronostico.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pronostico.entitiy.Planeta;
import com.pronostico.entitiy.ResponseClima;
import com.pronostico.entitiy.ResponsePeriodos;


public interface PronosticoDAO {
	
	public void save(List<Planeta> planetas) throws DataAccessException, SQLException;
	public void save(List<Planeta> planetas,double preimetro) throws DataAccessException, SQLException;
	public ResponseClima getClima(int dia) throws SQLException;
	public int getDiaMasLluvioso() throws SQLException;
	public ResponsePeriodos getPeriodosSequia() throws SQLException;
	public ResponsePeriodos getPeriodosLluvia() throws SQLException;
	public ResponsePeriodos getPeriodosOptimo() throws SQLException;
	public ResponsePeriodos getPeriodosNoLluvia() throws SQLException;
	public Planeta getFerengi() throws SQLException;
	public Planeta getVulcano() throws SQLException;
	public Planeta getBetasoide() throws SQLException;
	public void dropCreateTables() throws SQLException;
	

}
