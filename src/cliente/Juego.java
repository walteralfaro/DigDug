package cliente;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;

import commons.Coordenada;
import commons.DigDugLogger;
import commons.Message;
import commons.Movimiento;


public class Juego extends JApplet implements Runnable, KeyListener, Jugable {
	
	private static final long serialVersionUID = 1L;

	public Juego() {}
	
    int keyCode=0;
    
    private String variableMensaje;
	private char nivel[][] = {};
	// VECTOR PARA CARGAR TODAS LAS IMAGENES
    private BufferedImage nivel_img[] = new BufferedImage[14];
    
    private BufferedImage jugador1; //excavador
    private BufferedImage jugador2; //dragon
    private BufferedImage jugador3; //etc
    private BufferedImage jugador4; //etc
    
    private BufferedImage p1_der;
    private BufferedImage p1_izq;
    private BufferedImage p1_arriba;
    private BufferedImage p1_abajo;

    private BufferedImage p2_der;
    private BufferedImage p2_izq;
    private BufferedImage p2_arriba;
    private BufferedImage p2_abajo;
    
    private BufferedImage flor;
    private BufferedImage piedra;
    
    private BufferedImage bimg;

    private Image image;

    private Thread thread;
    
    private boolean music_on = true;
    
    AudioClip musiquita_sound;
    AudioClip eat_sound;
    AudioClip die_sound;
   
    
// POSICIONES INICIALES
    private int x_jugador1 = 3;
    private int y_jugador1 = 4;
    
    private int x_jugador2 = 14;
    private int y_jugador2 = 4;
    
    private int x_flor = 20;
    private int y_flor = 1;
    
    private int x_piedra = 16;
    private int y_piedra = 2;
    
    Socket cliente;
	String ip ="localhost";
	Message mensaje;
	
