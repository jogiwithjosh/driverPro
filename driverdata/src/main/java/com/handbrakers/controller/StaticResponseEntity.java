/**
 * 
 */
package com.handbrakers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Jogireddy
 *
 */
public class StaticResponseEntity {
	
	public static ResponseEntity<Object> RESPONSE_ENTITY(Object object,HttpStatus status){
		return new ResponseEntity<Object>(object, status);
	}

}
