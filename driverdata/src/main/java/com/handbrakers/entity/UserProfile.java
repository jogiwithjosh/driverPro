package com.handbrakers.entity;

// Generated 9 Sep, 2016 2:56:29 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserProfile generated by hbm2java
 */
@Entity
@Table(name = "user_profile")
public class UserProfile implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2684399973919180798L;
	private Long userProfileId;
	private transient Users users;
	private String firstName;
	private String lastName;
	private String alternatePhoneNumber;
	private String gender;

	public UserProfile() {
	}

	public UserProfile(Users users, String firstName, String lastName,
			String alternatePhoneNumber, String gender) {
		this.users = users;
		this.firstName = firstName;
		this.lastName = lastName;
		this.alternatePhoneNumber = alternatePhoneNumber;
		this.gender = gender;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_profile_id", unique = true, nullable = false)
	public Long getUserProfileId() {
		return this.userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "first_name", nullable = false)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", nullable = false)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "alternate_phone_number", nullable = false, length = 12)
	public String getAlternatePhoneNumber() {
		return this.alternatePhoneNumber;
	}

	public void setAlternatePhoneNumber(String alternatePhoneNumber) {
		this.alternatePhoneNumber = alternatePhoneNumber;
	}

	@Column(name = "gender", nullable = false, length = 5)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}