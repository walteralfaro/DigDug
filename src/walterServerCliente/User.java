package walterServerCliente;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.Socket;


public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private Socket socket;
	private String pass;
	private Integer score;
	private boolean aceptado = false;
	private Long idPartida;
	private Integer inGame = BigDecimal.ZERO.intValue();

	//private Score historicalScore;

	public Integer getInGame() {
		return inGame;
	}

	public void setInGame(Integer inGame) {
		this.inGame = inGame;
	}

	public Long getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(Long idPartida) {
		this.idPartida = idPartida;
	}

	public User(Integer id, Socket socket) {
		this.id = id;
		this.socket = socket;
		this.name = "";
		//this.historicalScore = null;
	}
	
	/*public User(String name, Score historicalScore) {
		this.id = null;
		this.socket = null;
		this.name = name;
		//this.historicalScore = historicalScore;
	}*/
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Socket getSocket() {
		return socket;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	/*public Score getHistoricalScore() {
		return historicalScore;
	}*/
	
	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}
}
