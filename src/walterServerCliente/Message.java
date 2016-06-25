package walterServerCliente;
import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	private String message;
	private char[][] map;
	private boolean flag;
	private Movimiento movimiento;
	private Integer userId;
	private Integer clave;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	Message(String mensaje){
		this.message = mensaje;
	}

	public Message(String mensaje, Movimiento movimiento, char[][] nivel) {
		this.message = mensaje;
		this.movimiento=movimiento;
		this.map = nivel;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}
	
}
