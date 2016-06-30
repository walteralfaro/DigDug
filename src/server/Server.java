package server;

import java.net.ServerSocket;

import org.apache.log4j.PropertyConfigurator;

import org.apache.log4j.Logger;


public class Server {

	private static final int PORT = 5000;
	private static final int MAXCONNECTIONS = 50;
	private final static Logger LoggerDigDug = Logger.getLogger(Server.class);
	
	public Server() {
		ServerSocket serverSocket;		
		try {
			serverSocket = new ServerSocket(PORT);
			LoggerDigDug.info("Puerto: " + PORT);
			LoggerDigDug.info("Conexiones maximas: " + MAXCONNECTIONS);
			LoggerDigDug.info("Esperando conexiones...");
			LoggerDigDug.info("Ingrese q para finalizar.");
			ServerEnd.getInstance().start();//para final el servirdor 
			 //Hilo que se encarga de aceptar conecciones
			AcceptUser server = new AcceptUser(serverSocket);
			server.start();//inicia el hilo de cada cliente
			ServerEnd.getInstance().join();//para final el servirdor 
			
			LoggerDigDug.info("Finalizando servidor...");
			UserConnection userConnectionInstance = UserConnection.getInstance();
			for(User eachUser: userConnectionInstance.getUsers()) 
				if(eachUser != null) {
					userConnectionInstance.blockSocket(eachUser.getId());
					userConnectionInstance.sendPackage(eachUser.getId(), new EndServerPackage());
					userConnectionInstance.releaseSocket(eachUser.getId());
				}
			LoggerDigDug.info("Servidor finalizado correctamente");
			System.exit(1);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		new Server();
	}
	
}
