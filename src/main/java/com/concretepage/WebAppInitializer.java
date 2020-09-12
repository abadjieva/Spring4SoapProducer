package com.concretepage;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer  {
	private final Logger log = Logger.getLogger(getClass().getName());

	public void onStartup(ServletContext servletContext)
			throws ServletException {
		log.log(Level.INFO, "GESTARTET......");

		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(AppConfig.class);
		ctx.setServletContext(servletContext);
				
		log.log(Level.INFO, "GESTARTET1......");

		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(ctx);
		servlet.setTransformWsdlLocations(true);

		log.log(Level.INFO, "GESTARTET2......");

		Dynamic dynamic = servletContext.addServlet("dispatcher", servlet);
		dynamic.addMapping("/soapws/*");
		dynamic.setLoadOnStartup(1);
		log.log(Level.INFO, "GESTARTET3......");

	}
//	public static void main(String[] args){
//		SpringApplication.run(WebAppInitializer.class, args);
//		
//	}
}