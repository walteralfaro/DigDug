package cliente;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import common.Clave;
import common.Coordenada;
import common.Message;
import common.Movimiento;
import server.Connection;


public class Juego extends JApplet implements Runnable, KeyListener ,Jugable{
	private final static Logger LoggerDigDug = Logger.getLogger(Juego.class);
	private Connection connection;
	private Integer idPosicionDeEntrada;

	public Juego() {}
    int keyCode=0;
	private char nivel[][] = {};

    private String variableMensaje;	
	// VECTOR PARA CARGAR TODAS LAS IMAGENES
    private BufferedImage nivel_img[] = new BufferedImage[19];
    private BufferedImage jugador1; //excavador
    private BufferedImage jugador2; //dragon
    private BufferedImage jugador3; //bichito
    private BufferedImage jugador4; //pacman
    private BufferedImage p1_der;
    private BufferedImage p1_izq;
    private BufferedImage p1_arriba;
    private BufferedImage p1_abajo;
    private BufferedImage p2_der;
    private BufferedImage p2_izq;
    private BufferedImage p2_arriba;
    private BufferedImage p2_abajo;
    private BufferedImage p3_der;
    private BufferedImage p3_izq;
    private BufferedImage p3_arriba;
    private BufferedImage p3_abajo;
    private BufferedImage p4_der;
    private BufferedImage p4_izq;
    private BufferedImage p4_arriba;
    private BufferedImage p4_abajo;
    private BufferedImage flor;
    private BufferedImage piedra;
    private BufferedImage bimg;
    private Long sleep=new Long(100);
    private Image image;
    private Thread thread;
    private boolean music_on = true;
	String musica_path = "sonidos/";
    AudioClip musiquita_sound;
    AudioClip musiquita_gameover;
    protected JLabel label_nombre1 = new JLabel("INSERT COIN");
    protected JLabel label_nombre2 = new JLabel("INSERT COIN");
    protected JLabel label_nombre3 = new JLabel("INSERT COIN");
    protected JLabel label_nombre4 = new JLabel("INSERT COIN");
    private int score_jugador1 = 0;
    private int score_jugador2 = 0;
    private int score_jugador3 = 0;
    private int score_jugador4 = 0;

// POSICIONES INICIALES
    private int x_jugador1 = 3;
    private int y_jugador1 = 4;
    private int x_jugador2 = 12;
    private int y_jugador2 = 6;
    private int x_jugador3 = 4;
    private int y_jugador3 = 14;
    private int x_jugador4 = 10;
    private int y_jugador4 = 10;
    private int x_flor = 20;
    private int y_flor = 1;
    private int x_piedra = 16;
    private int y_piedra = 2;
    private String orientacion;
	Message mensaje;
	Frame f;
	

	Color colorBlanco = new Color (255, 255, 255);
    Font stringFont = new Font("tahoma", Font.BOLD, 16);
    static int count = 0; // TIEMPO 0
    CustomFont cft = new CustomFont("Gameplay.ttf");
    

