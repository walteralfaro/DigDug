package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cliente.Juego;
import common.Message;

public class ServerThread extends Thread {
	private final static Logger LoggerDigDug = Logger.getLogger(ServerThread.class);

	private Integer userIdPosicionDeEntrada;
	private String userName = null;
	private static Integer  KEY_LOGIN = 0;
	private static Integer  KEY_JUEGO = 1;
	private static Integer KEY_LOGIN_VALIDACION_USER_PASS = 2;
	private static Integer KEY_JUEGO_FIN_JUEGO = 3;
	private static Integer KEY_LOGIN_REGISTRAR_USAURIO = 4;
	private static Integer KEY_LOGIN_REGISTRAR_MODIFICAR = 5;
	private static Integer KEY_LOGIN_AGREGAR_JUGADOR = 6;


	public ServerThread(Integer userId) {
		this.userIdPosicionDeEntrada = userId;
	}

	public synchronized void run() {
		Boolean endConection = false;
		JuegoDaoImp dao = new JuegoDaoImp();
		Message mensaje;
		ObjectOutputStream out;
		ObjectInputStream in;
		try {
			UserConnection userConnectionInstance = UserConnection.getInstance();
			while (!endConection) {		
				//el usuario
				User user = userConnectionInstance.getUser(userIdPosicionDeEntrada);
				
			    /// todos los mensajes TIENEN que tener esta interaccion con el servidor por que el JUEGO necesita saber el usuario
				mensaje = new Message();				
				//ENVIA
				out = getObjOutputStrem(user);
				mensaje.setUserIdPosicionDeEntrada(userIdPosicionDeEntrada);
				out.writeObject(mensaje);
				
				//LEEE - Se obtiene la INFO Y LA CLAVE DEL MENSAJE
				in = getObjInputStrem(user);
				mensaje =(Message) in.readObject();
				
				//EN LOS SIGUENTE IF solo depende de la locacion se sabe que hacer
				
				if(mensaje.getLocacion().getKey().equals(KEY_LOGIN)){
					out = getObjOutputStrem(user);				
					mensaje.setCantidadDeUsuarios(getUserConectados(userConnectionInstance,mensaje));
					out.writeObject(mensaje);
				}
				if(mensaje.getLocacion().getKey().equals(KEY_LOGIN_VALIDACION_USER_PASS)){
					out = getObjOutputStrem(user);
					//acepto el usuario cliente y servidor
					boolean aceptado = dao.validaUsuario(mensaje.getName(), mensaje.getPass());
					mensaje.setAceptado(aceptado);
					user.setName(mensaje.getName());
					user.setPass(mensaje.getPass());
					user.setId(dao.obtenerUsuario(mensaje.getName()).getId());
					user.setAceptado(aceptado);
					//busco la cantidad de conectados
					mensaje.setCantidadDeUsuarios(getUserConectados(userConnectionInstance,mensaje));
					mensaje.setIdUser(user.getId());
					out.writeObject(mensaje);		
				}
				
				if(mensaje.getLocacion().getKey().equals(KEY_JUEGO)){
					out = getObjOutputStrem(user);
					Maps maps = Maps.getInstance();
					if(Juego.MENSAJE_MOVIMIENTO.equals(mensaje.getMessage())){
						//DigDugLogger.log(mensaje.getMessage()+mensaje.getMovimiento1().getKeyCode());
						maps.repaint(mensaje.getMovimiento1(),userIdPosicionDeEntrada);
					}
					mensaje.setMap(maps.getMapa1());
					mensaje.setIdPartida(maps.getIdPartida());
					user.setIdPartida(maps.getIdPartida());
					user.setInGame(BigDecimal.ONE.intValue());
					mensaje.setUserIdPosicionDeEntrada(userIdPosicionDeEntrada);
					mensaje.setIdUser(user.getId());
					getNamePlayers(dao, mensaje, userConnectionInstance);
					out.writeObject(mensaje);
				}
				
				if((mensaje.getLocacion().getKey().equals(KEY_LOGIN_REGISTRAR_USAURIO))){
					out = getObjOutputStrem(user);;
					//acepto el usuario cliente y servidor
					boolean aceptado = dao.registrarUsuario(mensaje.getName(), mensaje.getPass());
					mensaje.setAceptadoRegistrado(aceptado);
					out.writeObject(mensaje);	
				}
				
				if((mensaje.getLocacion().getKey().equals(KEY_LOGIN_REGISTRAR_MODIFICAR))){
					out = getObjOutputStrem(user);
					//acepto el usuario cliente y servidor
					boolean aceptado = dao.actualizarUsuario(mensaje.getName(),mensaje.getNname(),mensaje.getNpass());
					mensaje.setAceptadoModificado(aceptado);
					out.writeObject(mensaje);	
				}
				
				if((mensaje.getLocacion().getKey().equals(KEY_LOGIN_AGREGAR_JUGADOR))){
					out = getObjOutputStrem(user);					
					agregarJugador(userConnectionInstance,mensaje);
					out.writeObject(mensaje);
				}
				
				if(mensaje.getLocacion().getKey().equals(KEY_JUEGO_FIN_JUEGO)){
					LoggerDigDug.info("Finalizando conexion con el cliente " + userIdPosicionDeEntrada);
					user.setAceptado(false);
					user.setInGame(BigDecimal.ZERO.intValue());
					endConection = true;
				}
			}
			
			if(userName != null)
				LoggerDigDug.info("El usuario " + userName + " se ha desconectado.");

			userConnectionInstance.closeOutputStream(userIdPosicionDeEntrada);
			userConnectionInstance.closeInputStream(userIdPosicionDeEntrada);
			userConnectionInstance.freeUser(userIdPosicionDeEntrada);
			
			LoggerDigDug.info("Conexion finalizada correctamente con el cliente: " + userIdPosicionDeEntrada);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getNamePlayers(JuegoDaoImp dao, Message mensaje, UserConnection users) {
		if(users.getUser(0)!=null)
		  mensaje.setNamePlayer1(dao.obtenerUsuarioId(users.getUser(0).getId()));
		
		if(users.getUser(1)!=null)
			mensaje.setNamePlayer2(dao.obtenerUsuarioId(users.getUser(1).getId()));
		
		if(users.getUser(2)!=null)
			mensaje.setNamePlayer3(dao.obtenerUsuarioId(users.getUser(2).getId()));
		
		if(users.getUser(3)!=null)
			mensaje.setNamePlayer4(dao.obtenerUsuarioId(users.getUser(3).getId()));
	}

	private int getUserConectados(UserConnection userConnectionInstance , Message mensaje) {
		int cantidad = 0;
		List<User> listUser = userConnectionInstance.getUsers();
		for (User user : listUser) {
			if(user!=null && user.isAceptado()){
				cantidad++;
			}
		}
		return cantidad;
	}
	
	private void agregarJugador(UserConnection userConnectionInstance , Message mensaje) {
		int cantidad = 0;
		List<User> listUser = userConnectionInstance.getUsers();
		for (User user : listUser) {
			if(user!=null && user.isAceptado()){
				cantidad= cantidad + user.getInGame();
			}
		}
		mensaje.setAgregarJugador(cantidad<4);
	}
	
	
	private ObjectOutputStream getObjOutputStrem(User user){
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(user.getSocket().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	
	private ObjectInputStream getObjInputStrem(User user){
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(user.getSocket().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}
}
