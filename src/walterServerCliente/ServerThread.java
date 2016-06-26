package walterServerCliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerThread extends Thread {

	private Integer userId;
	private String userName = null;

	
	public ServerThread(Integer userId) {
		this.userId = userId;
	}

	public synchronized void run() {
		Boolean endConection = false;
		Message mensaje;
		ObjectOutputStream obstrm;
		try {
			UserConnection userConnectionInstance = UserConnection.getInstance();
			while (!endConection) {				
				try {
					mensaje = new Message("Personaje"+userId,null,null);
					obstrm = new ObjectOutputStream(userConnectionInstance.getUser(userId).getSocket().getOutputStream());
					mensaje.setUserId(userId);
					obstrm.writeObject(mensaje);

					ObjectInputStream obStrm = new ObjectInputStream(userConnectionInstance.getUser(userId).getSocket().getInputStream());
					mensaje =(Message) obStrm.readObject();
					Maps maps = Maps.getInstance();
					if("MOVIMIENTO".equals(mensaje.getMessage())){
						DigDugLogger.log(mensaje.getMessage()+mensaje.getMovimiento1().getKeyCode());
						Movimiento mov = maps.repaint(mensaje.getMovimiento1(),userId);
//						mensaje.setMovimiento1(mov);
					}
					mensaje.setMap(maps.getMapa1());
					obstrm = new ObjectOutputStream(userConnectionInstance.getUser(userId).getSocket().getOutputStream());
					mensaje.setUserId(userId);
					obstrm.writeObject(mensaje);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
