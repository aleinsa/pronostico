package com.pronostico.controller;

import java.sql.SQLException;
import java.util.logging.Logger;
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

/**
 * @author Alejandro Insaurralde
 * 
 * Clase Rest Controller
 *
 */
@RestController
public class PronosticoController {
	
	Logger logger = Logger.getLogger(PronosticoController.class.getName());
	
	@Autowired
	public PronosticoCore pronosticoCore;
	
		/**
		 * Metodo que retorna el clima 
		 * @param dia
		 * @return ResponseClima
		 */
		@RequestMapping(value = "/dia/{dia}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseClima getClima(@PathVariable("dia") int dia) {
			try {
				return pronosticoCore.getClima(dia);
			}catch (EmptyResultDataAccessException e) {
				return new ResponseClima(dia, "No hay clima cargado para ese día");
			} catch (SQLException e) {
				logger.info("error en al consultar en BBDD: " + e.getMessage());
				return new ResponseClima(dia, "hubo un problema al cargar el clima");
			}catch (Exception e) {
				logger.info("error al consultar el clima: " + e.getMessage());
				return new ResponseClima(dia, "hubo un problema al cargar el clima");			}
		 }
	
	   	/**
	   	 * Metodo que retorna los periodos de sequia
	   	 * @return ResponsePeriodos
	   	 */
	   	@RequestMapping(value = "/periodos/sequia", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponsePeriodos getPeriodosSequia() {
			try{
				return pronosticoCore.getPeriodosSequia();
			}catch (SQLException e) {
				logger.info("error al consultar en BBDD periodos de sequia: " + e.getMessage());
				return new ResponsePeriodos(0, "hubo un problema al cargar el clima");
			}catch (Exception e) {
				logger.info("error al consultar  periodos de sequia: " + e.getMessage());
				return new ResponsePeriodos(0, "hubo un problema al cargar el clima");
			}
		}
	   	
		/**
		 * Metodo que retorna los periodos de lluvia
		 * @return
		 */
		@RequestMapping(value = "/periodos/lluvia", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponsePeriodos getPeriodosLluvia() {
			try{
				return pronosticoCore.getPeriodosLluvia();
			}catch (SQLException e) {
				logger.info("error al consultar en BBDD periodos de lluvia: " + e.getMessage());
				return new ResponsePeriodos(0, "hubo un problema al cargar el clima");
			}catch (Exception e) {
				logger.info("error al consultar  periodos de lluvia: " + e.getMessage());
				return new ResponsePeriodos(0, "hubo un problema al cargar el clima");
			}

		}

		/**
		 * Metodo que retorna los periodos de optimas condiciones
		 * @return
		 */
		@RequestMapping(value = "/periodos/optimo", method = RequestMethod.GET, produces =MediaType.APPLICATION_JSON_VALUE)
		public ResponsePeriodos getPeriodosOptimo() {
			 try{
				 return pronosticoCore.getPeriodosOptimo();
			 }catch (SQLException e) {
				 logger.info("error al consultar en BBDD periodos optimo: " + e.getMessage());
				 return new ResponsePeriodos(0, "hubo un problema al cargar el clima");
			}catch (Exception e) {
				logger.info("error al consultar periodos de optimo: " + e.getMessage());
				return new ResponsePeriodos(0, "hubo un problema al cargar el clima");
			}
			
		}
		
		/**
		 * Metodo que retorna los periodos que no llovio
		 * @return
		 */
		@RequestMapping(value = "/periodos/nolluvia", method = RequestMethod.GET, produces =MediaType.APPLICATION_JSON_VALUE)
		public ResponsePeriodos getPeriodosNoLluvia() {
			 try{
				 return pronosticoCore.getPeriodosNoLluvia();
			 }catch (SQLException e) {
				 logger.info("error al consultar en BBDD periodos de no lluvia: " + e.getMessage());
				 return new ResponsePeriodos(0, "hubo un problema al cargar el clima");
			}catch (Exception e) {
				logger.info("error al consultar periodos de no lluvia: " + e.getMessage());
				return new ResponsePeriodos(0, "hubo un problema al cargar el clima");
			}
		}
		
		/**
		 * Metodo que retorna el día mas lluvioso
		 * @return
		 */
		@RequestMapping(value = "/dia/maslluvioso", method = RequestMethod.GET, produces =MediaType.APPLICATION_JSON_VALUE)
		public ResponseClima getDiaMasLluvioso(){
			try{
				int dia= pronosticoCore.getDiaMasLluvioso();
			    return new ResponseClima(dia, "Es el día mas lluvioso");
			}catch (SQLException e) {
				logger.info("error al consultar en BBDD el dia mas lluvioso: " + e.getMessage());
				return new ResponseClima(0, "hubo un problema al cargar el clima");
			}catch (Exception e) {
				logger.info("error al consultar el dia mas lluvioso: " + e.getMessage());
				return new ResponseClima(0, "hubo un problema al cargar el clima");
			}
		}
		
		/**
		 * Metodo que realiza la carga inicial del clima
		 * @param anios
		 * @return ResponseCarga
		 */
		@RequestMapping(value = "/carga/{anios}", method = RequestMethod.PUT, produces =MediaType.APPLICATION_JSON_VALUE)
		public ResponseCarga inicarCarga(@PathVariable("anios") int anios){
			try{
				pronosticoCore.dropCreateTables();
				pronosticoCore.pronosticar(anios,1);
				return new ResponseCarga("OK", "CARGA OK");
			}catch (DataAccessException e) {
				logger.info("error en BBDD en la carga inicial: " + e.getMessage());
				return new ResponseCarga("Fail", e.getMessage());
			}catch (SQLException e) {
				logger.info("error en BBDD en la carga inicial  " + e.getMessage());
				return new ResponseCarga("Fail", e.getMessage());
			}catch (Exception e) {
				logger.info("error en la carga inicial: " + e.getMessage());
				return new ResponseCarga("Fail", e.getMessage());
			}
		}

}
