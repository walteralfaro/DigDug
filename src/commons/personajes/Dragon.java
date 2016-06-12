package commons.personajes;

import java.io.Serializable;
import java.net.Socket;

import commons.Score;

public class Dragon extends Jugador implements Serializable{
	
	private static final long serialVersionUID = 3307071588676186225L;

	public Dragon(Integer id, Socket socket, String name, Score historicalScore) {
		super(id, socket, name, historicalScore);
	}

}
