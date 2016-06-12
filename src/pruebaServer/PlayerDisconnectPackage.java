package pruebaServer;

import java.io.Serializable;

public class PlayerDisconnectPackage implements Serializable, Package {

	private static final long serialVersionUID = -3719603863382808234L;
	private static final Integer PACKAGEID = 16;
	private String userName;
	private Boolean gameCanceled;
	
	public PlayerDisconnectPackage() {
	}
	
	public PlayerDisconnectPackage(String userName, Boolean gameCanceled) {
		this.userName = userName;
		this.gameCanceled = gameCanceled;
	}
	
	public Integer getPackageID() {
		return PACKAGEID;
	}
	
	public String getUserName() {
		return userName;
	}

	public Boolean gameCanceled() {
		return gameCanceled;
	}
	
}
