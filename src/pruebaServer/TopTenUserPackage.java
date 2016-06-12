package pruebaServer;

import java.io.Serializable;
import java.util.ArrayList;

public class TopTenUserPackage implements Package, Serializable {

	private static final long serialVersionUID = -1088580331247262201L;
	private static final Integer PACKAGEID = 7;
	private ArrayList<User> topTen;
	
	public TopTenUserPackage(){
		
	}
	
	public TopTenUserPackage(ArrayList<User> topTen) {
		this.topTen = topTen;
	}
	
	public Integer getPackageID() {
		return PACKAGEID;
	}

	public ArrayList<User> getTopTen() {
		return topTen;
	}
	
}
