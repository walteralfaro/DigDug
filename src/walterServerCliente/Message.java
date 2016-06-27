package walterServerCliente;
import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	private String message;
	private char[][] map;
	private boolean flag;
	private Movimiento movimiento1;
	private Movimiento movimiento2;
	private Coordenada flor;
	private Coordenada piedra;
	private Integer clave;
	private Integer score1;
	private Integer score2;
	private Integer cantidadDeUsuarios;
	//para saber donde estoy y que voy hacer
	private Clave locacion;
	//para validar el usaurio
	private String name;
	private String pass;
	private boolean aceptado = false;
	private boolean aceptadoRegistrado = false;
	private Integer userIdPosicionDeEntrada;
	private Integer idUser;
	

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}

	public Integer getCantidadDeUsuarios() {
		return cantidadDeUsuarios;
	}

	public void setCantidadDeUsuarios(Integer cantidadDeUsuarios) {
		this.cantidadDeUsuarios = cantidadDeUsuarios;
	}

	public Integer getUserIdPosicionDeEntrada() {
		return userIdPosicionDeEntrada;
	}

	public void setUserIdPosicionDeEntrada(Integer userId) {
		this.userIdPosicionDeEntrada = userId;
	}


	Message(){}
	
	Message(String mensaje){
		this.message = mensaje;
	}

	public Message(String mensaje, Movimiento movimiento, char[][] nivel) {
		this.message = mensaje;
		this.movimiento1=movimiento;
		this.map = nivel;
	}

	public Message(String mensaje, Movimiento movimiento,Movimiento movimiento2, char[][] nivel) {
		this.message = mensaje;
		this.movimiento1=movimiento;
		this.movimiento2=movimiento2;
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

	public Movimiento getMovimiento1() {
		return movimiento1;
	}

	public void setMovimiento1(Movimiento movimiento) {
		this.movimiento1 = movimiento;
	}


	public Movimiento getMovimiento2() {
		return movimiento2;
	}

	public void setMovimiento2(Movimiento movimiento2) {
		this.movimiento2 = movimiento2;
	}

	public Integer getClave() {
		return clave;
	}

	public void setClave(Integer clave) {
		this.clave = clave;
	}

	public Integer getScore1() {
		return score1;
	}

	public void setScore1(Integer score1) {
		this.score1 = score1;
	}

	public Integer getScore2() {
		return score2;
	}

	public void setScore2(Integer score2) {
		this.score2 = score2;
	}

	public Coordenada getFlor() {
		return flor;
	}

	public void setFlor(Coordenada flor) {
		this.flor = flor;
	}

	public Coordenada getPiedra() {
		return piedra;
	}

	public void setPiedra(Coordenada piedra) {
		this.piedra = piedra;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Clave getLocacion() {
		return locacion;
	}

	public void setLocacion(Clave key) {
		this.locacion = key;
	}
	
	public boolean isAceptadoRegistrado() {
		return aceptadoRegistrado;
	}

	public void setAceptadoRegistrado(boolean aceptadoRegistrado) {
		this.aceptadoRegistrado = aceptadoRegistrado;
	}

}
