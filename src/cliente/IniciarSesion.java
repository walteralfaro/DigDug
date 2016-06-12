package cliente;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IniciarSesion extends JPanel implements ActionListener{

	private static int ANCHO_VENTANA = 550;
	private static int ALTO_VENTANA = 300;
	
	JButton boton1;
	TextField jTextField1;
	
	public void init() {
		boton1 = new JButton("Iniciar sesion");
		boton1.setBounds(30,60,10,30);
        add(boton1);
        boton1.addActionListener(this);
        
        jTextField1 = new TextField();
        jTextField1.setBounds(400,400,100,400);
        add(jTextField1);
        //String user = jTextField1.getText();        
        
        
	}
	
	public void actionPerformed(ActionEvent e) {
        if (e.getSource()==boton1) {
            System.exit(0);
        }
    }
	
	//public void paintComponent(Graphics g){
		//ImageIcon imagenFondo = new ImageIcon( getClass().getResource("imagenes/fld_09.png") );
		//g.drawImage(imagenFondo.getImage(),10,10,200,250,null);
		//setOpaque(false);
		//super.paintComponent(g);
	//}
	 
	
	public static void main(String[] args){
		
		Frame f = new Frame("Iniciar sesi�n");
		
		final IniciarSesion i = new IniciarSesion();
		i.init();
		i.setBounds(0, 0, 5000, 5000);
		
		f.add(i);
		
		f.pack();
		f.setSize( new Dimension(ANCHO_VENTANA, ALTO_VENTANA) );
		f.setIconImage( new ImageIcon("./imagenes/dig_dug_dragon.jpg").getImage() ); //iconito de la ventana
		f.setBounds(0,0,450,350);
		f.setVisible(true);
		//f.setResizable(false);
		//f.show();
	}//main

}//class
