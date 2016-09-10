/**
 * 
 */
package com.handbrakers.config;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 * @author JOGIREDDY
 *
 */

@Configuration
public class SpringHibernateConfiguration {
	
private static final Logger logger = LoggerFactory.getLogger(SpringHibernateConfiguration.class);
	
	
	@PostConstruct
	private void registerInitialAdmin() throws ServletException, IOException{
		logger.info("Inside the registerInitialAdmin() method....");	
	}
	
	@Bean(name = "dataSource")
	public DataSource getDataSource() throws Exception{
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl("jdbc:mysql://localhost:3306/driverpro");
		datasource.setUsername("root");
		datasource.setPassword("root");
		logger.info("inside getDataSource :: datasource construction is Successful at " + new Date());
		return datasource;
	}
	
	private Properties getHibernateProperties(){
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
    	properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    	logger.info("inside getHibernateProperties :: hibernate properties constructed at " + new Date());
		return properties;
	}
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory() throws Exception{
		LocalSessionFactoryBean sessionFactoryBuilder = new LocalSessionFactoryBean();
		sessionFactoryBuilder.setDataSource(getDataSource());
		sessionFactoryBuilder.setPackagesToScan("com.handbrakers.entity");
		sessionFactoryBuilder.setHibernateProperties(getHibernateProperties());
		sessionFactoryBuilder.afterPropertiesSet();
		logger.info("inside getSessionFactory :: Hibernate SessionFactory built successful at " + new Date());
		return sessionFactoryBuilder.getObject();
	}
	

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager() throws Exception{
		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager(getSessionFactory());
		logger.info("inside getTransactionManager :: Spring - Hibernate TransactionManager constructed successfully at " + new Date());
		return hibernateTransactionManager;
	}


}
