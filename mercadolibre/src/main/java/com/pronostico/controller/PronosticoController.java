package com.pronostico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.pronostico.core.PronosticoCore;
import com.pronostico.entitiy.ResponseCarga;
import com.pronostico.entitiy.ResponseClima;
import com.pronostico.entitiy.ResponsePeriodos;

@RestController
public class PronosticoController {
	
	@Autowired
	public PronosticoCore pronosticoCore;
	
		@RequestMapping(value = "/dia/{dia}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseClima getClima(@PathVariable("dia") int dia) {
	
			 return pronosticoCore.getClima(dia);
		}
	
	   	@RequestMapping(value = "/periodos/sequia", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponsePeriodos getPeriodosSequia() {
			
			 return pronosticoCore.getPeriodosSequia();
		}
	   	
		@RequestMapping(value = "/periodos/lluvia", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponsePeriodos getPeriodosLluvia() {
			try{
				return pronosticoCore.getPeriodosLluvia();
			}catch (EmptyResultDataAccessException e) {
				return new ResponsePeriodos(0, "No hay clima cargado para ese dia");
			}

		}

		@RequestMapping(value = "/periodos/optimo", method = RequestMethod.GET, produces =MediaType.APPLICATION_JSON_VALUE)
		public ResponsePeriodos getPeriodosOptimo() {
			
			 return pronosticoCore.getPeriodosOptimo();
		}
		
		@RequestMapping(value = "/periodos/nolluvia", method = RequestMethod.GET, produces =MediaType.APPLICATION_JSON_VALUE)
		public ResponsePeriodos getPeriodosNoLluvia() {
			
			 return pronosticoCore.getPeriodosNoLluvia();
		}
		
		@RequestMapping(value = "/maslluvioso/", method = RequestMethod.PUT, produces =MediaType.APPLICATION_JSON_VALUE)
		public ResponsePeriodos getDiaMasLluvioso(){
			
			return pronosticoCore.getDiaMasLluvioso();
		}
		
		@RequestMapping(value = "/carga/{anios}", method = RequestMethod.PUT, produces =MediaType.APPLICATION_JSON_VALUE)
		public ResponseCarga inicarCarga(@PathVariable("anios") int anios){
			try{
				pronosticoCore.pronosticar(anios,1);
				return new ResponseCarga("OK", "CARGA OK");
			}catch (DataAccessException e) {
				return new ResponseCarga("Fail", "Fallo Carga de Pronostico");
			}catch (Exception e) {
				return new ResponseCarga("Fail", "Fallo Carga de Pronostico");
			}
		}

}
