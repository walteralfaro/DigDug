package walterServerCliente;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
	
	
	private ObjectOutputStream outputObject;
	private ObjectInputStream inputObject;
	private Socket socket;
	private static Connection connection;
	private String ip;
	private int port;

	
	private Connection (){
		try{
			getProperties();
			socket = new Socket(ip, port);
			outputObject = new ObjectOutputStream(socket.getOutputStream());
			inputObject = new ObjectInputStream(socket.getInputStream());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static Connection getInstance(){
		
		if(connection == null){
			connection = new Connection();
		}
		return connection;
	}
	
	public void sendPackage (Object paquete){
		try{
			connection.outputObject.writeObject(paquete);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Object recievePackage (){
		Object response = -1;
		try{
			response = inputObject.readObject();
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}

	public void endConnection() {
		try {
			inputObject.close();
			outputObject.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void getProperties (){
		try{
			Properties properties = new Properties();
			//esta configuracion solo la levante para el cliente para saber en que ip conectar del servidor y puerto
			properties.load(new FileInputStream("propiedades.properties"));
			ip = properties.getProperty("ip");
			port = Integer.parseInt(properties.getProperty("port"));
		    //	ip=192.168.1.34
			//  port=1000
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public Socket getSocket (){
		return socket;
	}
}
