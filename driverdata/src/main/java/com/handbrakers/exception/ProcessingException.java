/**
 * 
 */
package com.handbrakers.exception;

/**
 * @author Jogireddy
 *
 */
public class ProcessingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8818997406864871082L;

	/**
	 * 
	 */
	public ProcessingException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ProcessingException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ProcessingException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ProcessingException(Throwable cause) {
		super(cause);
	}
}
