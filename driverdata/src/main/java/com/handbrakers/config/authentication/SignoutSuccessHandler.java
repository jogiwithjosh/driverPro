/**
 * 
 */
package com.handbrakers.config.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.handbrakers.exception.ProcessingException;
import com.handbrakers.service.UserService;

/**
 * @author Jogireddy
 *
 */
@Component("signoutSuccessHandler")
public class SignoutSuccessHandler implements LogoutSuccessHandler{
	
	@Autowired
	private UserService userService;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		try{
			userService.updateUserLogoutTs();
		} catch(Exception e){
			if(e instanceof ProcessingException){
				//set failure false to know that logout timestamp is not saved because of some reasons
				request.getRequestDispatcher("/logoutSuccess?failure=false").forward(request, response);
			}else{
				request.getRequestDispatcher("/logoutSuccess").forward(request, response);
			}
		}
		
	}

}
