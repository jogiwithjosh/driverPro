/**
 * 
 */
package com.handbrakers.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.handbrakers.config.authentication.SecurityParams;
import com.handbrakers.entity.Roles;
import com.handbrakers.entity.UserCodes;
import com.handbrakers.entity.UserLoginAttemptCount;
import com.handbrakers.entity.UserLoginFeed;
import com.handbrakers.entity.UserProfile;
import com.handbrakers.entity.UserToRole;
import com.handbrakers.entity.Users;
import com.handbrakers.exception.AuthenticationLostException;
import com.handbrakers.exception.ProcessingException;
import com.handbrakers.repository.UserRepository;
import com.handbrakers.util.MailTextService;
import com.handbrakers.util.RoleHelper;

/**
 * @author Jogireddy
 *
 */
@Transactional
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private SecurityParams securityParams;
	
	@Autowired
	private MailService mailService;
	
	private @Value("${email}") String fromMail;
	
	private final int maxWrongAttempts = 5;
	
	private Integer remainingWrongAttempts;

	@Override
	public boolean registerNewUser(Users user, String role) throws ProcessingException {
		try{
			
			//encode password
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			
			String signupVerificationCode = UUID.randomUUID().toString();
			//set usercodes - verification code
			UserCodes userCodes = new UserCodes();
			userCodes.setInitialCreatedTs(new Date());
			userCodes.setSignupVerificationCode(signupVerificationCode);
			userCodes.setSignupVerificationCodeCreatedTs(new Date());
			userCodes.setSignupVerificationCodeUsed(false);
			user.setUserCodes(userCodes);
			userCodes.setUsers(user);
			
			//set userroles
			Roles dbRole = new Roles();
			dbRole.setRoleId(RoleHelper.getRoleIdForUIRoleName(role));
			
			//set role and user
			UserToRole userToRole = new UserToRole();
			userToRole.setRoles(dbRole);
			userToRole.setUsers(user);
			user.getUserToRoles().add(userToRole);			
			
			return userRepository.saveUser(user);
		} catch(ProcessingException e){
			throw e;
		}
	}
	
	@Override
	public boolean changePassword(String oldPassword, String newPassword)
			throws ProcessingException, AuthenticationLostException {
		try{
			Users user = userRepository.getUserByUsername(securityParams.getMyUsername());
			if(user != null){
				if(!passwordEncoder.matches(oldPassword, user.getPassword())){
					throw new ProcessingException("Your current password isn't matched. Please check again.");
				}
				user.setPassword(passwordEncoder.encode(newPassword));
				return userRepository.updateUser(user);
			}else{
				throw new AuthenticationLostException("It seems like Your session is expired, Please login again.");
			}
		} catch(Exception e){
			if(e instanceof ProcessingException){
				throw new ProcessingException(e.getMessage());
			}
			throw new AuthenticationLostException("It seems like Your session is expired, Please login again.");
		}
	}
	
	@Override
	public boolean saveUserLoginData(UserLoginFeed newUserLoginFeed) throws ProcessingException, AuthenticationLostException {
		try{
			
			//get userby userId
			Users user = userRepository.getUserById(securityParams.getMyId());			
			
			//check whether if already loginfeed exists
			if(user.getUserLoginFeed() != null){
				user.getUserLoginFeed().setLoggedinTs(newUserLoginFeed.getLoggedinTs());
				user.getUserLoginFeed().setIpAddress(newUserLoginFeed.getIpAddress());
				return userRepository.updateUser(user);
			}else{
				newUserLoginFeed.setUsers(user);
				user.setUserLoginFeed(newUserLoginFeed);
				return userRepository.saveOrUpdateUserLoginData(newUserLoginFeed);
			}
			
		} catch(Exception e){
			if(e instanceof ProcessingException){
				throw new ProcessingException(e.getMessage());
			}
			throw new AuthenticationLostException("It seems like Your session is expired, Please login again.");
		}
	}

	@Override
	public Users getUserByUsername(String username) throws ProcessingException {
		try{
			Users user = userRepository.getUserByUsername(username);
			if(user == null){
				throw new ProcessingException("Username : '" + username + "' not found!");
			}
			return user;
		} catch(ProcessingException e){
			throw e;
		}
	}

	@Override
	public boolean updateUserLogoutTs()
			throws ProcessingException {
		try{
			Users user = userRepository.getUserById(securityParams.getMyId());
			if(user.getUserLoginFeed() != null){
				user.getUserLoginFeed().setLoggedoutTs(new Date());
				return userRepository.saveOrUpdateUserLoginData(user.getUserLoginFeed());
			}
			throw new ProcessingException("It seems like Your session is expired, Please login again.");
		} catch(Exception e){
			if(e instanceof ProcessingException){
				throw new ProcessingException(e.getMessage());
			}
			throw new ProcessingException("It seems like Your session is expired, Please login again.");
		}
	}

	@Override
	public Integer saveOrUpdateLoginCounter(String username)
			throws ProcessingException {
		
		try{			
			Users user = userRepository.getUserByUsername(username);
			if(user != null){
				if(user.getUserLoginAttemptCount() != null){
					if(user.getUserLoginAttemptCount().getAttemptCount() >= maxWrongAttempts-1){
						user.getUserLoginAttemptCount().setLastAttemptTs(new Date());
						user.setAccountLocked(true);
						if(userRepository.saveOrUpdateUserLoginAttemptCount(user.getUserLoginAttemptCount())){
							//userRepository.updateUser(user);
							remainingWrongAttempts =  0;
						}
					}else if(user.getUserLoginAttemptCount().getAttemptCount() < maxWrongAttempts){
						user.getUserLoginAttemptCount().setLastAttemptTs(new Date());
						user.getUserLoginAttemptCount().setAttemptCount(user.getUserLoginAttemptCount().getAttemptCount() + 1);
						if(userRepository.saveOrUpdateUserLoginAttemptCount(user.getUserLoginAttemptCount())){
							remainingWrongAttempts =  (maxWrongAttempts - user.getUserLoginAttemptCount().getAttemptCount());
						}
					} 
				} else{
					UserLoginAttemptCount newLoginAttemptCount = new UserLoginAttemptCount();
					newLoginAttemptCount.setAttemptCount(1);
					newLoginAttemptCount.setLastAttemptTs(new Date());
					newLoginAttemptCount.setUsers(user);
					if(userRepository.saveOrUpdateUserLoginAttemptCount(newLoginAttemptCount)){
						remainingWrongAttempts = maxWrongAttempts - 1;
					}				
				}
			} else{
				throw new UsernameNotFoundException("User not found with the Username : " + username);
			}
			return remainingWrongAttempts;
		} catch(Exception e){
			if(e instanceof ProcessingException){
				throw new ProcessingException(e.getMessage());
			}
			throw new ProcessingException("It seems like Your session is expired, Please login again.");
		}
	}



	@Override
	public boolean resetUserLoginAttempts(Long userId)
			throws ProcessingException {
		try{
			Users user = userRepository.getUserById(userId);
			if(user.getUserLoginAttemptCount() != null ){
				user.getUserLoginAttemptCount().setAttemptCount(0);
				user.getUserLoginAttemptCount().setLastAttemptTs(new Date());
				user.setAccountLocked(false);
				return userRepository.updateUser(user);
			} else{
				return true;
			}
		} catch(ProcessingException e){
			throw e;
		}
	}

	@Override
	public boolean sendResetPasswordInstuctions(String email)
			throws ProcessingException {
		try{
			String uuid = UUID.randomUUID().toString();
			Users user = userRepository.getUserByEmail(email);
			
			if(!user.isIsEmailVerified()){
				this.sendEmailVerificationLink(email, user.getUserCodes().getSignupVerificationCode());
				throw new ProcessingException("Your email isn't verified yet. we have sent you an email with verification link, please check your email.");
			}
			if(user.getUserCodes().getPasswordResetCode() != null){
				
				if(user.getUserCodes().isPasswordResetCodeUsed()){
					user.getUserCodes().setPasswordResetCode(uuid);
					user.getUserCodes().setPasswordResetCodeUpdatedTs(new Date());
				}else{
					user.getUserCodes().setPasswordResetCodeUpdatedTs(new Date());
				}
				
			} else{				
				user.getUserCodes().setPasswordResetCode(uuid);
				user.getUserCodes().setPasswordResetCodeCreatedTs(new Date());
				user.getUserCodes().setPasswordResetCodeUsed(false);
			}
			
			mailService.sendMail(fromMail, email, "CowRoure :: Reset Password Instructions", MailTextService.textForForgotPasswordInstructions(user.getUserCodes().getPasswordResetCode()));
			return userRepository.updateUser(user);
		} catch(ProcessingException e){
			throw e;
		}
	}
	
	
	@Override
	public void sendEmailVerificationLink(String email, String token)
			throws ProcessingException {
		mailService.sendMail(fromMail, email, "CowRoute :: Please verify Your Email for account activation", MailTextService.textForEmailVerification(token));		
	}

	@Override
	public Users getUserByEmail(String email) throws ProcessingException {
		try{
			return userRepository.getUserByEmail(email);
		} catch(ProcessingException e){
			throw e;
		}
	}

	@Override
	public boolean isUsernameUnique(String username)
			throws ProcessingException {
		try{
			return userRepository.isUsernameUnique(username);
		} catch(ProcessingException e){
			throw e;
		}
	}

	@Override
	public boolean isEmailUnique(String email) throws ProcessingException
			 {
		try{
			return userRepository.isEmailUnique(email);
		} catch(ProcessingException e){
			throw e;
		}
	}

	@Override
	public boolean isPhoneNumberUnique(String phoneNumber)
			throws ProcessingException {
		try{
			return userRepository.isPhoneNumberUnique(phoneNumber);
		} catch(ProcessingException e){
			throw e;
		}
	}

	@Override
	public String resetPassword(String token, String newPassword)
			throws ProcessingException {
		try{
			Users user = userRepository.getUserByPasswordResetToken(token);
			if(user != null){
				user.setPassword(passwordEncoder.encode(newPassword));
				user.setUpdatedTs(new Date());
				user.getUserCodes().setPasswordResetCodeUsed(true);
				user.setAccountLocked(false);
				if(userRepository.updateUser(user)){
					mailService.sendMail("", user.getEmail(), "", MailTextService.textForResetPasswordSuccess());
					return "Your Password reset was successfully. Now you can login with new Password";
				}else{
					throw new ProcessingException("Something wrong, Please try again later.");
				}
			}else{
				return "Your token is not valid. Please use the valid token or request for it by using forgot password."; 
			}
		} catch(ProcessingException e){
			throw e;
		}
	}

	@Override
	public boolean verifyUserEmail(String verificationToken)
			throws ProcessingException {
		try{
			Users user = userRepository.getUserByEmailVerificationToken(verificationToken);		
			if(user != null){
				if(user.isIsEmailVerified()){
					throw new ProcessingException("Your email has already been verified. You can login into your account. "
							+ "If you are facing any issues with the login, Please contact us!");
				}
				user.setEmailVerifiedTs(new Date());
				user.setIsEmailVerified(true);
				user.getUserCodes().setSignupVerificationCodeUsed(true);
			} else{
				throw new ProcessingException("It seems like verification link has broken or invalid. "
						+ "Please verify your email again or use forgot password to generate another link.");
			}			
			return userRepository.updateUser(user);
		} catch(ProcessingException e){
			throw e;
		}
	}

	@Override
	public UserProfile getUserProfile() throws ProcessingException, AuthenticationLostException {
		try{
			return userRepository.getUserById(securityParams.getMyId()).getUserProfile();
		} catch(Exception e){
			if(e instanceof ProcessingException){
				throw new ProcessingException(e.getMessage());
			}
			throw new AuthenticationLostException(e.getMessage());
		}
	}

	@Override
	public boolean updateUserProfile(UserProfile userProfile)
			throws ProcessingException, AuthenticationLostException {
		try{
			Users user = userRepository.getUserById(securityParams.getMyId());
			user.setUserProfile(userProfile);
			return userRepository.updateUser(user);
		} catch(Exception e){
			if(e instanceof ProcessingException){
				throw new ProcessingException(e.getMessage());
			}
			throw new AuthenticationLostException(e.getMessage());
		}
	}


}
