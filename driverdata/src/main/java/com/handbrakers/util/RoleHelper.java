/**
 * 
 */
package com.handbrakers.util;

/**
 * @author Jogireddy
 *
 */
public enum RoleHelper {
	
	//enum_name(dbRoleId, uiRole, dbRole);
	
	DRIVER(1,"DRIVER","ROLE_DRIVER"),
	CUSTOMER(2,"CUSTOMER","ROLE_CUSTOMER"),
	DRIVER_AGENCY(3,"DRIVER_AGENCY","ROLE_DRIVER_AGENCY"),
	SITE_ADMIN(4,"SITE_ADMIN","ROLE_ADMIN"),
	SITE_MANAGER(5,"SITE_MANAGER","ROLE_MANAGER");
	
	private final int roleId;
	private final String uiRole;
	private final String dbRole;
	
	RoleHelper(int roleId, String uiRole, String dbRole){
		this.roleId = roleId;
		this.uiRole = uiRole;
		this.dbRole = dbRole;
	}
	
	
	public static int getRoleIdForUIRoleName(String uiRole){
		for(RoleHelper roleEnum: RoleHelper.values()){
			if(roleEnum.uiRole.equalsIgnoreCase(uiRole)){
				return roleEnum.roleId;
			}
		}
		return 0;		
	}
	
	public static String getDbRoleNameForUIRoleName(String uiRole){
		for(RoleHelper roleEnum: RoleHelper.values()){
			if(roleEnum.uiRole.equalsIgnoreCase(uiRole)){
				return roleEnum.dbRole;
			}
		}
		return null;		
	}
	
	public static String getDbRoleNameForDbRoleid(int dbRoleId){
		for(RoleHelper roleEnum: RoleHelper.values()){
			if(roleEnum.roleId == dbRoleId){
				return roleEnum.dbRole;
			}
		}
		return null;	
	}
	
	public static boolean isSiteAdmin(String uiRole){
		if(SITE_ADMIN.uiRole.equalsIgnoreCase(uiRole)){
			return true;
		}
		return false;
	}
	
	public static boolean isSiteManager(String uiRole){
		if(SITE_MANAGER.uiRole.equalsIgnoreCase(uiRole)){
			return true;
		}
		return false;
	}
	
	public static boolean isCustomer(String uiRole){
		if(CUSTOMER.uiRole.equalsIgnoreCase(uiRole)){
			return true;
		} 
		return false;		
	}
	
	public static boolean isDriverAgency(String uiRole){
		if(DRIVER_AGENCY.uiRole.equalsIgnoreCase(uiRole)){
			return true;
		}
		return false;
	}
	
	public static boolean isDriver(String uiRole){
		if(DRIVER.uiRole.equalsIgnoreCase(uiRole)){
			return true;
		}
		return false;
	}
}
