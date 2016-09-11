/**
 * 
 */
package com.handbrakers.config.authentication;

import org.springframework.stereotype.Component;

/**
 * @author Jogireddy
 *
 */
@Component("securityParams")
public interface SecurityParams {
	
	/*public long getMyId() throws Exception;*/
	
	public String getMyUsername() throws Exception;
	
	public String getMyIp() throws Exception;
	
	public String[] getMyRoles() throws Exception;
	
}
