package com.pronostico.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author Alejandro Insaurralde
 * 
 * Clase Configuration que reemplaza el web.xml
 *
 */
public class SpringMvcInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		
		  return new Class[] { WebConfiguration.class };

	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		  return new String[] {"/"};
	}

}
