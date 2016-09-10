package com.handbrakers.entity;

// Generated 9 Sep, 2016 2:56:29 PM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserCodes generated by hbm2java
 */
@Entity
@Table(name = "user_codes")
public class UserCodes implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1852503049496431620L;
	private Long userCodesId;
	private transient Users users;
	private String passwordResetCode;
	private String signupVerificationCode;
	private boolean passwordResetCodeUsed;
	private boolean signupVerificationCodeUsed;
	private Date initialCreatedTs;
	private Date passwordResetCodeCreatedTs;
	private Date signupVerificationCodeCreatedTs;
	private Date passwordResetCodeUpdatedTs;
	private Date signupVerificationCodeUpdatedTs;

	public UserCodes() {
	}

	public UserCodes(Users users, String signupVerificationCode,
			boolean passwordResetCodeUsed, boolean signupVerificationCodeUsed,
			Date initialCreatedTs, Date passwordResetCodeCreatedTs,
			Date signupVerificationCodeCreatedTs,
			Date passwordResetCodeUpdatedTs,
			Date signupVerificationCodeUpdatedTs) {
		this.users = users;
		this.signupVerificationCode = signupVerificationCode;
		this.passwordResetCodeUsed = passwordResetCodeUsed;
		this.signupVerificationCodeUsed = signupVerificationCodeUsed;
		this.initialCreatedTs = initialCreatedTs;
		this.passwordResetCodeCreatedTs = passwordResetCodeCreatedTs;
		this.signupVerificationCodeCreatedTs = signupVerificationCodeCreatedTs;
		this.passwordResetCodeUpdatedTs = passwordResetCodeUpdatedTs;
		this.signupVerificationCodeUpdatedTs = signupVerificationCodeUpdatedTs;
	}

	public UserCodes(Users users, String passwordResetCode,
			String signupVerificationCode, boolean passwordResetCodeUsed,
			boolean signupVerificationCodeUsed, Date initialCreatedTs,
			Date passwordResetCodeCreatedTs,
			Date signupVerificationCodeCreatedTs,
			Date passwordResetCodeUpdatedTs,
			Date signupVerificationCodeUpdatedTs) {
		this.users = users;
		this.passwordResetCode = passwordResetCode;
		this.signupVerificationCode = signupVerificationCode;
		this.passwordResetCodeUsed = passwordResetCodeUsed;
		this.signupVerificationCodeUsed = signupVerificationCodeUsed;
		this.initialCreatedTs = initialCreatedTs;
		this.passwordResetCodeCreatedTs = passwordResetCodeCreatedTs;
		this.signupVerificationCodeCreatedTs = signupVerificationCodeCreatedTs;
		this.passwordResetCodeUpdatedTs = passwordResetCodeUpdatedTs;
		this.signupVerificationCodeUpdatedTs = signupVerificationCodeUpdatedTs;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_codes_id", unique = true, nullable = false)
	public Long getUserCodesId() {
		return this.userCodesId;
	}

	public void setUserCodesId(Long userCodesId) {
		this.userCodesId = userCodesId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "password_reset_code", length = 500)
	public String getPasswordResetCode() {
		return this.passwordResetCode;
	}

	public void setPasswordResetCode(String passwordResetCode) {
		this.passwordResetCode = passwordResetCode;
	}

	@Column(name = "signup_verification_code", nullable = false, length = 500)
	public String getSignupVerificationCode() {
		return this.signupVerificationCode;
	}

	public void setSignupVerificationCode(String signupVerificationCode) {
		this.signupVerificationCode = signupVerificationCode;
	}

	@Column(name = "password_reset_code_used", nullable = false)
	public boolean isPasswordResetCodeUsed() {
		return this.passwordResetCodeUsed;
	}

	public void setPasswordResetCodeUsed(boolean passwordResetCodeUsed) {
		this.passwordResetCodeUsed = passwordResetCodeUsed;
	}

	@Column(name = "signup_verification_code_used", nullable = false)
	public boolean isSignupVerificationCodeUsed() {
		return this.signupVerificationCodeUsed;
	}

	public void setSignupVerificationCodeUsed(boolean signupVerificationCodeUsed) {
		this.signupVerificationCodeUsed = signupVerificationCodeUsed;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "initial_created_ts", nullable = false, length = 19)
	public Date getInitialCreatedTs() {
		return this.initialCreatedTs;
	}

	public void setInitialCreatedTs(Date initialCreatedTs) {
		this.initialCreatedTs = initialCreatedTs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "password_reset_code_created_ts", nullable = false, length = 19)
	public Date getPasswordResetCodeCreatedTs() {
		return this.passwordResetCodeCreatedTs;
	}

	public void setPasswordResetCodeCreatedTs(Date passwordResetCodeCreatedTs) {
		this.passwordResetCodeCreatedTs = passwordResetCodeCreatedTs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "signup_verification_code_created_ts", nullable = false, length = 19)
	public Date getSignupVerificationCodeCreatedTs() {
		return this.signupVerificationCodeCreatedTs;
	}

	public void setSignupVerificationCodeCreatedTs(
			Date signupVerificationCodeCreatedTs) {
		this.signupVerificationCodeCreatedTs = signupVerificationCodeCreatedTs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "password_reset_code_updated_ts", nullable = false, length = 19)
	public Date getPasswordResetCodeUpdatedTs() {
		return this.passwordResetCodeUpdatedTs;
	}

	public void setPasswordResetCodeUpdatedTs(Date passwordResetCodeUpdatedTs) {
		this.passwordResetCodeUpdatedTs = passwordResetCodeUpdatedTs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "signup_verification_code_updated_ts", nullable = false, length = 19)
	public Date getSignupVerificationCodeUpdatedTs() {
		return this.signupVerificationCodeUpdatedTs;
	}

	public void setSignupVerificationCodeUpdatedTs(
			Date signupVerificationCodeUpdatedTs) {
		this.signupVerificationCodeUpdatedTs = signupVerificationCodeUpdatedTs;
	}

}
