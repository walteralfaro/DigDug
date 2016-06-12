package commons.personajes;

import java.io.Serializable;
import java.net.Socket;

import commons.Score;

public class Bola extends Jugador implements Serializable {
	
	private static final long serialVersionUID = 3307071588676186223L;

	public Bola(Integer id, Socket socket, String name, Score historicalScore) {
		super(id, socket, name, historicalScore);
	}

}
