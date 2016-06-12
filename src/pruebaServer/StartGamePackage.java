package pruebaServer;

import java.io.Serializable;

public class StartGamePackage implements Serializable, Package {

	private static final long serialVersionUID = -961426379852472252L;
	private static final Integer PACKAGEID = 4;
	private Boolean canStartGame;

	public StartGamePackage(){	
	}
	
	public StartGamePackage(Boolean canStartGame){
		this.canStartGame = canStartGame;
	}
	
	public Integer getPackageID() {
		return PACKAGEID;
	}
	
	public Boolean canStartGame() {
		return canStartGame;
	}
	
	
}
