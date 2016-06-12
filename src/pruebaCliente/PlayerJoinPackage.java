package pruebaCliente;

import java.io.Serializable;

public class PlayerJoinPackage implements Serializable, Package {
	
	private static final long serialVersionUID = 989024739531282591L;
	private static final Integer PACKAGEID = 3;
	private Integer joinStatus;
	
	public PlayerJoinPackage(){
	}

	public PlayerJoinPackage(int joinStatus) {
		this.joinStatus = joinStatus;
	}
	
	public Integer getPackageID() {
		return PACKAGEID;
	}

	public Integer getJoinStatus() {
		return joinStatus;
	}
}
