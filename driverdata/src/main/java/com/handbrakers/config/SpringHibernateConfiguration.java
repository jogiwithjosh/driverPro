/**
 * 
 */
package com.handbrakers.config;

import java.util.Date;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author JOGIREDDY
 *
 */

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.handbrakers.config" })
@PropertySource(value = { "classpath:application.properties" })
public class SpringHibernateConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(SpringHibernateConfiguration.class);
	
	@Autowired
    private Environment environment;
	
	
	/*@PostConstruct
	private void registerInitialAdmin() throws ServletException, IOException{
		logger.info("Inside the registerInitialAdmin() method....");	
	}*/
	
	@Bean
	public DataSource getDataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		logger.info("inside getDataSource :: datasource construction is Successful at " + new Date());
		return dataSource;
	}
	
	
	private Properties getHibernateProperties(){
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
	    properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
	    properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
    	logger.info("inside getHibernateProperties :: hibernate properties constructed at " + new Date());
		return properties;
	}
	
	@Autowired
	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean getSessionFactory(){
		LocalSessionFactoryBean sessionFactoryBuilder = new LocalSessionFactoryBean();
		sessionFactoryBuilder.setDataSource(getDataSource());
		sessionFactoryBuilder.setPackagesToScan("com.handbrakers.entity");
		sessionFactoryBuilder.setHibernateProperties(getHibernateProperties());
		//sessionFactoryBuilder.afterPropertiesSet();
		logger.info("inside getSessionFactory :: Hibernate SessionFactory built successful at " + new Date());
		return sessionFactoryBuilder;
	}
	

	@Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }


}
