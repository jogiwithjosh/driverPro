/**
 * 
 */
package com.handbrakers.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.handbrakers.entity.Users;
import com.handbrakers.exception.ProcessingException;
import com.handbrakers.service.UserService;
import com.handbrakers.util.RoleHelper;

/**
 * @author Jogireddy
 *
 */

@Controller
public class BaseController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String homePage(){
		return "index";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ResponseEntity<Object> register(){
		Users user = new Users("jogiwithjosh@gmail.com","jogiwithjosh","Test1234","8500859510",true,true,new Date(),new Date(),new Date(),new Date());
		try {
			if(userService.registerNewUser(user, RoleHelper.SITE_ADMIN.toString())){
				return StaticResponseEntity.RESPONSE_ENTITY("Registration Success", HttpStatus.OK);
			}
			return StaticResponseEntity.RESPONSE_ENTITY("Registration Failure", HttpStatus.NOT_FOUND);
			
		} catch (ProcessingException e) {
			return StaticResponseEntity.RESPONSE_ENTITY(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<Object> resisterUser(@ModelAttribute(value = "user") Users user,@RequestHeader(name = "X-Auth-Role")String role, BindingResult errors){
		
		Map<String, String> errorMap = new HashMap<String, String>();
		try{
			if(!userService.isEmailUnique(user.getEmail())){
				errorMap.put("email-error", "An account with this email is already registered with us. If you forgot the Password, reset by using forgot password.");
			}
			if(!userService.isPhoneNumberUnique(user.getPhoneNumber())){
				errorMap.put("primaryPhoneNumber-error", "This phone number is already associated with one of the existing accounts. Please use another one.");
			}
			if(!userService.isUsernameUnique(user.getUsername())){
				errorMap.put("username-error", "This username is already used. Please use another one.");
			}
		} catch(ProcessingException e){
			errorMap.put("error", e.getMessage());
		}
		
		if(!errorMap.isEmpty()){
			return StaticResponseEntity.RESPONSE_ENTITY(errorMap, HttpStatus.BAD_REQUEST);
		}
		try{
			if(userService.registerNewUser(user, role)){
				return StaticResponseEntity.RESPONSE_ENTITY("Dear " + user.getUserProfile().getFirstName() 
						+ " " + user.getUserProfile().getLastName() + ", Your account hasbeen created successfully. "
								+ "You are one more step away to use our services."
								+ "An email with account activation link has been sent to your email address. "
								+ "Please click on the link and activate your account." , HttpStatus.OK);
			} else{
				return StaticResponseEntity.RESPONSE_ENTITY("It's not possible to register your account now. Please try again later.", HttpStatus.NOT_FOUND);
			}
		} catch(ProcessingException e){
			return StaticResponseEntity.RESPONSE_ENTITY(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
