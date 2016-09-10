/**
 * 
 */
package com.handbrakers.config.authentication;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import com.handbrakers.entity.UserLoginFeed;
import com.handbrakers.exception.ProcessingException;
import com.handbrakers.service.UserService;

/**
 * @author Jogireddy
 *
 */

@Component(value = "successHandler")
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityParams securityParams;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		try{
			UserLoginFeed loginFeed = new UserLoginFeed();
			loginFeed.setIpAddress(securityParams.getMyIp());
			loginFeed.setLoggedinTs(new Date());
			if(userService.saveUserLoginData(loginFeed)){
				DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
		        if( defaultSavedRequest != null ) {
		            String requestUrl = defaultSavedRequest.getRequestURL() + "?" + defaultSavedRequest.getQueryString();
		            getRedirectStrategy().sendRedirect(request, response, requestUrl);
		        } else {
		            //super.onAuthenticationSuccess(request, response, authentication);
		            request.getRequestDispatcher("/authSuccess").forward(request, response);
		        }		
			} else{
				request.getRequestDispatcher("/authFailure?logout=true&message="+"There is a problem with login. Please try again Later").forward(request, response);
			}
		} catch(Exception e){
				if(e instanceof ProcessingException){
					request.getRequestDispatcher("/authFailure?logout=true&message="+e.getMessage()).forward(request, response);
				}
				request.getRequestDispatcher("/logout?logout=true&message="+"It seems like your session has Expired, Please login again").forward(request, response);
			}	
			
	}

}
