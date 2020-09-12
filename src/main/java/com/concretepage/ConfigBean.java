package com.concretepage;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.concretepage") 
@PropertySource(value = { "classpath:application.properties" })
public class ConfigBean {
	
//@Value("${jdbc.password}")
public String prop;

@Autowired
private Environment environment;

@Bean(name="Find")
public TestObj find() throws NamingException {
	TestObj obj = new TestObj();
	prop= environment.getRequiredProperty("jdbc.username"); //gibt Fehler zurück wenn kein Prop
	//prop= environment.getProperty("jdbc.usernam"); //null
	obj.setProp(prop);
	return obj;
	
}

}
