/**
 * 
 */
package com.handbrakers.repository;

import com.handbrakers.entity.UserLoginAttemptCount;
import com.handbrakers.entity.UserLoginFeed;
import com.handbrakers.entity.Users;
import com.handbrakers.exception.ProcessingException;

/**
 * @author Jogireddy
 *
 */
public interface UserRepository {
	
	public boolean saveUser(Users user) throws ProcessingException;
	
	public boolean updateUser(Users user) throws ProcessingException;
	
	public boolean saveOrUpdateUserLoginData(UserLoginFeed userLoginFeed) throws ProcessingException;
	
	public boolean saveOrUpdateUserLoginAttemptCount(UserLoginAttemptCount userLoginAttemptCount) throws ProcessingException;
	
	public boolean deleteUser(Users user) throws ProcessingException;
	
	public Users getUserById(Long userId) throws ProcessingException;
	
	public Users getUserByUsername(String username )throws ProcessingException;
	
	public Users getUserByEmail(String email) throws ProcessingException;
	
	public boolean isUsernameUnique(String username) throws ProcessingException;
	
	public boolean isEmailUnique(String email) throws ProcessingException;
	
	public boolean isPhoneNumberUnique(String phoneNumber) throws ProcessingException;
	
	public Users getUserByPasswordResetToken(String token) throws ProcessingException;
	
	public Users getUserByEmailVerificationToken(String token) throws ProcessingException;

}
