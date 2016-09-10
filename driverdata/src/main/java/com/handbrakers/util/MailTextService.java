/**
 * 
 */
package com.handbrakers.util;

/**
 * @author Jogireddy
 *
 */
public class MailTextService {
	
	public static String textForEmailVerification(String emailVerificationToken){
		return emailVerificationToken;
	}
	
	public static String textForForgotPasswordInstructions(String passwordResetCode/*, String tempPassword*/){
		
		//https://cowroute.com/resetPassword?token=passwordRestCode
		return null;
	}
	
	public static String textForResetPasswordSuccess(){
		return null;
	}

}
