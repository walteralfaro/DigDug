package pruebaServer;

import java.net.ServerSocket;


public class Server {

	private static final int PORT = 10000;
	private static final int MAXCONNECTIONS = 50;
	
	public Server() {
		ServerSocket serverSocket;
		
		try {
			serverSocket = new ServerSocket(PORT);
			Logger.info("Puerto: " + PORT);
			Logger.info("Conexiones maximas: " + MAXCONNECTIONS);
			Logger.info("Esperando conexiones...");
			Logger.info("Ingrese q para finalizar.");
			ServerEnd.getInstance().start();
			AcceptUser server = new AcceptUser(serverSocket); //Hilo que se encarga de aceptar conecciones
			server.start();
			ServerEnd.getInstance().join();
			Logger.info("Finalizando servidor...");
			UserConnection userConnectionInstance = UserConnection.getInstance();
			for(User eachUser: userConnectionInstance.getUsers()) 
				if(eachUser != null) {
					userConnectionInstance.blockSocket(eachUser.getId());
					userConnectionInstance.sendPackage(eachUser.getId(), new EndServerPackage());
					userConnectionInstance.releaseSocket(eachUser.getId());
				}
			Logger.info("Servidor finalizado correctamente");
			System.exit(1);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Server();
	}
	
}
