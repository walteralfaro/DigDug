package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import commons.Maps;

public class Listener extends Thread{

	// El puerto por defecto para el constructor sin argumentos.
    private final static int PUERTO_POR_DEFECTO = 2048;
    
    // Generador de sockets.
    private final ServerSocket serverSocket;

    // Indica si el hilo está corriendo.
    private boolean corriendo;
    
    // Mantiene una referencia a los hilos que creó para cerrarlos. 
    private List<ServerThread> hilosEscucha;
    
    // Bloquea la eliminación de hilos. 
//    private Semaphore semaforo;
    
    // Indica si actualmente se están eliminado los hilos.
    private boolean matandoHilos;
    
    /**
     * Crea un servicio de escucha para un puerto determinado.
     * @param puerto
     * @return 
     * @throws ZombielandException
     */
    public Listener(int puerto) throws Exception {
//        super("ServicioEscucha");
//        Log.info("Arrancando servidor");
        corriendo = true;
        hilosEscucha = new ArrayList<ServerThread>();
        try {
            serverSocket = new ServerSocket(puerto);
        } catch (IOException e) {
//            Log.error("No se pudo abrir el socket: ");
//            Log.error(e.getMessage());
            e.printStackTrace();
            throw new Exception("No se pudo iniciar el servidor: "
                    + e.getLocalizedMessage());
        }
//        semaforo = new Semaphore(1);
        matandoHilos = false;
    }
    
    /**
     * Crea un servicio de escucha con el puerto por defecto.
     * @throws ZombielandException
     */
    public Listener() throws Exception {
    	this(PUERTO_POR_DEFECTO);
	}

    @Override
    public void run() {
//    	Log.info("Servidor arrancado");
        while (corriendo) {
            try {
            	Maps mapa = Maps.getInstance();
                ServerThread hilo = new ServerThread(serverSocket.accept(),"");
                hilo.start();
                synchronized (this) {
                    hilosEscucha.add(hilo);
                }
            } catch (SocketException e) {
                try {
//                	semaforo.acquire();
                    matandoHilos = true;
                    for (ServerThread hilo : hilosEscucha) {
                        try {
                            // Permito que el hilo se cierre.
//                            semaforo.release();
                            hilo.join(1000);
                            // Y lo vuelvo a bloquear.
//                            semaforo.acquire();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
//                    semaforSo.release();
                } catch (Exception e1) {
                }
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cierra al servicio de escucha, limpiando todos los recursos que adquirió.
     */
    public void cerrar() {
//    	Log.info("Cerrando servidor");
        corriendo = false;
        if (serverSocket != null)
            try {
                serverSocket.close();
            } catch (IOException e) {
            }
    }

    /**
     * @return los hilos de escucha.
     */
    public List<ServerThread> getHilos() {
        return hilosEscucha;
    }

//    @Override
//    public synchronized void hiloCerrado(ListenerThread hilo) {
//        try {
//            // Bloqueo si se está operando sobre el listado de hilos. 
//            semaforo.acquire();
//        } catch (InterruptedException e) {
//            Log.error("Interrumpido al adquirir el semáforo para hiloCerrado: " + e.getMessage());
//            return;
//        }
//        // Me aseguro que no se estén matando hilos para removerme del listado.
//        if (!matandoHilos)
//            hilosEscucha.remove(hilo);
//        semaforo.release();
//    }
}
