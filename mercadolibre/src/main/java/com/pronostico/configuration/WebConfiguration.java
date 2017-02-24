package com.pronostico.configuration;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import com.pronostico.core.PronosticoCore;
import com.pronostico.dao.PronosticoDAO;
import com.pronostico.dao.impl.PronosticoDAOImpl;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages= "com.pronostico")
public class WebConfiguration extends WebMvcConfigurerAdapter{
	
	   @Bean
	    public ViewResolver viewResolver() {
	        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setViewClass(JstlView.class);
	        viewResolver.setPrefix("/WEB-INF/");
	        viewResolver.setSuffix(".jsp");
	        return viewResolver;
	    }
	
        @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {}

       	@Bean
    	public DataSource dataSource() throws NamingException {
    		JndiTemplate jndiTemplate = new JndiTemplate();
    		DataSource dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DataSource");
    		return dataSource;
    	}
       	
    	@Bean
    	public PronosticoDAO pronosticoDAO() throws NamingException {
    		return new PronosticoDAOImpl(dataSource());
    	}
    	
    	@Bean
    	public PronosticoCore pronosticoCore() throws NamingException {
    		return new PronosticoCore();
    	}
    	
//    	@Bean
//    	public List<Planeta> planetas() throws NamingException {
//    		return new PronosticoDAOImpl(dataSource()).getPlanetas();
//    	}
}
