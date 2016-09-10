/**
 * 
 */
package com.handbrakers.config;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.handbrakers.config.authentication.SecurityParams;
import com.handbrakers.config.authentication.SecurityParamsImpl;

/**
 * @author JOGIREDDY
 *
 */

@Configuration
@ComponentScan({"com.handbrakers"})
@Import({SpringMailConfiguration.class,
		 SpringHibernateConfiguration.class,
		 SpringTilesConfiguration.class})
@EnableWebMvc
@EnableTransactionManagement
@EnableSpringConfigured//this don't have any use now. 
						//Thought of using @configurable to autowire beans
						//(as of now, have so many doubts on this feature).
public class SpringWebMvcConfiguration extends WebMvcConfigurerAdapter{
	
	private static final Logger logger = Logger.getLogger(SpringWebMvcConfiguration.class); 
	
	@Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {
		logger.info("Inside class :: "+ SpringWebMvcConfiguration.class +", Inside Method :: configureMessageConverters()");
		converters.add(converter());
        converters.add(new ResourceHttpMessageConverter());
    }
	
	@Bean
    MappingJackson2HttpMessageConverter converter() {
		logger.info("Inside class :: "+ SpringWebMvcConfiguration.class +", Inside Method :: converter()");
    	return new MappingJackson2HttpMessageConverter();
    }
	
	@Bean
	public MultipartResolver multipartResolver(){
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		return commonsMultipartResolver;
	}
	
	@Bean
	public BCryptPasswordEncoder encoder(){
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Bean
	public SecurityParams securityParams(){
		SecurityParams securityParams = new SecurityParamsImpl();
		return securityParams;
	}
	
	@Override
	 public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	  configurer.enable();
	 }
	
	@Bean
	public InternalResourceViewResolver viewResolver()
	{
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		//viewResolver.setOrder(1);
		return viewResolver;
	}
	
}
