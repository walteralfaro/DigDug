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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.Timer;


public class Juego extends JApplet implements Runnable, KeyListener {
	
	public Juego() {
		//ggg
	}
	
	
    public static int ALTO_VENTANA = 800;
    public static int ANCHO_VENTANA = 705;
    
    private int ALTO_NIVEL = 24;
    private int ANCHO_NIVEL = 23;
    
	private char nivel[][] = {
    		//1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23
			
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
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9}, //15
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9}, 
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9}, //20
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9}, //21
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9}, //22
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9}, //23
    		{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, //24
    };
	// 9 pared
	// 5 cielo pared
	// 6 cielo para subir y moverse
	// 0 espacios para moverse en negro
	// 1 2 3 4 tierra para excavar
	
	

	// VECTOR PARA CARGAR TODAS LAS IMAGENES
    private BufferedImage nivel_img[] = new BufferedImage[11];

    private BufferedImage bimg;

    private Image image;

    private Thread thread;
    
    private boolean music_on = true;
    
    AudioClip musiquita_sound;
    AudioClip eat_sound;
    AudioClip die_sound;
    
    
    
    
// POSICIONES INICIALES
    private int x_jugador = 3;
    private int y_jugador = 4;
    
    
    
    

    public void init() {
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
    	 
    	 
    	 
    	//Image image = ( new ImageIcon( "imagenes/monster1.png").getImage() );
    	
    	try {
    		nivel_img[0] = ImageIO.read(new File("imagenes/negro.png"));
    		nivel_img[1] = ImageIO.read(new File("imagenes/tierra1.png"));
    		nivel_img[2] = ImageIO.read(new File("imagenes/tierra2.png"));
    		nivel_img[3] = ImageIO.read(new File("imagenes/tierra3.png"));
    		nivel_img[4] = ImageIO.read(new File("imagenes/tierra4.png"));
    		
    		nivel_img[5] = ImageIO.read(new File("imagenes/cielo.png"));
    		nivel_img[6] = ImageIO.read(new File("imagenes/cielo.png"));
    		nivel_img[7] = ImageIO.read(new File("imagenes/negro.png"));
    		nivel_img[8] = ImageIO.read(new File("imagenes/negro.png"));
    		
    		nivel_img[9] = ImageIO.read(new File("imagenes/negro.png"));
    		
    		nivel_img[10] = ImageIO.read(new File("imagenes/dig_dug_sprites_1.gif"));

    	 } catch (IOException e) { }
    }//init()
	
    

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g; // Convertimos a g de Graphics a Graphics2D
        //g2d.drawImage(image, 0, 0, this);
        g2d.setStroke(new BasicStroke(5.0f));
        //drawNivel(30, 30, g2d);
        
        for (int x = 0; x <= ANCHO_NIVEL-1; x++) {      // 23 ANCHO de 0 a 22
        	for (int y = 0; y <= ALTO_NIVEL-1; y++) {   // 24 ALTO  de 0 a 23
        		g2d.drawImage(nivel_img[nivel[y][x]], x*30, y*30, null);
        		
        		g2d.drawImage(nivel_img[10], x_jugador*30, y_jugador*30, null);
        	}
        	
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }//paint()
    
    
    
    
    
    
    
    

    public void run() {
        Thread yo = Thread.currentThread();
       
        while (thread == yo) {
            repaint();
            try {
            	thread.sleep(10);
            } catch (InterruptedException e) { break; }
        }// while thread
         
        thread = null;
    }// run()
    
    
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
        //f.setResizable(false);
        f.setSize( new Dimension(ANCHO_VENTANA, ALTO_VENTANA) );
        f.setIconImage( new ImageIcon("./imagenes/dig_dug_dragon.jpg").getImage() ); //iconito de la ventana
        f.show();
        f.addKeyListener(ventanaJuego);

        ventanaJuego.start();
        //timer.start();
        
	} //main

	
	
	
	
	

	@Override
	public void keyPressed(KeyEvent e) {
	    int keyCode = e.getKeyCode();
	    
	    switch( keyCode ) { 
	    
	    	case KeyEvent.VK_UP:
	        	if ( (nivel[y_jugador-1][x_jugador] == 0) || (nivel[y_jugador-1][x_jugador] == 1) || (nivel[y_jugador-1][x_jugador] == 2) || (nivel[y_jugador-1][x_jugador] == 3) || (nivel[y_jugador-1][x_jugador] == 4) || (nivel[y_jugador-1][x_jugador] == 6) ) {
	        		nivel[y_jugador][x_jugador] = 0; //set en la matriz el lugar excavado
	        		y_jugador = y_jugador - 1; //set posicion movimiento arriba
	        	}
	            break;
	            
	        case KeyEvent.VK_DOWN:
	        	if ( (nivel[y_jugador+1][x_jugador] == 0) || (nivel[y_jugador+1][x_jugador] == 1) || (nivel[y_jugador+1][x_jugador] == 2) || (nivel[y_jugador+1][x_jugador] == 3) || (nivel[y_jugador+1][x_jugador] == 4) || (nivel[y_jugador+1][x_jugador] == 6) ) {
	        		nivel[y_jugador][x_jugador] = 0; //lugar excavado
	        		y_jugador = y_jugador + 1;
	        	}
	            break;
	            
	        case KeyEvent.VK_LEFT:
	        	if ( (nivel[y_jugador][x_jugador-1] == 0) || (nivel[y_jugador][x_jugador-1] == 1) || (nivel[y_jugador][x_jugador-1] == 2) || (nivel[y_jugador][x_jugador-1] == 3) || (nivel[y_jugador][x_jugador-1] == 4) || (nivel[y_jugador][x_jugador-1] == 6) ) {
	        		nivel[y_jugador][x_jugador] = 0; //lugar excavado
	        		x_jugador = x_jugador - 1;
	        	}
	            break;
	            
	        case KeyEvent.VK_RIGHT :
	        	if ( (nivel[y_jugador][x_jugador+1] == 0) || (nivel[y_jugador][x_jugador+1] == 1) || (nivel[y_jugador][x_jugador+1] == 2) || (nivel[y_jugador][x_jugador+1] == 3) || (nivel[y_jugador][x_jugador+1] == 4) || (nivel[y_jugador][x_jugador+1] == 6) ) {
	        		nivel[y_jugador][x_jugador] = 0; //lugar excavado
	        		x_jugador = x_jugador + 1;
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
	        	
	            
	     }// switch
	    
/*
	    // SCORE
	    // va sumando el SCORE, play al sonido de comer y borra las PAPITAS del laberinto
	    if (laberinto[y][x] == 1) {
	    	eat_sound.play();
	    	laberinto[y][x] = 0;
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
