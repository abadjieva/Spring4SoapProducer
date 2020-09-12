package com.concretepage;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class postProcessor implements BeanPostProcessor {
	private final Logger log = Logger.getLogger(getClass().getName());
public postProcessor() {
	// TODO Auto-generated constructor stub
}
@Override
	public Object postProcessAfterInitialization(Object arg0, String arg1)
			throws BeansException {
		// TODO Auto-generated method stub
	log.log(Level.INFO, "AfterInitialization bean: "+arg1);
//	if(arg0 instanceof Serializable){
//		
//	}
		return arg0;
	}
@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		// TODO Auto-generated method stub
		
		return arg0;
	}
}
