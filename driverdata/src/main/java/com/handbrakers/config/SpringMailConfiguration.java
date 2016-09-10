/**
 * 
 */
package com.handbrakers.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author JOGIREDDY
 *
 */
@Configuration
@PropertySource(value = { "classpath:mail.properties" })
public class SpringMailConfiguration {
	
	@Autowired
	private Environment env;	
	
	@Bean
	public JavaMailSender javaMailSenderImpl() {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setHost(env.getProperty("toHost"));
		mailSenderImpl.setPort(env.getProperty("port", Integer.class));
		mailSenderImpl.setProtocol(env.getProperty("protocol"));
		mailSenderImpl.setUsername(env.getProperty("email"));
		mailSenderImpl.setPassword(env.getProperty("password"));

		Properties javaMailProps = new Properties();
		javaMailProps.put("mail.smtp.auth", true);
		javaMailProps.put("mail.smtp.starttls.enable", true);

		mailSenderImpl.setJavaMailProperties(javaMailProps);
		JavaMailSender mailSender = mailSenderImpl;
		return mailSender;
	}

}
