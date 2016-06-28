package walterServerCliente;

import java.net.ServerSocket;
import java.net.Socket;

public class AcceptUser extends Thread {
	private ServerSocket serverSocket;
	
	public AcceptUser(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public void run() {
		Socket clientSocket;
		UserConnection clientSocketInstance = UserConnection.getInstance();
		int clientId;
		Maps map = Maps.getInstance();
		map.agregarFlor(new Coordenada(2,1));
		map.agregarFlor(new Coordenada(3,1));
		map.agregarFlor(new Coordenada(21,1));
		map.agregarFlor(new Coordenada(22,1));
		map.agregarPiedra(new Coordenada(20,8));
		map.agregarPiedra(new Coordenada(7,15));
		map.agregarPiedra(new Coordenada(5,8));

		while(ServerEnd.getInstance().isTheEnd() == false) {
			try {
				clientSocket = serverSocket.accept();
				if((clientId = clientSocketInstance.getFreeIndexUser()) != -1) {
					clientSocketInstance.setUserConnection(new User(clientId, clientSocket));
					Logger.info("Conexion Aceptada. ClientId: " + clientId);
					new ServerThread(clientId).start(); //Crea una conexion nueva de escucha para cada cliente
				} else {
					Logger.warn("No hay más conexiones disponibles");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