    public Timer timer = new Timer (1000, new ActionListener () { 
        public void actionPerformed(ActionEvent e) { 
            count++;
         } 
    }); 
    
    
	public void init() {
	    	try {
	    		ObjectOutputStream obstrm;
				ObjectInputStream obiStrm = new ObjectInputStream(connection.getSocket().getInputStream());
				mensaje =(Message) obiStrm.readObject();
				idPosicionDeEntrada = mensaje.getUserIdPosicionDeEntrada();
	    		
	    		Movimiento mov = new Movimiento();
	    		Coordenada pos = new Coordenada();
	    		
	    		if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(0))==0){
		    		pos.setX(x_jugador1);
		        	pos.setY(y_jugador1);
	    		}else if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(1))==0){
	    			pos.setX(x_jugador2);
		        	pos.setY(y_jugador2);
	    		}
	    		else if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(2))==0){
	    			pos.setX(x_jugador3);
		        	pos.setY(y_jugador3);
	    		}else if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(3))==0){
	    			pos.setX(x_jugador4);
		        	pos.setY(y_jugador4);
	    		}
	    		
	    		
	        	mov.setPosicion(pos);
	        	mov.setKeyCode(keyCode);
				mensaje = new Message(MENSAJE_VACIO, mov, nivel);
				mensaje.setLocacion(Clave.getNewInstancia(KEY_JUEGO));
				obstrm = new ObjectOutputStream(connection.getSocket().getOutputStream());
				obstrm.writeObject(mensaje);
				variableMensaje = MENSAJE_VACIO;
				obiStrm = new ObjectInputStream(connection.getSocket().getInputStream());			
				mensaje =(Message) obiStrm.readObject();
				nivel = mensaje.getMap();

		    	setBackground(Color.black);
		    	
		    	 try{
		         	 musiquita_sound = Applet.newAudioClip( new URL("file:" + musica_path + "Dig_Dug_Theme_Song_HD.wav") );
		         	 musiquita_gameover = Applet.newAudioClip( new URL("file:" + musica_path + "Dig_Dug_Done_Sound_Effect.wav") );
		         	 //musiquita_sound.play();
		             musiquita_sound.loop();
		         }catch (IOException e) { }
		    	 
		    	try {
		    		nivel_img[0] = ImageIO.read(new File("imagenes/negro.png")); //lugar excavado
		    		nivel_img[1] = ImageIO.read(new File("imagenes/tierra1.png"));
		    		nivel_img[2] = ImageIO.read(new File("imagenes/tierra2.png"));
		    		nivel_img[3] = ImageIO.read(new File("imagenes/tierra3.png"));
		    		nivel_img[4] = ImageIO.read(new File("imagenes/tierra4.png"));
		    		nivel_img[5] = ImageIO.read(new File("imagenes/cielo.png"));
		    		nivel_img[6] = ImageIO.read(new File("imagenes/cielo.png"));
		    		nivel_img[7] = ImageIO.read(new File("imagenes/sprites_flor.jpg"));
		    		nivel_img[8] = ImageIO.read(new File("imagenes/sprites_piedra2.png"));
		    		nivel_img[9] = ImageIO.read(new File("imagenes/negro.png")); //pared lateral y abajo
		    		nivel_img[10] = ImageIO.read(new File("imagenes/sprites_dig_dug_derecha.gif"));
		    	    nivel_img[11] = ImageIO.read(new File("imagenes/sprites_bichito_derecha.gif"));
		    	    nivel_img[12] = ImageIO.read(new File("imagenes/sprites_dragon_derecha.gif"));
		    	    nivel_img[13] = ImageIO.read(new File("imagenes/monster1.png"));
		    		/***PRUEBA***/
		    		nivel_img[14] = ImageIO.read(new File("imagenes/sprites_fuego1_derecha.gif"));
		    		nivel_img[15] = ImageIO.read(new File("imagenes/sprites_dig_dug_derecha.gif"));
		    	    nivel_img[16] = ImageIO.read(new File("imagenes/sprites_bichito_derecha.gif"));
		    	    nivel_img[17] = ImageIO.read(new File("imagenes/sprites_dragon_derecha.gif"));
		    	    nivel_img[18] = ImageIO.read(new File("imagenes/monster1.png"));
		    		/******/
		    		p1_der = ImageIO.read(new File("imagenes/sprites_dig_dug_derecha.gif"));
		    		p1_izq = ImageIO.read(new File("imagenes/sprites_dig_dug_izquierda.gif"));
		    		p1_arriba = ImageIO.read(new File("imagenes/sprites_dig_dug_derecha_arriba.gif"));
		    		p1_abajo = ImageIO.read(new File("imagenes/sprites_dig_dug_derecha_abajo.gif"));
		    		
		    		p2_der = ImageIO.read(new File("imagenes/sprites_dragon_derecha.gif"));
		    		p2_izq = ImageIO.read(new File("imagenes/sprites_dragon_izquierda.gif"));
		    		p2_arriba = ImageIO.read(new File("imagenes/sprites_dragon_derecha_arriba.gif"));
		    		p2_abajo = ImageIO.read(new File("imagenes/sprites_dragon_derecha_abajo.gif"));    	
		    		
		    		p3_der = ImageIO.read(new File("imagenes/sprites_bichito_derecha.gif"));
		    	    p3_izq = ImageIO.read(new File("imagenes/sprites_bichito_izquierda.gif"));
		    	    p3_arriba = ImageIO.read(new File("imagenes/sprites_bichito_derecha_arriba.gif"));
		    	    p3_abajo = ImageIO.read(new File("imagenes/sprites_bichito_derecha_abajo.gif"));
		    	    
		    	    p4_der = ImageIO.read(new File("imagenes/monster1.png"));
		    	    p4_izq = ImageIO.read(new File("imagenes/monster1.png"));
		    	    p4_arriba = ImageIO.read(new File("imagenes/monster1.png"));
		    	    p4_abajo = ImageIO.read(new File("imagenes/monster1.png"));   
		    		
		    	 } catch (IOException e) { } 
		    	
		    	if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(0))==0){
		    		jugador1 = nivel_img[10];
		    	}
		    	if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(1))==0){
		    		jugador2 = nivel_img[12];
		    	}
		    	if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(2))==0){
		    		jugador3 = nivel_img[11];
		    	}
		    	if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(3))==0){
		    		jugador4 = nivel_img[13];
		    	}
		    	if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(0))==0){
     		    	nivel[y_jugador1][x_jugador1] = 10;
		    	}
		    	if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(1))==0){
		    		nivel[y_jugador2][x_jugador2] = 12;
		    	}
		    	if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(2))==0){
		    		nivel[y_jugador3][x_jugador3] = 11;
		    	}
		    	if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(3))==0){
		    		nivel[y_jugador4][x_jugador4] = 13;
		    	}
		    	nivel[y_flor][x_flor] = 7;
		    	nivel[y_piedra][x_piedra] = 8;
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
    

    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D)g;
        
    	//if ( obtenerFinDejuego(nivel,mensaje.getCantidadDeUsuarios()) == false){
    	

            g2d.setStroke(new BasicStroke(5.0f));
        for (int x = 0; x <= ANCHO_NIVEL-1; x++) {      // 23 ANCHO de 0 a 22
        	for (int y = 0; y <= ALTO_NIVEL-1; y++) {   // 24 ALTO  de 0 a 23
        		g2d.drawImage(nivel_img[nivel[y][x]], x*30, y*30, null);	
        	
        		g2d.setFont(cft.MyFont(0,20));
        		//g2d.setFont(stringFont);
        		g2d.setColor(colorBlanco);
        		g2d.drawString("TIEMPO ", 610, 740);
                g2d.drawString(String.valueOf(count), 710, 740);
        	}
        }
    	
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
    	//}//if fin
    }
    
    
    public synchronized void run() {
        Thread yo = Thread.currentThread();    
        Socket socket = connection.getSocket();
		ObjectOutputStream obstrm;
		Movimiento mov = new Movimiento();
		Coordenada pos = new Coordenada();
		boolean fin = false;
        while (thread == yo && !fin) {
            try {				
            	
            	ObjectInputStream obiStrm = new ObjectInputStream(connection.getSocket().getInputStream());
				mensaje =(Message) obiStrm.readObject();

				if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(0))==0){
					pos.setX(x_jugador1);
	            	pos.setY(y_jugador1);
	    		}else if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(1))==0){
					pos.setX(x_jugador2);
	            	pos.setY(y_jugador2);
	    		}else if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(2))==0){
					pos.setX(x_jugador3);
	            	pos.setY(y_jugador3);
	    		}else if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(3))==0){
	    			pos.setX(x_jugador4);
		        	pos.setY(y_jugador4);
	    		}
				
            	mov.setPosicion(pos);
            	mov.setKeyCode(keyCode);
            	mov.setOrientacion(orientacion);
				mensaje = new Message(variableMensaje, mov, nivel);
				obstrm = new ObjectOutputStream(socket.getOutputStream());
				mensaje.setLocacion(Clave.getNewInstancia(KEY_JUEGO));
				obstrm.writeObject(mensaje);
				variableMensaje = MENSAJE_VACIO;
			    obiStrm = new ObjectInputStream(socket.getInputStream());
				try {
					mensaje =(Message) obiStrm.readObject();
					nivel = mensaje.getMap();
					boolean vivo = estaVivo(nivel[ mensaje.getMovimiento1().getPosicion().getX()][ mensaje.getMovimiento1().getPosicion().getY()]);

					if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(0))==0){
						x_jugador1 = mensaje.getMovimiento1().getPosicion().getX();
						y_jugador1 = mensaje.getMovimiento1().getPosicion().getY();
						//vivo = estoyVivo2(nivel[x_jugador1][y_jugador1]);
					}else if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(1))==0){
						x_jugador2 = mensaje.getMovimiento1().getPosicion().getX();
						y_jugador2 = mensaje.getMovimiento1().getPosicion().getY();
						//vivo = estoyVivo2(nivel[x_jugador2][y_jugador2]);
					}else if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(2))==0){
						x_jugador3 = mensaje.getMovimiento1().getPosicion().getX();
						y_jugador3 = mensaje.getMovimiento1().getPosicion().getY();
						//vivo = estoyVivo2(nivel[x_jugador3][y_jugador3]);
					}else if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(3))==0){
						x_jugador4 = mensaje.getMovimiento1().getPosicion().getX();
						y_jugador4 = mensaje.getMovimiento1().getPosicion().getY();
						//vivo = estoyVivo2(nivel[x_jugador4][y_jugador4]);
					}

					if(!mensaje.getNamePlayer1().isEmpty()){
						//score_jugador1 = score_jugador1 + mensaje.getCantMuerto1();
						//label_nombre1.setText(mensaje.getNamePlayer1() +" - " + score_jugador1);
						label_nombre1.setText(mensaje.getNamePlayer1() +" - " +  mensaje.getCantMuerto1());
					}
					
					if(!mensaje.getNamePlayer2().isEmpty()){
						//score_jugador2 = score_jugador2 + mensaje.getCantMuerto2();
						//label_nombre2.setText(mensaje.getNamePlayer2() +" - " + score_jugador2);
						label_nombre2.setText(mensaje.getNamePlayer2() +" - " + mensaje.getCantMuerto2());
					}
					
					if(!mensaje.getNamePlayer3().isEmpty()){
						//score_jugador3 = score_jugador3 + mensaje.getCantMuerto3();
						//label_nombre3.setText(mensaje.getNamePlayer3() +" - " + score_jugador3);
						label_nombre3.setText(mensaje.getNamePlayer3() +" - " + mensaje.getCantMuerto3());
					}
					
					if(!mensaje.getNamePlayer4().isEmpty()){
						//score_jugador4 = score_jugador4 + mensaje.getCantMuerto4();
						//label_nombre4.setText(mensaje.getNamePlayer4() +" - " + score_jugador4);
						label_nombre4.setText(mensaje.getNamePlayer4() +" - " + mensaje.getCantMuerto4());
					}

					
					
					 fin = obtenerFinDejuego(nivel,mensaje.getCantidadDeUsuarios());
			
					repaint();	
					
					//LoggerDigDug.info("idPartida : " + mensaje.getIdPartida());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				thread.sleep(sleep);
            } catch (InterruptedException e) { break; } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        
		
		
		if(fin){	
			if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(0))==0){
				cartelDeFin(estaVivo(nivel[y_jugador1][ x_jugador1]));
			}else if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(1))==0){
				cartelDeFin(estaVivo(nivel[y_jugador2][x_jugador2]));
			}else if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(2))==0){
				cartelDeFin(estaVivo(nivel[y_jugador3][x_jugador3]));
			}else if(mensaje.getUserIdPosicionDeEntrada().compareTo(Integer.valueOf(3))==0){
				cartelDeFin(estaVivo(nivel[y_jugador4][x_jugador4]));
			}
		}	
         
        thread = null;
    }


	private void cartelDeFin(boolean vivo) {

		ObjectOutputStream obstrm;
		ObjectInputStream  instrem;
		Message mensaje;
		
		try {
			//recibe.
			//esto para lo unico que es necesario.. es por que el juego (el mapa) tiene que recibir el mensaje del mapa...
			//capas se puede mejorar .. pero tenes que tocar todas las llamadas al server			
			instrem = new ObjectInputStream(connection.getSocket().getInputStream());
			mensaje =(Message) instrem.readObject();
			
			//Se envia el fin del juego
			obstrm = new ObjectOutputStream(connection.getSocket().getOutputStream());
			mensaje.setLocacion(Clave.getNewInstancia(KEY_JUEGO_FIN_JUEGO));				
			obstrm.writeObject(mensaje);

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if(vivo){
			//System.out.println("GANEE");
			gane();
		}else{
			//System.out.println("LOOOOSERRR");
			perdi();
		}
	}
    
	public boolean estaVivo(char value){
		if(value == 14)
			return false;
		return true;
	}
	
    private boolean obtenerFinDejuego(char[][] nivel,int cantJugadores) {	
    	int cont = 0;
    	for (int i = 0; i < Jugable.ANCHO_NIVEL; i++) {
    		for (int j = 0; j < Jugable.ALTO_NIVEL; j++) {
    			if(nivel[j][i]==14){
       			 cont++; 
       		 	}
    		}
		}
 		 
    	 if(cont == cantJugadores-1){
    		 return true;
    	 }else{
    		 return false;
    	 }
	}

    
    public void gane(){
    	
    	JPanelFondo jpf = new JPanelFondo();
		jpf.setBounds(0, 0, 900, 700);
		jpf.setSize(900, 700);
		//jpf.setVisible(true);
		//jpf.setEnabled(true);
		//jpf.setBackground(Color.YELLOW);
		//jpf.setForeground(Color.GREEN);
		jpf.setBackground("imagenes/victory.png");
		jpf.setOpaque(false);
		//this.getContentPane().removeAll();
		add(jpf);
		revalidate();
		
    	musiquita_sound.stop();
    	musiquita_gameover.play();	
    }
    public void perdi(){
    	
    	JPanelFondo jpf = new JPanelFondo();
		jpf.setBounds(0, 0, 900, 700);
		jpf.setSize(900, 700);
		//jpf.setVisible(true);
		//jpf.setEnabled(true);
		//jpf.setBackground(Color.YELLOW);
		//jpf.setForeground(Color.GREEN);
		//jpf.setBackground("imagenes/gameover_animada_duck_hunt.gif");
		jpf.setBackground("imagenes/Duck_hunt_nes_dog.jpg");
		jpf.setOpaque(false);
		//this.getContentPane().removeAll();
		add(jpf);
		revalidate();
		
    	musiquita_sound.stop();
    	musiquita_gameover.play();
    	
    }
    
    
    
    

	public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }
    
    public synchronized void stop() {
        thread = null;
    }
	
	public void inGame(Connection con){
		connection = con;
		
		if(connection.getSocket() != null){
	        init();
	        f = new Frame("Dig-Dug");
			//label_nombre1.setText("jugador1");
			label_nombre1.setOpaque(true); // para que no sea transparente
			label_nombre1.setBackground(Color.black);
			label_nombre1.setForeground(Color.white);

			label_nombre1.setBounds(80, 20, 85, 30);
			//label_nombre1.setFont(stringFont);
			label_nombre1.setFont(cft.MyFont(0,11));

				
			label_nombre2.setOpaque(true); // para que no sea transparente
			label_nombre2.setBackground(Color.black);
			label_nombre2.setForeground(Color.white);

			label_nombre2.setBounds(440, 20, 85, 30);
			//label_nombre2.setFont(stringFont);
			label_nombre2.setFont(cft.MyFont(0,11));

			
			label_nombre3.setOpaque(true); // para que no sea transparente
			label_nombre3.setBackground(Color.black);
			label_nombre3.setForeground(Color.white);
			
			label_nombre3.setBounds(250, 20, 85, 30);
			//label_nombre3.setFont(stringFont);
			label_nombre3.setFont(cft.MyFont(0,11));

			
			label_nombre4.setOpaque(true); // para que no sea transparente
			label_nombre4.setBackground(Color.black);
			label_nombre4.setForeground(Color.white);

			label_nombre4.setBounds(650, 20, 85, 30);
			//label_nombre4.setFont(stringFont);
			label_nombre4.setFont(cft.MyFont(0,11));

			f.add(label_nombre1);
			f.add(label_nombre2);
			f.add(label_nombre3);
			f.add(label_nombre4);

			
	        f.addWindowListener(new WindowAdapter() {
	            public void windowClosing(WindowEvent e) {
	        		ObjectOutputStream obstrm;
	        		ObjectInputStream  instrem;
	        		Message mensaje;
	        		
	        		try {
	        			//recibe.
	        			//esto para lo unico que es necesario.. es por que el juego (el mapa) tiene que recibir el mensaje del mapa...
	        			//capas se puede mejorar .. pero tenes que tocar todas las llamadas al server			
	        			instrem = new ObjectInputStream(connection.getSocket().getInputStream());
	        			mensaje =(Message) instrem.readObject();
	        			
	        			//Se envia el fin del juego
	        			obstrm = new ObjectOutputStream(connection.getSocket().getOutputStream());
	        			mensaje.setLocacion(Clave.getNewInstancia(KEY_JUEGO_FIN_JUEGO));				
	        			obstrm.writeObject(mensaje);
	
	        		} catch (IOException e1) {
	        			//e1.printStackTrace();
	        		} catch (ClassNotFoundException e1) {
	        			//e1.printStackTrace();
	        		}
	        		
	            	System.exit(0);
	            	}
	            public void windowDeiconified(WindowEvent e) { start(); }
	            public void windowIconified(WindowEvent e) { stop(); }
	        });
	        
	        f.add(this);
	        f.pack();
	        //f.setSize( 780, 750 ); //(ancho, alto)
	        f.setSize( new Dimension(ANCHO_VENTANA, ALTO_VENTANA) );
	        f.setLocationRelativeTo(null);
	        f.setIconImage( new ImageIcon("./imagenes/dig_dug.jpg").getImage() ); //iconito de la ventana
	        f.show();
	        f.addKeyListener(this);
		    f.setResizable(false); //va en false
	        start();
	        timer.start();
		}else{
			CantConnectWindow cantconnectwindow = new CantConnectWindow();
			cantconnectwindow.setVisible(true);
		}    
	}

	@Override
	public void keyPressed(KeyEvent e) {
	    keyCode = e.getKeyCode();	  	
			    switch( keyCode ) { 
			    	case KeyEvent.VK_UP:
			    		if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(0)) == 0){
			    			nivel_img[10] = p1_arriba;
			    		}else if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(1)) == 0){
			    			nivel_img[12] = p2_arriba;
			    		}else if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(2)) == 0){
			    			nivel_img[11] = p3_arriba;
			    		}else if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(3)) == 0){
			    			nivel_img[13] = p4_arriba;
			    		}
			    		orientacion = ARRIBA;
						variableMensaje = MENSAJE_MOVIMIENTO;
			            break;
			            
			        case KeyEvent.VK_DOWN:
			        	if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(0)) == 0){
			    			nivel_img[10] = p1_abajo;
			    		}else if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(1)) == 0){
			    			nivel_img[12] = p2_abajo;
			    		}else if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(2)) == 0){
			    			nivel_img[11] = p3_abajo;
			    		}else if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(3)) == 0){
			    			nivel_img[13] = p4_abajo;
			    		}
			        	orientacion = ABAJO;
			        	variableMensaje = MENSAJE_MOVIMIENTO;
			            break;
			            
			        case KeyEvent.VK_LEFT:
			        	if(this.idPosicionDeEntrada.compareTo(0) == 0){
			    			nivel_img[10] = p1_izq;
			    		}else if(this.idPosicionDeEntrada.compareTo(1) == 0){
			    			nivel_img[12] = p2_izq;
			    		}else if(this.idPosicionDeEntrada.compareTo(2) == 0){
			    			nivel_img[11] = p3_izq;
			    		}else if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(3)) == 0){
			    			nivel_img[13] = p4_izq;
			    		}
			        	orientacion = IZQUIERDA;
			        	variableMensaje = MENSAJE_MOVIMIENTO;
			            break;
			            
			        case KeyEvent.VK_RIGHT :
			        	if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(0)) == 0){
			    			nivel_img[10] = p1_der;
			    		}else if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(1)) == 0){
			    			nivel_img[12] = p2_der;
			    		}else if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(2)) == 0){
			    			nivel_img[11] = p3_der;
			    		}else if(this.idPosicionDeEntrada.compareTo(Integer.valueOf(3)) == 0){
			    			nivel_img[13] = p4_der;
			    		}
			        	orientacion = DERECHA;
			        	variableMensaje = MENSAJE_MOVIMIENTO;
			            break;
			        case KeyEvent.VK_A:
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
			     }
	}
	
	public boolean estoyVivo(int value){
		if(value == 55)
			return false;
		return true;
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) { }
	@Override
	public void keyTyped(KeyEvent arg0) { }	
}