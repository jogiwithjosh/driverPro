/**
 * 
 */
package com.handbrakers.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.handbrakers.entity.UserLoginFeed;
import com.handbrakers.entity.UserProfile;
import com.handbrakers.entity.Users;
import com.handbrakers.exception.AuthenticationLostException;
import com.handbrakers.exception.ProcessingException;

/**
 * @author Jogireddy
 *
 */

@Service
@Transactional
public interface UserService {
	
	public boolean registerNewUser(Users user, String role) throws ProcessingException;
	
	public Users getUserByUsername(String username )throws ProcessingException;
	
	public Users getUserByEmail(String email) throws ProcessingException;
	
	public boolean isUsernameUnique(String username) throws ProcessingException;
	
	public boolean isEmailUnique(String email) throws ProcessingException;
	
	public boolean isPhoneNumberUnique(String phoneNumber) throws ProcessingException;
	
	public boolean changePassword(String oldPassword, String newPassword) throws ProcessingException, AuthenticationLostException;
	
	public boolean saveUserLoginData(UserLoginFeed userLoginFeed) throws ProcessingException, AuthenticationLostException;
	
	public boolean updateUserLogoutTs() throws ProcessingException;
	 
	public Integer saveOrUpdateLoginCounter(String username) throws ProcessingException;
	
	public boolean resetUserLoginAttempts(Long userId) throws ProcessingException;
	
	public boolean sendResetPasswordInstuctions(String email) throws ProcessingException;
	
	public void sendEmailVerificationLink(String email, String token) throws ProcessingException;
	
	public boolean verifyUserEmail(String verificationToken) throws ProcessingException;
	
	public String resetPassword(String token, String newPassword) throws ProcessingException;
	
	public UserProfile getUserProfile() throws ProcessingException, AuthenticationLostException;
	
	public boolean updateUserProfile(UserProfile userProfile) throws ProcessingException, AuthenticationLostException;
}