    public void init() {
    	
    	try {
    		cliente = new Socket(ip, PUERTO);  
    		ObjectOutputStream obstrm;

    		Movimiento mov = new Movimiento();
    		Coordenada pos = new Coordenada();
    		pos.setX(x_jugador1);
        	pos.setY(y_jugador1);
        	mov.setPosicion(pos);
        	mov.setKeyCode(keyCode);
			mensaje = new Message(MENSAJE_VACIO, mov, nivel);
			obstrm = new ObjectOutputStream(cliente.getOutputStream());
			obstrm.writeObject(mensaje);
			variableMensaje = MENSAJE_VACIO;
			ObjectInputStream obiStrm = new ObjectInputStream(cliente.getInputStream());
			
			try {
				mensaje =(Message) obiStrm.readObject();
//				if(mensaje.isFlag()){
//					DigDugLogger.log("Volvio:"+mensaje.isFlag());
					nivel = mensaje.getMap();
					
			}catch(Exception e){
				
			}
	    	setBackground(Color.black);
	    	String musica_path = "musica1/";
	    	
	    	 try{
	         	 musiquita_sound = Applet.newAudioClip( new URL("file:" + musica_path + "Dig_Dug_Theme_Song_HD.wav") );
	             //eat_sound = Applet.newAudioClip(new URL("file:" + musica_path + "eat_01.wav"));
	             die_sound = Applet.newAudioClip(new URL("file:" + musica_path + "Dig_Dug_Kill_Enemy_Sound_Effect.mp3"));
	             musiquita_sound.play();
	             musiquita_sound.loop();
	         
	           }
	         catch (IOException e) { }
	    	 
	    	try {
	    		nivel_img[0] = ImageIO.read(new File("imagenes/negro.png")); //lugar excavado
	    		nivel_img[1] = ImageIO.read(new File("imagenes/tierra1.png"));
	    		nivel_img[2] = ImageIO.read(new File("imagenes/tierra2.png"));
	    		nivel_img[3] = ImageIO.read(new File("imagenes/tierra3.png"));
	    		nivel_img[4] = ImageIO.read(new File("imagenes/tierra4.png"));
	    		
	    		nivel_img[5] = ImageIO.read(new File("imagenes/cielo.png"));
	    		nivel_img[6] = ImageIO.read(new File("imagenes/cielo.png"));
	    		
	    		nivel_img[7] = ImageIO.read(new File("imagenes/sprites_flor.jpg"));
	    		nivel_img[8] = ImageIO.read(new File("imagenes/sprites_piedra.png"));
	    		
	    		nivel_img[9] = ImageIO.read(new File("imagenes/negro.png")); //pared lateral y abajo
	    		
	    		nivel_img[10] = ImageIO.read(new File("imagenes/sprites_dig_dug_derecha.gif"));
	    		nivel_img[11] = ImageIO.read(new File("imagenes/sprites_dig_dug_derecha.gif"));
	    		nivel_img[12] = ImageIO.read(new File("imagenes/sprites_dragon_derecha.gif"));
	    		nivel_img[13] = ImageIO.read(new File("imagenes/sprites_bichito_derecha.gif"));
	    		
	    		p1_der = ImageIO.read(new File("imagenes/sprites_dig_dug_derecha.gif"));
	    		p1_izq = ImageIO.read(new File("imagenes/sprites_dig_dug_izquierda.gif"));
	    		p1_arriba = ImageIO.read(new File("imagenes/sprites_dig_dug_derecha_arriba.gif"));
	    		p1_abajo = ImageIO.read(new File("imagenes/sprites_dig_dug_derecha_abajo.gif"));
	    		
	    		p2_der = ImageIO.read(new File("imagenes/sprites_dragon_derecha.gif"));
	    		p2_izq = ImageIO.read(new File("imagenes/sprites_dragon_izquierda.gif"));
	    		p2_arriba = ImageIO.read(new File("imagenes/sprites_dragon_derecha_arriba.gif"));
	    		p2_abajo = ImageIO.read(new File("imagenes/sprites_dragon_derecha_abajo.gif"));    		
	    		
	    	 } catch (IOException e) { } 
	    	
	    	//jugador: tiene la imagen actual
	    	jugador1 = nivel_img[10];
	    	// de los demas jugadores recibo la posicion desde el server: der izq arriba abajo
	    	jugador2 = nivel_img[12];
	    	//jugador3 = nivel_img[13];
	    	//jugador4 = nivel_img[11];
	    	nivel[y_jugador1][x_jugador1] = 10;
	    	//nivel[y_jugador2][x_jugador2] = 11;
	    	nivel[y_jugador2][x_jugador2] = 12;
	    	//nivel[y_jugador2][x_jugador2] = 13;
	    	nivel[y_flor][x_flor] = 7;
	    	nivel[y_piedra][x_piedra] = 8;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    }
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g; // Convertimos a g de Graphics a Graphics2D
        //g2d.drawImage(image, 0, 0, this);
        g2d.setStroke(new BasicStroke(5.0f));
       //drawNivel(30, 30, g2d);
        
        for (int x = 0; x <= ANCHO_NIVEL-1; x++) {      // 23 ANCHO de 0 a 22
        	for (int y = 0; y <= ALTO_NIVEL-1; y++) {   // 24 ALTO  de 0 a 23
        		g2d.drawImage(nivel_img[nivel[y][x]], x*30, y*30, null);
        		
        		//g2d.drawImage(nivel_img[7], x_flor*30, y_flor*30, 30, 30, null);
        		//g2d.drawImage(nivel_img[8], x_piedra*30, y_piedra*30, 30, 30, null);
        		
        		//(imgen, x y desde donde * 30 pixels, x y tama�o 30 pixels)
        		
        		//g2d.drawImage(nivel_img[10], x_jugador1*30, y_jugador1*30, 30, 30, null); //dibujo al jugador1 en su posicion
        		//g2d.drawImage(nivel_img[12], x_jugador2*30, y_jugador2*30, 30, 30, null); //dibujo al jugador3 en su posicion
        		
        	}
        	
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }//paint()
    
    public void run() {
        Thread yo = Thread.currentThread();

		ObjectOutputStream obstrm;
		Movimiento mov = new Movimiento();
		Coordenada pos = new Coordenada();
        while (thread == yo) {
//            repaint();
            try {
            	pos.setX(x_jugador1);
            	pos.setY(y_jugador1);
            	mov.setPosicion(pos);
            	mov.setKeyCode(keyCode);
				mensaje = new Message(variableMensaje, mov, nivel);
				obstrm = new ObjectOutputStream(cliente.getOutputStream());
				obstrm.writeObject(mensaje);
				variableMensaje = MENSAJE_VACIO;
				ObjectInputStream obiStrm = new ObjectInputStream(cliente.getInputStream());
				try {
					mensaje =(Message) obiStrm.readObject();
					nivel = mensaje.getMap();
					x_jugador1 = mensaje.getMovimiento().getPosicion().getX();
					y_jugador1 = mensaje.getMovimiento().getPosicion().getY();
					repaint();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				thread.sleep(60);
            } catch (InterruptedException e) { break; } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
         
        thread = null;
    }
    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }
    
    public synchronized void stop() {
        thread = null;
    }	
	public static void main(String argv[]) {
		
		final Juego ventanaJuego = new Juego();
		ventanaJuego.init();
        
        Frame f = new Frame("Dig-Dug");
        
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
            public void windowDeiconified(WindowEvent e) { ventanaJuego.start(); }
            public void windowIconified(WindowEvent e) { ventanaJuego.stop(); }
        });
        
        f.add(ventanaJuego);
        f.pack();
        f.setSize( new Dimension(ANCHO_VENTANA, ALTO_VENTANA) );
        f.setIconImage( new ImageIcon("./imagenes/dig_dug_dragon.jpg").getImage() ); //iconito de la ventana
        f.show();
        f.addKeyListener(ventanaJuego);

        ventanaJuego.start();
        
	} //main

