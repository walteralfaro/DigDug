package pruebaServer;

import java.io.Serializable;

public class Category implements Serializable {
	
	private static final long serialVersionUID = 4850529438667146682L;
	private Integer id;
	private String category;
	
	public Category(Integer id, String category) {
		this.id = id;
		this.category = category;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getCategory() {
		return category;
	}
}
