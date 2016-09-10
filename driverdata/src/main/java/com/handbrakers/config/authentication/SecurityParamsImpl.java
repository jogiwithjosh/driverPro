/**
 * 
 */
package com.handbrakers.config.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * @author Jogireddy
 *
 */
public class SecurityParamsImpl implements SecurityParams{
	
	public long getMyId() throws Exception{
		try{
			return Long.parseLong(SecurityContextHolder.
					getContext().
					getAuthentication().
					getPrincipal().
					toString().
				split("///")[1]);
		} catch(Exception e){
			throw new Exception("You haven't Logged-in. Please login and Try again. ");
		}
	}
	
	public String getMyUsername() throws Exception{
		try{
			return SecurityContextHolder.
					getContext().
					getAuthentication().
					getPrincipal().
					toString().
					split("///")[0];
		} catch(Exception e){
			throw new Exception("You haven't Logged-in. Please login and Try again. ");
		}		
	}
	
	public String getMyIp() throws Exception{
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
			return details.getRemoteAddress();
		} catch(Exception e){
			throw new Exception("You haven't Logged-in. Please login and Try again. ");
		}	
	}
	
	public String[] getMyRoles() throws Exception{
		String[] roles = null;
		try{
			roles = new String[SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray().length];
			
			for(int i = 0; i < roles.length; i++){
				roles[i] = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[i].toString();
			}
			return roles;
		} catch(Exception e){
			throw new Exception("You haven't Logged-in. Please login and Try again. ");
		}
	}
}
