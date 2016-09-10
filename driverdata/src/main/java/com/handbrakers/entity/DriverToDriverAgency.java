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
 * DriverToDriverAgency generated by hbm2java
 */
@Entity
@Table(name = "driver_to_driver_agency")
public class DriverToDriverAgency implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9136967288688307796L;
	private Long driverToDriverAgencyId;
	private Users users;
	private DriverAgencyIdentityProfile driverAgencyIdentityProfile;

	public DriverToDriverAgency() {
	}

	public DriverToDriverAgency(Users users,
			DriverAgencyIdentityProfile driverAgencyIdentityProfile) {
		this.users = users;
		this.driverAgencyIdentityProfile = driverAgencyIdentityProfile;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "driver_to_driver_agency_id", unique = true, nullable = false)
	public Long getDriverToDriverAgencyId() {
		return this.driverToDriverAgencyId;
	}

	public void setDriverToDriverAgencyId(Long driverToDriverAgencyId) {
		this.driverToDriverAgencyId = driverToDriverAgencyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_agency_identity_profile_id", nullable = false)
	public DriverAgencyIdentityProfile getDriverAgencyIdentityProfile() {
		return this.driverAgencyIdentityProfile;
	}

	public void setDriverAgencyIdentityProfile(
			DriverAgencyIdentityProfile driverAgencyIdentityProfile) {
		this.driverAgencyIdentityProfile = driverAgencyIdentityProfile;
	}

}
