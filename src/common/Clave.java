package common;

import java.io.Serializable;

public class Clave implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer key;
	public static Clave getNewInstancia(Integer key){
		return new Clave(key);
	}
	public Clave(Integer key){
		this.key = key;
		
	}
	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}
}