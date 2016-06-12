package commons.personajes;

import java.io.Serializable;
import java.net.Socket;

import commons.Score;

public class DigDug extends Jugador implements Serializable {
	
	private static final long serialVersionUID = 3307071588676186224L;

	public DigDug(Integer id, Socket socket, String name, Score historicalScore) {
		super(id, socket, name, historicalScore);
	}

}
