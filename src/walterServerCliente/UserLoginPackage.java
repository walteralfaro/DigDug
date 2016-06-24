package walterServerCliente;

import java.io.Serializable;

public class UserLoginPackage implements Serializable, Package {

	private static final long serialVersionUID = 664747009248310784L;
	private static final Integer PACKAGEID = 1;
	private String user;
	private String password;
	private Integer userType;
	
	public UserLoginPackage(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public UserLoginPackage(String user, String password, Integer userType) {
		this(user, password);
		this.userType = userType;
	}
	
	public Integer getPackageID() {
		return PACKAGEID;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Integer getUserType() {
		return userType;
	}
	
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
}
