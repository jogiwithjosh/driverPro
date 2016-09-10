/**
 * 
 */
package com.handbrakers.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jogireddy
 *
 */
public abstract class AbstractDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	 * This method is used to get Hibernate currentSession from Hibernate sessionFactory
	 */
	protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
	
}
