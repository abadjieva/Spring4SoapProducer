package com.concretepage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchemaCollection;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;
import org.springframework.ws.server.EndpointInterceptor;

@Configuration
@EnableWs
@ComponentScan("com.concretepage") 
//@ComponentScan
public class AppConfig extends WsConfigurerAdapter {
	private final Logger log= Logger.getLogger(getClass().getName());
	
	// http://localhost:8080/spring4soap-1/soapws/students.wsdl
	@Override
    public void addInterceptors(List<EndpointInterceptor> interceptors){
       try{
		CustomValidationInterceptor validatingInterceptor = new CustomValidationInterceptor();
        validatingInterceptor.setValidateRequest(true);
        validatingInterceptor.setValidateResponse(true);
        //validatingInterceptor.setXsdSchema(studentsSchema());
        log.log(Level.INFO, "addInterceptors......");
        validatingInterceptor.setXsdSchemaCollection(getSchemaCollection());
        interceptors.add(validatingInterceptor);
       }
       catch(Exception e){
    	   System.out.println("IVA");
    	   log.log(Level.SEVERE, "ValidatingInterceptor nicht zugefügt......");
       }
    }

	
	// Name WSDL students.wsdl
	@Bean(name = "students")
	public DefaultWsdl11Definition defaultWsdl11Definition() throws IOException{
		log.log(Level.INFO, "im DefaultWsdl11Definition......");
		
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("StudentsPort");
		wsdl11Definition.setLocationUri("/soapws");
		wsdl11Definition.setTargetNamespace("http://concretepage.com/soap");
		//wsdl11Definition.setSchema(studentsSchema());
		log.log(Level.INFO, " setSchemaCollection......");
		
		wsdl11Definition.setSchemaCollection(getSchemaCollection());
		log.log(Level.INFO, " bei Ausgang aus DefaultWsdl11Definition......");
		wsdl11Definition.setServiceName("students");
		
		return wsdl11Definition;
	}
//	@Bean
//	public XsdSchema studentsSchema() {
//		XsdSchema schema = new SimpleXsdSchema(new ClassPathResource("Students.xsd"));
//		return schema;
//	}
	
	XsdSchemaCollection getSchemaCollection() throws IOException{
		try{
		CommonsXsdSchemaCollection collection = new CommonsXsdSchemaCollection();
		log.log(Level.INFO, "SchemaCollection......");
		collection.setInline(true);
		collection.setXsds(new Resource[] {
				new ClassPathResource("xsd/Students.xsd")
		});
		collection.afterPropertiesSet();
		
		
		return collection;}
		catch(Exception e){
			
			log.log(Level.INFO, e.getLocalizedMessage());
			throw e;
		}
	}
	
	

	//@Bean
	//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	//@Scope(value="prototype")
	//public ExpBean getExpBean(){
	//	return new ExpBean();
	//}
	
	
	
}
