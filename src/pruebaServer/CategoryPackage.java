package pruebaServer;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryPackage implements Serializable, Package {

	private static final long serialVersionUID = 1322572983931253501L;
	private static final Integer PACKAGEID = 5;
	private ArrayList<Category> categories; 
	private int idScreen;
	
	public CategoryPackage(){
		
	}
	
	public CategoryPackage(ArrayList<Category> categories,int idScreen) {
		this.categories = categories;
		this.idScreen = idScreen;
	}
	
	public CategoryPackage(int idScreen) {
		this.idScreen = idScreen;
	}

	public ArrayList<Category> getCategories() {
		return categories;
	}
	
	public int getIdScreen(){
		return idScreen;
	}
	
	public Integer getPackageID() {
		return PACKAGEID;
	}

}