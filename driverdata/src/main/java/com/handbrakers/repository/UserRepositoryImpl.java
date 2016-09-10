/**
 * 
 */
package com.handbrakers.repository;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.handbrakers.entity.UserLoginAttemptCount;
import com.handbrakers.entity.UserLoginFeed;
import com.handbrakers.entity.Users;
import com.handbrakers.exception.ProcessingException;

/**
 * @author Jogireddy
 *
 */
@Repository
public class UserRepositoryImpl extends AbstractDAO implements UserRepository{
	
	private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
	
	
	@Override
	public boolean saveUser(Users user) throws ProcessingException {
		try{
			getSession().save(user);
			return true;
		} catch(HibernateException e){
			logger.info("inside saveUser :: Exception occured at " + new Date(), e);
			throw new ProcessingException("Something went wrong, Please try again later!");
		}
	}

	@Override
	public boolean updateUser(Users user) throws ProcessingException {
		try{
			getSession().saveOrUpdate(user);
			return true;
		} catch(HibernateException e){
			throw new ProcessingException("Something went wrong, Please try again later!");
		}
	}
	
	@Override
	public boolean saveOrUpdateUserLoginData(UserLoginFeed userLoginFeed) throws ProcessingException {
		try{
			getSession().saveOrUpdate(userLoginFeed);
			return true;
		} catch(HibernateException e){
			throw new ProcessingException("Something went wrong, Please try again later!");
		}
	}
	
	@Override
	public boolean saveOrUpdateUserLoginAttemptCount(UserLoginAttemptCount userLoginAttemptCount) throws ProcessingException {
		try{
			getSession().saveOrUpdate(userLoginAttemptCount);
			return true;
		} catch(HibernateException e){
			throw new ProcessingException("Something went wrong, Please try again later!");
		}
	}

	@Override
	public boolean deleteUser(Users user) throws ProcessingException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Users getUserById(Long userId) throws ProcessingException {
		try{
			return getSession().get(Users.class, userId);
		} catch(HibernateException e){
			throw new ProcessingException("Something went wrong, Please try again later!");
		}
	}


	
	@Override
	public Users getUserByUsername(String username) throws ProcessingException {
		try{
			return (Users) getSession().
					createCriteria(Users.class).
					add(Restrictions.eq("username", username)).
					uniqueResult();
		} catch(HibernateException e){
			throw new ProcessingException("Something went wrong, Please try again later!");
		}
	}
	
	@Override
	public Users getUserByEmail(String email) throws ProcessingException {
		try{
			return (Users) getSession().createCriteria(Users.class).add(Restrictions.eq("email", email)).uniqueResult();
		} catch(HibernateException e){
			throw new ProcessingException("Something went wrong, Please try again later!");
		}
	}

	@Override
	public boolean isUsernameUnique(String username)
			throws ProcessingException {
		try{
			
			Integer count = (Integer) getSession().
					createCriteria(Users.class).
					add(Restrictions.eq("username", username)).
					setProjection(Projections.rowCount()).
					uniqueResult();
			if(count == 0){
				return true;
			}
			return false;
			
		} catch(HibernateException e){
			logger.info("inside isUsernameUnique :: Exception occured at " + new Date(), e);
			throw new ProcessingException("Something went wrong, Please try again later!");
		}
	}

	@Override
	public boolean isEmailUnique(String email) throws ProcessingException {
		try{
			Integer count = (Integer) getSession().
					createCriteria(Users.class).
					add(Restrictions.eq("email", email)).
					setProjection(Projections.rowCount()).
					uniqueResult();
			if(count == 0){
				return true;
			}
			return false;
			
		} catch(HibernateException e){
			logger.info("inside isEmailUnique :: Exception occured at " + new Date(), e);
			throw new ProcessingException("Something went wrong, Please try again later!");
		}
	}

	@Override
	public boolean isPhoneNumberUnique(String phoneNumber)
			throws ProcessingException {
		try{
			Integer count = (Integer) getSession().
					createCriteria(Users.class).
					add(Restrictions.eq("primaryPhoneNumber", phoneNumber)).
					setProjection(Projections.rowCount()).
					uniqueResult();
			if(count == 0){
				return true;
			}
			return false;
		} catch(HibernateException e){
			logger.info("inside isPhoneNumberUnique :: Exception occured at " + new Date(), e);
			throw new ProcessingException("Something went wrong, Please try again later!");
		}
	}

	@Override
	public Users getUserByPasswordResetToken(String token)
			throws ProcessingException {
		try{
			return (Users) getSession().createCriteria(Users.class).add(Restrictions.eq("userCodes.passwordResetCode", token)).uniqueResult();
		} catch(HibernateException e){
			throw new ProcessingException("Something went wrong, Please try again later!");
		}
	}

	@Override
	public Users getUserByEmailVerificationToken(String token)
			throws ProcessingException {
		try{
			return (Users) getSession().createCriteria(Users.class)
					.add(Restrictions.eq("userCodes.signupVerificationCode", token))
					.createAlias("userCodes", "userCodes")
					.uniqueResult();
		} catch(HibernateException e){
			throw new ProcessingException("Something went wrong, Please try again later!");
		}
	}
}
