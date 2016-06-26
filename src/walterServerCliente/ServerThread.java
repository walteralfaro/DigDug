package walterServerCliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ServerThread extends Thread {

	private Integer userId;
	private String userName = null;
	private static Integer  KEY_LOGIN = 0;
	private static Integer  KEY_JUEGO = 1;
	private static Integer KEY_LOGIN_VALIDACION_USER_PASS = 2;

	
	public ServerThread(Integer userId) {
		this.userId = userId;
	}

	public synchronized void run() {
		Boolean endConection = false;
		JuegoDaoImp dao = new JuegoDaoImp();
		Message mensaje;
		ObjectOutputStream obstrm;
		try {
			UserConnection userConnectionInstance = UserConnection.getInstance();
			while (!endConection) {		
			    /// todos los mensajes TIENEN que tener esta interaccion con el servidor por que el JUEGO necesita saber el usuario
				mensaje = new Message();
				obstrm = new ObjectOutputStream(userConnectionInstance.getUser(userId).getSocket().getOutputStream());
				mensaje.setUserId(userId);
				obstrm.writeObject(mensaje);
				
				//Se obtiene la INFO Y LA CLAVE DEL MENSAJE
				ObjectInputStream obStrm = new ObjectInputStream(userConnectionInstance.getUser(userId).getSocket().getInputStream());
				mensaje =(Message) obStrm.readObject();
				//////
				
				//aca solo depende de la locacion se sabe que hacer
				if(mensaje.getLocacion().getKey().equals(KEY_LOGIN)){
					obstrm = new ObjectOutputStream(userConnectionInstance.getUser(userId).getSocket().getOutputStream());					
					mensaje.setCantidadDeUsuarios(getUserUsuariosConectados(userConnectionInstance,mensaje));
					obstrm.writeObject(mensaje);
				}
				if(mensaje.getLocacion().getKey().equals(KEY_LOGIN_VALIDACION_USER_PASS)){
					obstrm = new ObjectOutputStream(userConnectionInstance.getUser(userId).getSocket().getOutputStream());
					mensaje.setAceptado(dao.validaUsuario(mensaje.getName(), mensaje.getPass()));
					mensaje.setCantidadDeUsuarios(getUserUsuariosConectados(userConnectionInstance,mensaje));
					obstrm.writeObject(mensaje);		
				}
				
				if(mensaje.getLocacion().getKey().equals(KEY_JUEGO)){
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

	private int getUserUsuariosConectados(UserConnection userConnectionInstance , Message mensaje) {
		int cantidad = 0;
		List<User> listUser = userConnectionInstance.getUsers();
		for (User user : listUser) {
			if(user!=null && mensaje.isAceptado() ){
				cantidad++;
			}
		}
		return cantidad;
	}
}
