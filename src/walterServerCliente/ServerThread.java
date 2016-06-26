package walterServerCliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerThread extends Thread {

	private Integer userId;
	private String userName = null;
	private static Integer  KEY_LOGIN = 0;
	private static Integer  KEY_JUEGO = 1;

	
	public ServerThread(Integer userId) {
		this.userId = userId;
	}

	public synchronized void run() {
		Boolean endConection = false;
		Clave clave;
		JuegoDaoImp dao = new JuegoDaoImp();
		Message mensaje;
		ObjectOutputStream obstrm;
		try {
			UserConnection userConnectionInstance = UserConnection.getInstance();
			while (!endConection) {		
				mensaje = new Message();
				obstrm = new ObjectOutputStream(userConnectionInstance.getUser(userId).getSocket().getOutputStream());
				mensaje.setUserId(userId);
				obstrm.writeObject(mensaje);
								
				ObjectInputStream obStrm = new ObjectInputStream(userConnectionInstance.getUser(userId).getSocket().getInputStream());
				mensaje =(Message) obStrm.readObject();
				
				if(mensaje.getKey().getKey().equals(KEY_LOGIN)){
					obstrm = new ObjectOutputStream(userConnectionInstance.getUser(userId).getSocket().getOutputStream());
					mensaje.setUserId(userId);
					mensaje.setCantidadDeUsuarios(dao.obtenerUsuarios().size());
					obstrm.writeObject(mensaje);
				}
				
				if(mensaje.getKey().getKey().equals(KEY_JUEGO)){
					Maps maps = Maps.getInstance();
					if("MOVIMIENTO".equals(mensaje.getMessage())){
						DigDugLogger.log(mensaje.getMessage()+mensaje.getMovimiento1().getKeyCode());
						maps.repaint(mensaje.getMovimiento1(),userId);
					}
					mensaje.setMap(maps.getMapa1());
					obstrm = new ObjectOutputStream(userConnectionInstance.getUser(userId).getSocket().getOutputStream());
					mensaje.setUserId(userId);
					obstrm.writeObject(mensaje);
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
