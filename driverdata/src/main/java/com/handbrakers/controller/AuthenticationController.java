/**
 * 
 */
package com.handbrakers.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.handbrakers.config.authentication.SecurityParams;
import com.handbrakers.exception.ProcessingException;
import com.handbrakers.exception.ResourceNotFoundException;
import com.handbrakers.service.UserService;



/**
 * @author Jogireddy
 *
 */

@Controller
public class AuthenticationController {
	
	private static final Logger logger = Logger.getLogger(AuthenticationController.class); 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityParams securityParams;
	
	@RequestMapping(value = "/error")
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(HttpServletRequest request, HttpServletResponse response) {
		logger.info("inside class ::: "+ AuthenticationController.class + ", Inside Method :: handleResourceNotFoundException()");
		return new ResponseEntity<Object>("The page You are requested not found.", HttpStatus.BAD_REQUEST);
    }
	
	@RequestMapping(value = "/authSuccess")
	public ResponseEntity<Object> authenticationSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("User with Userid " + securityParams.getMyUsername() + " authentication Success at " + new Date().toString());
		logger.log(Level.INFO, "User with Userid " + securityParams.getMyUsername() + " authentication Success at " + new Date().toString());
		try{
			return new ResponseEntity<Object>(securityParams.getMyUsername() + "///" + securityParams.getMyRoles().toString(), HttpStatus.OK);
		} catch(Exception e){
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/authFailure", method = RequestMethod.GET)
	public ResponseEntity<Object> authenticationFailure(@RequestParam(name = "logout", required = false)boolean logout,
			@RequestParam(name = "message", required = false)String message, HttpServletRequest request, HttpServletResponse response){
		logger.info("authentication Failure at " + new Date().toString());
		if(logout){
			doLogout(request);
			return StaticResponseEntity.RESPONSE_ENTITY(message, HttpStatus.UNAUTHORIZED);
		}
		return StaticResponseEntity.RESPONSE_ENTITY(getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"), HttpStatus.UNAUTHORIZED);
	}
	
	private String getErrorMessage(HttpServletRequest request, String key) {
		logger.info("Error Message requested at " + new Date().toString());
		Exception exception = (Exception) request.getSession().getAttribute(key);
		if (exception instanceof BadCredentialsException) {
			return exception.getMessage();
		} else if (exception instanceof LockedException) {
			return exception.getMessage();
		} else if(exception instanceof AuthenticationException){
			return exception.getMessage();
		}else {
			return "Invalid username or Password! ";
		}
	}
	
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ResponseEntity<Object> accesssDenied(@AuthenticationPrincipal User activeUser) {
		String username = "";

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			
			//use this if custom/inbuilt userdetails is used and returned for authentication
			//UserDetails userDetail = (UserDetails) auth.getPrincipal();
			username = auth.getPrincipal().toString();
			username = username.split("///")[0];
		}
		logger.info("Access Denied for the User "+ username +" at "+ new Date().toString());
		return StaticResponseEntity.RESPONSE_ENTITY("Dear "+ username + ", You are not authorised to see this content.", HttpStatus.UNAUTHORIZED);

	}
		
		
	@RequestMapping(value = "/signout")
	public ResponseEntity<Object> logoutSuccess(@RequestParam(name = "failure", required = false)boolean failure, HttpServletRequest request, HttpSession session,HttpServletResponse response) throws Exception {
		
		try{				
			if(failure){
				logger.info("User"+ securityParams.getMyUsername() +" loggedout " +" at "+ new Date().toString() +", but logout timing is not updated.");
			}
			try{
				userService.updateUserLogoutTs();
			} catch(ProcessingException e){
				System.out.println("User"+ securityParams.getMyUsername() +" loggedout " +" at "+ new Date().toString() +", but logout timing is not updated.");
			}
			doLogout(request);
			return StaticResponseEntity.RESPONSE_ENTITY("Thanks for visiting us. Have a good day!", HttpStatus.OK);
		} catch(Exception e){			
			return StaticResponseEntity.RESPONSE_ENTITY("Something wrong with logout. Please try again.", HttpStatus.NOT_FOUND);
		}
	}
	
	private boolean doLogout(HttpServletRequest request){
		SecurityContextHolder.getContext().setAuthentication(null);
		SecurityContextHolder.clearContext();
		request.getSession().invalidate();
		return true;
	}
	
	@RequestMapping(value = "/verifyAccount", method = RequestMethod.GET)
	public ResponseEntity<Object> verifyAccount(@RequestParam(name = "token", required = true)String verificationToken){
		
		try{
			userService.verifyUserEmail(verificationToken);
			return new ResponseEntity<Object>(securityParams.getMyUsername() + "///" + securityParams.getMyRoles(), HttpStatus.OK);
		} catch(Exception e){
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
