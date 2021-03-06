package server;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import cliente.Juego;
import common.Coordenada;

public class AcceptUser extends Thread {
	private final static Logger LoggerDigDug = Logger.getLogger(AcceptUser.class);

	private ServerSocket serverSocket;
	
	public AcceptUser(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public void run() {
		Socket clientSocket;
		UserConnection clientSocketInstance = UserConnection.getInstance();
		int clientId;
		Maps map = Maps.getInstance();
		map.agregarFlor(new Coordenada(2,2));
		map.agregarFlor(new Coordenada(3,2));
		map.agregarFlor(new Coordenada(21,2));
		map.agregarFlor(new Coordenada(22,2));
		map.agregarPiedra(new Coordenada(20,8));
		map.agregarPiedra(new Coordenada(7,15));
		map.agregarPiedra(new Coordenada(5,8));

		while(ServerEnd.getInstance().isTheEnd() == false) {
			try {
				clientSocket = serverSocket.accept();
				if((clientId = clientSocketInstance.getFreeIndexUser()) != -1) {
					clientSocketInstance.setUserConnection(new User(clientId, clientSocket));
					LoggerDigDug.info("Conexion Aceptada. ClientId: " + clientId);
					new ServerThread(clientId).start(); //Crea una conexion nueva de escucha para cada cliente
				} else {
					LoggerDigDug.warn("No hay más conexiones disponibles");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
