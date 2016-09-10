/**
 * 
 */
package com.handbrakers.service;

/**
 * @author Jogireddy
 *
 */
public interface MailService {
	
	/**
	 * Mail Service to send email to a single recipient.
	 * @param from
	 * @param to
	 * @param subject
	 * @param msg
	 * @return boolean to indicate mail sent or not.
	 */
	public boolean sendMail(String fromAddr, String toAddr, String subject, String bodyText);
	
	/**
	 * Mail Service to send email to a multiple recipients.
	 * @param to
	 * @param from
	 * @param subject
	 * @param msg
	 * @return boolean to indicate mail sent or not.
	 */
	public boolean sendMultipleMails(String[] toAddresses,String fromAddr, String subject, String bodyText);

}
