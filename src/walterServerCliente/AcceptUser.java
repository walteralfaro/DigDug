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
