/**
 * 
 */
package com.handbrakers.config.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author JOGIREDDY
 *
 */
public class SecurityUser extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4124097710018484590L;

	public SecurityUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}

}
