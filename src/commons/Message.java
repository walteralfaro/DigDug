package commons;
import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	private String message;
	private int keyCode;
	private int posX;
	private int posY;
	private char[][] map;
	private boolean flag;
	
	Message(String mensaje){
		this.message = mensaje;
	}

	public Message(String mensaje, int x_jugador1, int y_jugador1, int keyCode2, char[][] nivel) {
		this.message = mensaje;
		this.keyCode = keyCode2;
		this.posX = x_jugador1;
		this.posY = y_jugador1;
		this.map = nivel;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public char[][] getMap() {
		return map;
	}

	public void setMap(char[][] map) {
		this.map = map;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
	
}
