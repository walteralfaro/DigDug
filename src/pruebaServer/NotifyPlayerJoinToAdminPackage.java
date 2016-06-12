package pruebaServer;

import java.io.Serializable;

public class NotifyPlayerJoinToAdminPackage implements Serializable, Package {
	
	private static final long serialVersionUID = 378713806090824989L;
	private static final Integer PACKAGEID = 15;
	private String userName;
	
	public NotifyPlayerJoinToAdminPackage(String userName) {
		this.userName = userName;
	}

	public Integer getPackageID() {
		return PACKAGEID;
	}

	public String getUserName() {
		return userName;
	}
}