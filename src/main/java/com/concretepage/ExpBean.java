package com.concretepage;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Scope(value="singleton")
public class ExpBean {
	private final Logger log= Logger.getLogger(getClass().getName());
	
	Integer intTemp= 1952;
	String temp="";
	
	public String getTemp() {
		return temp;
	}
	
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public Integer getIntTemp() {
	
		return intTemp;
	}
	
	
	public ExpBean() {
		// TODO Auto-generated constructor stub
		log.log(Level.INFO, "Generieren ExpBean......");
	}


}
