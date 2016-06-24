package walterServerCliente;

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
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;


public class Juego extends JApplet implements Runnable, KeyListener {

	public static int ALTO_VENTANA = 800;
    public static int ANCHO_VENTANA = 705;
    public static Frame meFrame = null;
    
    
    private int ALTO_NIVEL = 24;
    private int ANCHO_NIVEL = 23;
    
	private char nivel[][] = {
			
    		{ 9, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9}, //1
    		{ 9, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 9}, 
    		{ 9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9}, 
    		{ 9, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9}, 
    		{ 9, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 9}, //5
    		{ 9, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9}, 
    		{ 9, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9}, 
    		{ 9, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9}, 
    		{ 9, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9}, 
    		{ 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9}, //10
    		{ 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9}, 
    		{ 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9}, 
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3, 9}, //15
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 3, 3, 3, 3, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 9}, 
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 9}, //20
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 4, 4, 9}, //21
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9}, //22
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9}, //23
    		{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, //24
    };
	// 9 pared lateral y abajo
	// 5 cielo pared
	// 6 cielo para subir y moverse
	// 7 flor, 8 piedra
	// 0 espacios para moverse en negro
	// 1 2 3 4 tierra para excavar
	// 10 11 12 13 jugadores
	
	

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

    private int x_jugador1 = 3;
    private int y_jugador1 = 4;
    
    private int x_jugador2 = 14;
    private int y_jugador2 = 4;
    
    private int x_flor = 20;
    private int y_flor = 1;
    
    private int x_piedra = 16;
    private int y_piedra = 2;
    
    

    public void init() {
    	setBackground(Color.black);
    	
    	String musica_path = "musica1/";
    	
    	 try{
         	 musiquita_sound = Applet.newAudioClip( new URL("file:" + musica_path + "Dig_Dug_Theme_Song_HD.wav") );
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
    	
    	
    	//inicio jugadores de acuerdo a lo que diga el server
    	//p1:excavador
    	//p2:dragon
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
    	
    }//init()
	
    

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g; // Convertimos a g de Graphics a Graphics2D
        g2d.setStroke(new BasicStroke(5.0f));        
        for (int x = 0; x <= ANCHO_NIVEL-1; x++) {      // 23 ANCHO de 0 a 22
        	for (int y = 0; y <= ALTO_NIVEL-1; y++) {   // 24 ALTO  de 0 a 23
        		g2d.drawImage(nivel_img[nivel[y][x]], x*30, y*30, null);
        	}
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void run() {
        Thread yo = Thread.currentThread();      
        while (thread == yo) {
            repaint();
            try {
            	thread.sleep(10);
            } catch (InterruptedException e) { break; }
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
	}
	
	public static Frame cargarJuego(Juego ventanaJuego) {
		
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
        
        //ventanaJuego.start();   
        //return ventanaJuego;
        return f;
	}


	@Override
	public void keyPressed(KeyEvent e) {
	    int keyCode = e.getKeyCode();
	    
	    switch( keyCode ) { 
	    
	    	case KeyEvent.VK_UP:
	    		nivel_img[10] = p1_arriba;
	    		
	        	if ( (nivel[y_jugador1-1][x_jugador1] == 0) || (nivel[y_jugador1-1][x_jugador1] == 1) || (nivel[y_jugador1-1][x_jugador1] == 2) || (nivel[y_jugador1-1][x_jugador1] == 3) || (nivel[y_jugador1-1][x_jugador1] == 4) || (nivel[y_jugador1-1][x_jugador1] == 6) ) {
	        			nivel[y_jugador1][x_jugador1] = 0; //set en la matriz el lugar excavado
	        		y_jugador1 = y_jugador1 - 1; //set posicion movimiento arriba
	        		nivel[y_jugador1][x_jugador1] = 10;
	        	}
	            break;
	            
	        case KeyEvent.VK_DOWN:
	        	nivel_img[10] = p1_abajo;
	        	
	        	if ( (nivel[y_jugador1+1][x_jugador1] == 0) || (nivel[y_jugador1+1][x_jugador1] == 1) || (nivel[y_jugador1+1][x_jugador1] == 2) || (nivel[y_jugador1+1][x_jugador1] == 3) || (nivel[y_jugador1+1][x_jugador1] == 4) || (nivel[y_jugador1+1][x_jugador1] == 6) ) {
	        			nivel[y_jugador1][x_jugador1] = 0;
	        		y_jugador1 = y_jugador1 + 1;
	        		nivel[y_jugador1][x_jugador1] = 10;
	        	}
	            break;
	            
	        case KeyEvent.VK_LEFT:
	        	nivel_img[10] = p1_izq;
	        	
	        	if ( (nivel[y_jugador1][x_jugador1-1] == 0) || (nivel[y_jugador1][x_jugador1-1] == 1) || (nivel[y_jugador1][x_jugador1-1] == 2) || (nivel[y_jugador1][x_jugador1-1] == 3) || (nivel[y_jugador1][x_jugador1-1] == 4) || (nivel[y_jugador1][x_jugador1-1] == 6) ) {

	        			nivel[y_jugador1][x_jugador1] = 0;
	        		x_jugador1 = x_jugador1 - 1;
	        		nivel[y_jugador1][x_jugador1] = 10;
	        	}
	            break;
	            
	        case KeyEvent.VK_RIGHT :
	        	nivel_img[10] = p1_der;
	        	
	        	if ( (nivel[y_jugador1][x_jugador1+1] == 0) || (nivel[y_jugador1][x_jugador1+1] == 1) || (nivel[y_jugador1][x_jugador1+1] == 2) || (nivel[y_jugador1][x_jugador1+1] == 3) || (nivel[y_jugador1][x_jugador1+1] == 4) || (nivel[y_jugador1][x_jugador1+1] == 6) ) {

	        			nivel[y_jugador1][x_jugador1] = 0;
	        		x_jugador1 = x_jugador1 + 1;
	        		nivel[y_jugador1][x_jugador1] = 10;
	        		}
	            break;
	            
	        // MUSICA ON/OFF
	        case KeyEvent.VK_M:
	        	if (music_on == true) {
	        		music_on = false;
	        		musiquita_sound.stop();
	        	}
	        	else {
	        		music_on = true;
	        		musiquita_sound.loop();
	        	}      
	     }
	}

	@Override
	public void keyReleased(KeyEvent arg0) { }
	@Override
	public void keyTyped(KeyEvent arg0) { }
	
	
	
}
