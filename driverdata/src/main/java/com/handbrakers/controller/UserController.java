/**
 * 
 */
package com.handbrakers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.handbrakers.entity.UserProfile;
import com.handbrakers.exception.AuthenticationLostException;
import com.handbrakers.exception.ProcessingException;
import com.handbrakers.service.UserService;

/**
 * @author Jogireddy
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ResponseEntity<Object> getProfile(){
		try{
			return StaticResponseEntity.RESPONSE_ENTITY(userService.getUserProfile(), HttpStatus.OK);
		} catch(ProcessingException e){
			return StaticResponseEntity.RESPONSE_ENTITY(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch(AuthenticationLostException e){
			return StaticResponseEntity.RESPONSE_ENTITY(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value = "/updateProfile", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateProfile(@ModelAttribute("profile")UserProfile userProfile){
		try{
			if(userService.updateUserProfile(userProfile)){
				return StaticResponseEntity.RESPONSE_ENTITY("Your Profile has been updated successfully.", HttpStatus.OK);
			} else{
				return StaticResponseEntity.RESPONSE_ENTITY("It's not possible to register your account now. Please try again later.", HttpStatus.NOT_FOUND);
			}
		} catch(ProcessingException e){
			return StaticResponseEntity.RESPONSE_ENTITY(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch(AuthenticationLostException e){
			return StaticResponseEntity.RESPONSE_ENTITY(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value = "/send-reset-password-steps", method = RequestMethod.POST)
	public ResponseEntity<Object> forgotPasswordInstructions(@RequestParam(name = "email", required = true)String email){
		try{
			if(userService.sendResetPasswordInstuctions(email)){
				return StaticResponseEntity.RESPONSE_ENTITY("If you supplied a correct email address then an email should have been sent to you. \n It contains easy instructions to confirm and complete this password change. If you continue to have difficulty, please contact us.", HttpStatus.OK);
			} else{
				return StaticResponseEntity.RESPONSE_ENTITY("Something wrong, Please try later !", HttpStatus.NOT_FOUND);
			}
		} catch(ProcessingException e){
			return StaticResponseEntity.RESPONSE_ENTITY(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ResponseEntity<Object> resetPassword(@RequestParam(name = "token", required = true)String token, 
			@RequestParam(name = "newPassword", required = true)String newPassword, 
			@RequestParam(name = "confirmPassword")String confirmPassword){
		try{
			if(!newPassword.equals(confirmPassword)){
				return StaticResponseEntity.RESPONSE_ENTITY("Passwords aren't matched.", HttpStatus.NOT_FOUND);
			}else{
				return StaticResponseEntity.RESPONSE_ENTITY(userService.resetPassword(token, newPassword), HttpStatus.OK);
			}
		} catch(ProcessingException e){
			return StaticResponseEntity.RESPONSE_ENTITY(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public ResponseEntity<Object> changePassword(@RequestParam(name = "oldPassword")String oldPassword, 
			@RequestParam(name = "newPassword")String newPassword, 
			@RequestParam(name = "confirmPassword")String confirmPassword){
		
		try{
			if(!newPassword.equals(confirmPassword)){
				return StaticResponseEntity.RESPONSE_ENTITY("Passwords aren't matched.", HttpStatus.NOT_FOUND);
			}else{
				return StaticResponseEntity.RESPONSE_ENTITY(userService.changePassword(oldPassword, newPassword), HttpStatus.OK);
			}
		} catch(ProcessingException e){
			return StaticResponseEntity.RESPONSE_ENTITY(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch(AuthenticationLostException e){
			return StaticResponseEntity.RESPONSE_ENTITY(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}


}
