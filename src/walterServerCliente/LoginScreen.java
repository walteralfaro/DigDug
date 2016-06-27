package walterServerCliente;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class LoginScreen extends JFrame {

	private static final long serialVersionUID = -560582234414629430L;
	private static Integer  KEY_LOGIN = 0;
	private static Integer  KEY_LOGIN_VALIDACION_USER_PASS = 2;
	private static Integer KEY_JUEGO_FIN_JUEGO = 3;
	private static Integer KEY_LOGIN_REGISTRAR_USAURIO = 4;

	
	private JLabel     label_nada    = new JLabel();
	
	private JLabel     label_user    = new JLabel("Usuario");
	private JTextField text_user     = new JTextField(15);

	private JLabel     label_pass    = new JLabel("Password");
	private JTextField text_pass     = new JTextField(15);
	
	private JLabel     label_nuser   = new JLabel("Nuevo Usuario");
	private JTextField text_nuser    = new JTextField(15);
	
	private JLabel     label_npass   = new JLabel("Nuevo Password");
	private JTextField text_npass    = new JTextField(15);
	
	private JButton    boton_iniciar = new JButton("Iniciar sesion");
	private JButton    boton_update  = new JButton("Modificar user y pass");
	private JButton    boton_registrarse  = new JButton("Registrarse");
	private JButton    boton_jugar   = new JButton("JUGAR!!");
	
	private JLabel     label_conectando       = new JLabel("LOGIN FOR PLAY...");
	private JLabel     label_user_registrado  = new JLabel("");
	private JLabel     label_user_erroneo     = new JLabel("");


	private Image imagenFondo;
	private String musica_path = "musica/";
	private AudioClip sonido_usernew;
	private boolean validaUser = false;
	private Integer cantConectados = 0;

	
	//constructor
	public LoginScreen(Connection connection) {
		
		setIconImage( new ImageIcon("imagenes/dig_dug_dragon.jpg").getImage() ); //iconito de la ventana
	    setSize(580,700);
		setTitle("Dig Dug");
	    setBounds(0, 0, 580, 700);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
		//carga imagen de fondo en un JPanel: JPanelFondo
	    JPanelFondo jpf = new JPanelFondo();
		jpf.setBackground("imagenes/33.png");
		add(jpf);

		label_user.setForeground(Color.white);
		label_pass.setForeground(Color.white);
		text_user.setToolTipText("Ingrese usuario");
		text_pass.setToolTipText("Ingrese password");
		
		label_nuser.setForeground(Color.white);
		label_npass.setForeground(Color.white);
		text_nuser.setToolTipText("Ingrese nuevo usuario");
		text_npass.setToolTipText("Ingrese nuevo password");

		label_conectando.setForeground(Color.WHITE);
		label_user_registrado.setForeground(Color.WHITE);
		label_user_erroneo.setForeground(Color.WHITE);
		
		ObjectOutputStream obstrm;
		ObjectInputStream  instrem;
		Message mensaje;

		try {
			//recibe.
			//esto para lo unico que es necesario.. es por que el juego (el mapa) tiene que recibir el mensaje del mapa...
			//capas se puede mejorar .. pero tenes que tocar todas las llamadas al server			
			instrem = new ObjectInputStream(connection.getSocket().getInputStream());
			mensaje =(Message) instrem.readObject();
			
			//manda donde esta osea logien
			obstrm = new ObjectOutputStream(connection.getSocket().getOutputStream());
			mensaje.setLocacion(Clave.getNewInstancia(KEY_LOGIN));				
			obstrm.writeObject(mensaje);

			//recibe la info de cantidad
			instrem = new ObjectInputStream(connection.getSocket().getInputStream());
			mensaje =(Message) instrem.readObject();
			label_conectando.getText();
			label_conectando.setText("Usuarios conectados: "+mensaje.getCantidadDeUsuarios());
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		 boton_iniciar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						try {
							if(!text_user.getText().isEmpty() && !text_pass.getText().isEmpty()){
								ObjectOutputStream obstrm;
								ObjectInputStream  instrem;
								Message mensaje;
								//recibe
								//esto para lo unico que es necesario.. es por que el juego (el mapa) tiene que recibir el mensaje del mapa...
								//capas se puede mejorar .. pero tenes que tocar todas las llamadas al server
								instrem = new ObjectInputStream(connection.getSocket().getInputStream());
								mensaje =(Message) instrem.readObject();
								
								//manda donde esta osea logien
								obstrm = new ObjectOutputStream(connection.getSocket().getOutputStream());
								mensaje.setLocacion(Clave.getNewInstancia(KEY_LOGIN_VALIDACION_USER_PASS));	
								mensaje.setName(text_user.getText());
								mensaje.setPass(text_pass.getText());
								obstrm.writeObject(mensaje);
								
								//recibe la info de cantidad
								instrem = new ObjectInputStream(connection.getSocket().getInputStream());
								mensaje =(Message) instrem.readObject();
								label_conectando.getText();
								label_conectando.setText("Usuarios conectados: " + mensaje.getCantidadDeUsuarios());
								
								validaUser = mensaje.isAceptado();
								cantConectados = mensaje.getCantidadDeUsuarios();
								
								if(validaUser){
									label_user_erroneo.setVisible(false);
									label_user_erroneo.setText("");
									try{
							         	sonido_usernew = Applet.newAudioClip( new URL("file:" + musica_path + "extra_life.wav") );
							         	sonido_usernew.play();
							           }
							         catch (IOException e2) { }
									
									System.out.println("SOY EL CLIENTE ACEPTADO CON NOMBRE:"+ mensaje.getName());
									System.out.println("SOY EL CLIENTE ACEPTADO ID DE BASE DE DATOS:"+ mensaje.getIdUser());
									System.out.println("SOY EL CLIENTE ACEPTADO ID DE POSICION DE ENTRADA:"+ mensaje.getUserIdPosicionDeEntrada());
	
									//habilito texts y boton update
									label_conectando.setText("Usuarios conectados: " + mensaje.getCantidadDeUsuarios());
									
									text_nuser.setEnabled(true);
									label_nuser.setVisible(true);
									text_nuser.setVisible(true);
							        text_nuser.setBackground(Color.WHITE);
							        
									text_npass.setEnabled(true);
									label_npass.setVisible(true);
									text_npass.setVisible(true);
							        text_npass.setBackground(Color.WHITE);
							        
									boton_update.setEnabled(true);
									boton_update.setVisible(true);
									
									text_user.setEditable(false);
									text_pass.setEditable(false);
									text_user.setBackground(Color.LIGHT_GRAY);
									text_pass.setBackground(Color.LIGHT_GRAY);
									
									//deshbilito botones xq ya hay un usuario logineado
									boton_registrarse.setEnabled(false);
									boton_registrarse.setVisible(false);
									label_user_registrado.setVisible(false);
									boton_iniciar.setEnabled(true);
									boton_iniciar.setText("Actualizar conectados..");
									boton_iniciar.setToolTipText("Actualiza los usuarios conectados..");
									
									//if cantidad de usuarios es mayor a 1, true, pero no funciona para el primero conectado
									//if( mensaje.getCantidadDeUsuarios() > 1 ){
									  boton_jugar.setEnabled(true);
									  boton_jugar.setVisible(true);
									//}
								}else{
									label_user_registrado.setVisible(false);
									label_user_erroneo.setVisible(true);
									label_user_erroneo.setText("User o pass invalida..");
								}
							}else{
								label_user_registrado.setVisible(false);
								label_user_erroneo.setVisible(true);
								label_user_erroneo.setText("Tiene que ingresar user y pass");
							}
						
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				});
		
		 boton_update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
			        //System.exit(0);
					}
				});

		 boton_jugar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						if(validaUser && cantConectados>1){
							Juego pe = new Juego();
							pe.inGame(connection);
						}
					}
				});
		 
		 boton_registrarse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						label_user_erroneo.setText("");
						label_user_erroneo.setVisible(false);
						if(!text_user.getText().isEmpty() && !text_pass.getText().isEmpty()){
							ObjectOutputStream obstrm;
							ObjectInputStream  instrem;
							Message mensaje;
							//recibe
							//esto para lo unico que es necesario.. es por que el juego (el mapa) tiene que recibir el mensaje del mapa...
							//capas se puede mejorar .. pero tenes que tocar todas las llamadas al server
		
							instrem = new ObjectInputStream(connection.getSocket().getInputStream());
							mensaje =(Message) instrem.readObject();
							
							//manda donde esta osea logien
							obstrm = new ObjectOutputStream(connection.getSocket().getOutputStream());
							mensaje.setLocacion(Clave.getNewInstancia(KEY_LOGIN_REGISTRAR_USAURIO));	
							mensaje.setName(text_user.getText());
							mensaje.setPass(text_pass.getText());
							obstrm.writeObject(mensaje);
							
							//recibe la info si se registro bien o no
							instrem = new ObjectInputStream(connection.getSocket().getInputStream());
							mensaje =(Message) instrem.readObject();
							mensaje.isAceptadoRegistrado();

							if(mensaje.isAceptadoRegistrado()){
								label_user_registrado.setText("User registrado OK..");
							}
						}else{
							label_user_registrado.setText("Tiene que ingresar user y pass");
						}
						label_user_registrado.setVisible(true);

					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			});
		 
		// create a new panel with GridBagLayout manager
		JPanel jp = new JPanel( new GridBagLayout() );
		
		
		// add components to the panel
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(5, 5, 5, 5);
        //creo q es asi: (espacio desde arriba, espacio desde la izq ancho, espacio vertical entre lineas, espacio horiz entre cosos);

        constraints.gridy = 0;
		constraints.gridx = 0;
		jp.add(label_nada, constraints);		
		
        constraints.gridy = 1;
        
		constraints.gridx = 0;
	    jp.add(label_user, constraints);
	    constraints.gridx = 1;
	    jp.add(text_user, constraints);

        constraints.gridy = 2; 
        
	    constraints.gridx = 0; 
	    jp.add(label_pass, constraints);
	    constraints.gridx = 1;
	    jp.add(text_pass, constraints);

        constraints.gridy = 4;
        
        constraints.gridx = 0;
        jp.add(label_nuser, constraints);
	    constraints.gridx = 1;
	    jp.add(text_nuser, constraints);
	    
	    constraints.gridy = 5;
        
        constraints.gridx = 0;
        jp.add(label_npass, constraints);
	    constraints.gridx = 1;
	    jp.add(text_npass, constraints);
	    
        constraints.gridy = 7;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.EAST;
        jp.add(boton_jugar, constraints);
        
        constraints.gridy = 8;
	    constraints.gridx = 0;
        jp.add(label_conectando, constraints);
        
        constraints.gridy = 9;
   	    constraints.gridx = 0;
        jp.add(label_user_registrado, constraints);
        
        constraints.gridy = 10;
   	    constraints.gridx = 0;
        jp.add(label_user_erroneo, constraints);
        
        
        constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.gridx = 1;
        constraints.gridy = 3;
        //constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.EAST;
        jp.add(boton_iniciar, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;
	    constraints.anchor = GridBagConstraints.WEST;
        jp.add(boton_registrarse, constraints);

	    constraints.gridx = 1;
        constraints.gridy = 6;
	    constraints.anchor = GridBagConstraints.EAST;
        jp.add(boton_update, constraints);
	    
        
        //desabilitado, en gris
        label_nuser.setEnabled(false);
        label_nuser.setVisible(false);
	    text_nuser.setEnabled(false);
	    text_nuser.setVisible(false);
        text_nuser.setBackground(Color.GRAY);
        
        label_npass.setEnabled(false);
        label_npass.setVisible(false);
	    text_npass.setEnabled(false);
	    text_npass.setVisible(false);
        text_npass.setBackground(Color.GRAY);
	    
        boton_update.setEnabled(false);
        boton_update.setVisible(false);
        boton_jugar.setEnabled(false);
        boton_jugar.setVisible(false);
        
     // set border for the panel
        jp.setBorder( BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null) );
        //jp.setBackground(Color.BLACK); 
        jp.setOpaque(false);
        
        jpf.add(jp);
        
        
        pack();
        setLocationRelativeTo(null);
        
	    setSize(579,699);
	    setSize(580,700);

	    setVisible(true);
	    
	    this.addWindowListener(new WindowAdapter() {
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
        			e1.printStackTrace();
        		} catch (ClassNotFoundException e1) {
        			e1.printStackTrace();
        		}
				System.exit(0);}
			});
	    
	}//constructor Login()
	
	
	
	public void paintComponent(Graphics g) {
	    paintComponent(g);
	    // Draw the background image.
	    g.drawImage(imagenFondo, 0, 0, this);
	  }
	
	
	
		
	
	

}// class