	@Override
	public void keyPressed(KeyEvent e) {
	    keyCode = e.getKeyCode();	  	
			    switch( keyCode ) { 
			    	case KeyEvent.VK_UP:
			    		nivel_img[10] = p1_arriba;
						variableMensaje = MENSAJE_MOVIMIENTO;
			            break;
			            
			        case KeyEvent.VK_DOWN:
			        	nivel_img[10] = p1_abajo;
			        	variableMensaje = MENSAJE_MOVIMIENTO;
			            break;
			            
			        case KeyEvent.VK_LEFT:
			        	nivel_img[10] = p1_izq;
			        	variableMensaje = MENSAJE_MOVIMIENTO;
			            break;
			            
			        case KeyEvent.VK_RIGHT :
			        	nivel_img[10] = p1_der;
			        	variableMensaje = MENSAJE_MOVIMIENTO;
			            break;
			            
			        // MUSICA ON/OFF
			        case KeyEvent.VK_M:
			        	if (music_on == true) {
			        		music_on = false;
			        		musiquita_sound.stop();
			        	}else {
			        		music_on = true;
			        		musiquita_sound.loop();
			        	}
			        	
			            
			     }// switch
//	  		}else{
//	  			
//	  		}
	    
	    
/*
	    // SCORE
	    // va sumando el SCORE, play al sonido de comer y borra las PAPITAS del laberinto
	    if (nivel[y][x] == 1) {
	    	eat_sound.play();
	    	nivel[y][x] = 0;
	    	score[2]++; //unidades
	    	
	    	if (score[2] == 58) {
	    		score[2] = 48;
	    		score[1]++; //decenas
	    	}
	    	if (score[1] == 58) {
	    		score[1] = 48;
	    		score[0]++; //centenas
	    	}
	    }// if
*/
	    
	    
	}//keyPressed
	
	
	
	@Override
	public void keyReleased(KeyEvent arg0) { }
	@Override
	public void keyTyped(KeyEvent arg0) { }
	
	
	
} //class Juego
