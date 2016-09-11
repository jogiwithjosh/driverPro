/**
 * 
 */
package com.handbrakers.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import com.handbrakers.config.CorsFilter;
import com.handbrakers.config.authentication.AuthFailureHandler;
import com.handbrakers.config.authentication.AuthSuccessHandler;
import com.handbrakers.config.authentication.CustomAuthProvider;
import com.handbrakers.config.authentication.SignoutSuccessHandler;

/**
 * @author JOGIREDDY
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomAuthProvider authProvider;
	
	@Autowired
	private AuthSuccessHandler successHandler;
	
	@Autowired
	private AuthFailureHandler failureHandler;
	
	@Autowired
	private SignoutSuccessHandler signoutSuccessHandler;
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder authBuilder)
			throws Exception {		
		authBuilder.authenticationProvider(authProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class).csrf().disable().exceptionHandling()
			.accessDeniedPage("/403")			
			.and().authorizeRequests()
			.antMatchers("/**").permitAll().anyRequest().authenticated()				
			.and()
			.formLogin()
				.usernameParameter("username").passwordParameter("password")
				.loginProcessingUrl("/authProcessing")
				.successHandler(successHandler)				
			.failureHandler(failureHandler)
				.failureUrl("/authFailure")				
			.and();
		
	}
	
	/*@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}*/

}
