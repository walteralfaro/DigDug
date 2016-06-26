package walterServerCliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ServerThread extends Thread {

	private Integer userIdPosicionDeEntrada;
	private String userName = null;
	private static Integer  KEY_LOGIN = 0;
	private static Integer  KEY_JUEGO = 1;
	private static Integer KEY_LOGIN_VALIDACION_USER_PASS = 2;

	
	public ServerThread(Integer userId) {
		this.userIdPosicionDeEntrada = userId;
	}

	public synchronized void run() {
		Boolean endConection = false;
		JuegoDaoImp dao = new JuegoDaoImp();
		Message mensaje;
		ObjectOutputStream obstrm;
		try {
			UserConnection userConnectionInstance = UserConnection.getInstance();
			while (!endConection) {		
				//el usuario
				User user = userConnectionInstance.getUser(userIdPosicionDeEntrada);
				
			    /// todos los mensajes TIENEN que tener esta interaccion con el servidor por que el JUEGO necesita saber el usuario
				mensaje = new Message();				
				obstrm = new ObjectOutputStream(user.getSocket().getOutputStream());
				mensaje.setIdUser(user.getId());
				mensaje.setUserIdPosicionDeEntrada(userIdPosicionDeEntrada);
				obstrm.writeObject(mensaje);
				
				//Se obtiene la INFO Y LA CLAVE DEL MENSAJE
				ObjectInputStream obStrm = new ObjectInputStream(userConnectionInstance.getUser(userIdPosicionDeEntrada).getSocket().getInputStream());
				mensaje.setIdUser(user.getId());
				mensaje =(Message) obStrm.readObject();
				//////
				
				//EN LOS SIGUENTE IF solo depende de la locacion se sabe que hacer
				
				if(mensaje.getLocacion().getKey().equals(KEY_LOGIN)){
					obstrm = new ObjectOutputStream(userConnectionInstance.getUser(userIdPosicionDeEntrada).getSocket().getOutputStream());					
					mensaje.setCantidadDeUsuarios(getUserUsuariosConectados(userConnectionInstance,mensaje));
					mensaje.setIdUser(user.getId());
					obstrm.writeObject(mensaje);
				}
				if(mensaje.getLocacion().getKey().equals(KEY_LOGIN_VALIDACION_USER_PASS)){
					obstrm = new ObjectOutputStream(userConnectionInstance.getUser(userIdPosicionDeEntrada).getSocket().getOutputStream());
					//acepto el usuario cliente y servidor
					boolean aceptado = dao.validaUsuario(mensaje.getName(), mensaje.getPass());
					mensaje.setAceptado(aceptado);
					user.setAceptado(aceptado);
					//busco la cantidad de conectados
					mensaje.setCantidadDeUsuarios(getUserUsuariosConectados(userConnectionInstance,mensaje));
					
					mensaje.setIdUser(user.getId());
					obstrm.writeObject(mensaje);		
				}
				
				if(mensaje.getLocacion().getKey().equals(KEY_JUEGO)){
					Maps maps = Maps.getInstance();
					if("MOVIMIENTO".equals(mensaje.getMessage())){
						DigDugLogger.log(mensaje.getMessage()+mensaje.getMovimiento1().getKeyCode());
						maps.repaint(mensaje.getMovimiento1(),userIdPosicionDeEntrada);
					}
					mensaje.setMap(maps.getMapa1());
					obstrm = new ObjectOutputStream(userConnectionInstance.getUser(userIdPosicionDeEntrada).getSocket().getOutputStream());
					mensaje.setUserIdPosicionDeEntrada(userIdPosicionDeEntrada);
					mensaje.setIdUser(user.getId());
					obstrm.writeObject(mensaje);
				}
			}
			
			if(userName != null)
				Logger.info("El usuario " + userName + " se ha desconectado.");

			userConnectionInstance.closeOutputStream(userIdPosicionDeEntrada);
			userConnectionInstance.closeInputStream(userIdPosicionDeEntrada);
			userConnectionInstance.freeUser(userIdPosicionDeEntrada);
			
			Logger.info("Conexion finalizada correctamente con el cliente: " + userIdPosicionDeEntrada);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getUserUsuariosConectados(UserConnection userConnectionInstance , Message mensaje) {
		int cantidad = 0;
		List<User> listUser = userConnectionInstance.getUsers();
		for (User user : listUser) {
			if(user!=null && user.isAceptado()){
				cantidad++;
			}
		}
		return cantidad;
	}
}
