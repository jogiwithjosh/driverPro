/**
 * 
 *//*
package com.handbrakers.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

*//**
 * @author JOGIREDDY
 *
 *//*
public class SpringWebApplicationInitializer implements WebApplicationInitializer {
	
	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(SpringWebMvcConfiguration.class);
		
		servletContext.addListener(new ContextLoaderListener(context));
		servletContext.addListener(new HttpSessionEventPublisher());
		
		ServletRegistration.Dynamic dynamic = servletContext.addServlet("SpringDispatcher", new DispatcherServlet(context));
		dynamic.addMapping("/");
		dynamic.setLoadOnStartup(1);
		
	}

}
*/