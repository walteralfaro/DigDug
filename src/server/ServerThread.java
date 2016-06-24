package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import commons.DigDugLogger;
import commons.Maps;
import commons.Message;


public class ServerThread extends Thread{
	
    private Socket socket;
    
    private boolean corriendo;
    
    private  String tipoPlayer;

    public ServerThread(Socket socket, String tipo){//, ControladorFactory controladorFactory) {
        super("HiloEscucha: " + socket.getInetAddress());
        corriendo = true;
        tipoPlayer = tipo ;
        this.socket = socket;
    }
    
    @Override
    public void run() {
		Message mensaje;
		ObjectOutputStream obstrm;
    	while(true) {
    		
			try {
				ObjectInputStream obStrm = new ObjectInputStream(socket.getInputStream());
				mensaje =(Message) obStrm.readObject();
				DigDugLogger.log(mensaje.getMessage());
				Maps maps = Maps.getInstance();
				mensaje.setFlag(true);//maps.mover(mensaje));
				obstrm = new ObjectOutputStream(socket.getOutputStream());
				obstrm.writeObject(mensaje);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

    }

}
