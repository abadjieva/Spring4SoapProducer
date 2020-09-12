package com.concretepage;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;





import com.concretepage.dao.PartnerfirmaDAO;
import com.concretepage.dao.PartnerfirmaDAOImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.concretepage"})
@PropertySource(value = { "classpath:application.properties", "classpath:persistence-jndi.properties" })
public class HibernateConfiguration {

	private final Logger log= Logger.getLogger(getClass().getName());
    
	@Autowired
    private Environment environment;

    @Bean(name="SESSIONFactory")
    //@Bean
    public LocalSessionFactoryBean sessionFactory() throws NamingException{
    	
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.concretepage.model" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        log.log(Level.INFO, "sessionFactory......");   
        return sessionFactory;
     }
	

  @Bean
    public DataSource dataSource() {
	  	//ComboPooledDataSource cd = new ComboPooledDataSource();
	  
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        log.log(Level.INFO, "DataSource......");
        return dataSource;
    }
    
//    @Bean
//    public DataSource dataSource() throws NamingException {
//        return (DataSource) new InitialContext().lookup(environment.getProperty("jdbc.bind"));
//    }
    
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
       return properties;        
    }
    
	@Bean
    @Autowired
    public HibernateTransactionManager transactionManager() throws Exception {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(sessionFactory().getObject());
       return txManager;
    }
	

	
}


