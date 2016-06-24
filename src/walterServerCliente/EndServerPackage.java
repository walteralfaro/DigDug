package walterServerCliente;

import java.io.Serializable;

public class EndServerPackage implements Serializable, Package {
	
	private static final long serialVersionUID = 1974115774864352030L;
	private static final Integer PACKAGEID = 14;
	
	public EndServerPackage() {
	}
	
	public Integer getPackageID() {
		return PACKAGEID;
	}
	
}
