/**
 * 
 */
package com.handbrakers.config.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.handbrakers.entity.UserToRole;
import com.handbrakers.entity.Users;
import com.handbrakers.exception.ProcessingException;
import com.handbrakers.service.UserService;

/**
 * @author Jogireddy
 *
 */

@Component(value = "authProvider")
public class CustomAuthProvider implements AuthenticationProvider{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	
	private boolean usernameNotMatched = false;
	
	//private HttpServletRequest request;
	
	//private HttpServletResponse response;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
				
		try {
			Users user = userService.getUserByUsername(authentication.getName());
			
			//boolean authFailure = false;
			
			//this check is not needed but kept for exceptional cases (as now, i too don't know what are those exceptional cases)
			if(!user.getUsername().equals(authentication.getName())){
				//authFailure = true;
				usernameNotMatched = true;
				throw new BadCredentialsException("Username and Password doesn't match!");
			}
			
			if(user.isAccountLocked()){
				throw new BadCredentialsException("Your account got locked! Please use forgot password for password recovery.");
			}
			
			if(!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())){
				//authFailure = true;
				int remainingAttemepts = getRemainingWrongAttempts(authentication.getName());
				if(remainingAttemepts == -2){
					throw new BadCredentialsException("Username and Password doesn't match!");
				}
				throw new BadCredentialsException("Username and Password doesn't match! Warning : You have " + remainingAttemepts + 
						"attempts remained. There after your account will be locked.");
			}
			
			/*if(user.isAccountExpired()){
				throw new BadCredentialsException("Your account is Expired. Please contact us!");
			}*/
			
			if(!user.isIsEmailVerified()){
				userService.sendEmailVerificationLink(user.getEmail(), user.getUserCodes().getSignupVerificationCode());
				throw new BadCredentialsException("Your email isn't verified yet. we have sent you an email with verification link, please check your email.");
			}
			/*if(user.isPasswordTemporary()){
				request.getRequestDispatcher("/chooseNewPassword").forward(request, response);
			}*/
			//this means authentication is successful
			try{
				userService.resetUserLoginAttempts(user.getUserId());
			} catch(ProcessingException e){
				System.out.println("Login attemts reset failed at " + new Date() + " for Username : "+ user.getUsername());
			}
			
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(user.getUsername() + "///" + user.getUserId(), 
					user.getPassword(), getAuthorities(user.getUserToRoles()));
			
			return authenticationToken;
		} catch (ProcessingException e) {
			throw new BadCredentialsException(e.getMessage());
		} /*catch(IOException | ServletException e){
			throw new BadCredentialsException("Something went wrong, Please try again later!");
		}
*/	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(Set<UserToRole> roles) {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		if(roles != null)
		{
			Iterator<UserToRole> iterator = roles.iterator();
			while(iterator.hasNext()){
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(iterator.next().getRoles().getRoleName());
				authorities.add(authority);
			}
		}
		return authorities;
	}
	
	private Integer getRemainingWrongAttempts(String username) throws ProcessingException{
		if(usernameNotMatched){
			return -2;
		}else{
			return userService.saveOrUpdateLoginCounter(username);
		}
	}

}
