package pruebaServer;

import java.io.Serializable;
import java.net.Socket;


public class User implements Serializable {

	private static final long serialVersionUID = 779490985792071291L;
	private Integer id;
	private String name;
	private Socket socket;
	private Score historicalScore;
	
	public User(Integer id, Socket socket) {
		this.id = id;
		this.socket = socket;
		this.name = "";
		this.historicalScore = null;
	}
	
	public User(String name, Score historicalScore) {
		this.id = null;
		this.socket = null;
		this.name = name;
		this.historicalScore = historicalScore;
	}
	
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
	
	public Score getHistoricalScore() {
		return historicalScore;
	}

}
