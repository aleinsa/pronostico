package com.pronostico.dao;

import java.util.List;

import com.pronostico.entitiy.Planeta;
import com.pronostico.entitiy.ResponseClima;
import com.pronostico.entitiy.ResponsePeriodos;


public interface PronosticoDAO {
	
	public void save(List<Planeta> planetas);
	public void save(List<Planeta> planetas,double preimetro);
	public ResponseClima getClima(int dia);
	public ResponsePeriodos getDiaMasLluvioso();
	public ResponsePeriodos getPeriodosSequia();
	public ResponsePeriodos getPeriodosLluvia();
	public ResponsePeriodos getPeriodosOptimo();
	public ResponsePeriodos getPeriodosNoLluvia();
	

}
