package walterServerCliente;

import java.io.Serializable;

public class Movimiento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Coordenada posicion;
	private int keyCode;
	public Movimiento(){}
	public Movimiento(Coordenada posicion, int keyCode){
		this.posicion=posicion;
		this.keyCode=keyCode;
	}
	public Coordenada getPosicion() {
		return posicion;
	}
	public void setPosicion(Coordenada posicion) {
		this.posicion = posicion;
	}

	public int getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}	
}
