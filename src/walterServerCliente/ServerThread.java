package walterServerCliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ServerThread extends Thread {

	private Integer userId;
	private String userName = null;
	
	public ServerThread(Integer userId) {
		this.userId = userId;
	}

	public void run() {
		Boolean endConection = false;
		boolean isTheEnd = false;

		try {
			UserConnection userConnectionInstance = UserConnection.getInstance();
			
			while (!endConection && isTheEnd == false) {
					System.out.println("mandando algo a todos los clientes");
					//aca iria lo que hace el user .. la navegacion de las pantallas
					ArrayList<User> users  = userConnectionInstance.getUsers();
					System.out.println("Enviando");
					for(User indice : users) {
						//if(userId != indice.getId())
						if(indice !=null)
							new DataOutputStream(indice.getSocket().getOutputStream()).writeUTF("TODOS");
						
					}
					 
					 char c; 
						BufferedReader br = new 
						BufferedReader(new InputStreamReader(System.in)); 
						c = (char) br.read(); 
						if(c == 'q') isTheEnd = true;
			}
			
			if(userName != null)
				Logger.info("El usuario " + userName + " se ha desconectado.");

			userConnectionInstance.closeOutputStream(userId);
			userConnectionInstance.closeInputStream(userId);
			userConnectionInstance.freeUser(userId);
			
			Logger.info("Conexion finalizada correctamente con el cliente: " + userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
