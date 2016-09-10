/**
 * 
 */
package com.handbrakers.exception;

/**
 * @author Jogireddy
 *
 */
public class NonUniqueException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4819412159454903569L;

	/**
	 * 
	 */
	public NonUniqueException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NonUniqueException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NonUniqueException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NonUniqueException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NonUniqueException(Throwable cause) {
		super(cause);
	}
	

}
