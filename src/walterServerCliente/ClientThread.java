package walterServerCliente;

import java.io.DataInputStream;

public class ClientThread extends Thread {
	

	private Boolean endConnection = false;
	private Connection connection = Connection.getInstance();
	
	
	public void run() {
		try {
			boolean isTheEnd = false;
			while (!endConnection  && isTheEnd == false) {

				String  texto = new DataInputStream(connection.getSocket().getInputStream()).readUTF();
				System.out.println(texto + "\n");
		
			}
			endConnection = true;
			connection.endConnection();
			System.out.println("Conexion Finalizada");
		} catch (Exception 
				e) {
			e.printStackTrace();
		}
	}
	
}
