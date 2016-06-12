package commons.personajes;

import java.net.Socket;

import commons.Score;
import commons.Ubicable;

public class Jugador extends Ubicable {

	private Integer id;
	private String name;
	private Socket socket;
	private Score historicalScore;

	public Jugador(Integer id, Socket socket,String name, Score historicalScore) {
		this.id = id;
		this.socket = socket;
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
	
	private int moverseAbajo(){
		return 1;	
	}
	private int moverseArriba(){
		return 2;	
	}
	private int moverseDerecha(){
		return 3;	
	}
	private int moverseIzquierda(){
		return 4;	
	}
}
