/**
 * 
 */
package com.handbrakers.config.authentication;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Jogireddy
 *
 */
public class SessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("==== Session is created ====");
        event.getSession().setMaxInactiveInterval(5*60);		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("==== Session is destroyed after====");
		System.out.println(se.getSession().getCreationTime());
		System.out.println(se.getSession().getLastAccessedTime());
		se.getSession().invalidate();
	}
}
