package cliente;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import common.Clave;
import common.Message;
import server.Connection;



public class LoginScreen extends JFrame {

	private static final long serialVersionUID = -560582234414629430L;
	private static Integer  KEY_LOGIN = 0;
	private static Integer  KEY_LOGIN_VALIDACION_USER_PASS = 2;
	private static Integer KEY_JUEGO_FIN_JUEGO = 3;
	private static Integer KEY_LOGIN_REGISTRAR_USAURIO = 4;
	private static Integer KEY_LOGIN_REGISTRAR_MODIFICAR = 5;
	private static Integer KEY_LOGIN_AGREGAR_JUGADOR = 6;

	
	private JLabel     label_nada    = new JLabel("LOGIN FOR PLAY ...");
	
	private JLabel     label_user    = new JLabel("Usuario");
	private JTextField text_user     = new JTextField(15);

	private JLabel     label_pass    = new JLabel("Password");
	private JPasswordField text_pass     = new JPasswordField(15);
	
	private JLabel     label_nuser   = new JLabel("Nuevo Usuario");
	private JTextField text_nuser    = new JTextField(15);
	
	private JLabel     label_npass   = new JLabel("Nuevo Password");
	private JPasswordField text_npass    = new JPasswordField(15);
	
	private JButton    boton_iniciar = new JButton("Iniciar sesion");
	private JButton    boton_update  = new JButton("Modificar user y pass");
	private JButton    boton_registrarse  = new JButton("Registrarse");
	private JButton    boton_jugar   = new JButton("JUGAR!!");
	
	private JLabel     label_conectando       = new JLabel("");
	
	
	private JLabel     label_abajo  = new JLabel("");
	
	//private JLabel     label_user_registrado  = new JLabel("");
	//private JLabel     label_user_erroneo     = new JLabel("");
	//private JLabel     label_user_erroneo_update  = new JLabel("");
	//private JLabel     label_partida_en_juego  = new JLabel("");




	private Image imagenFondo;
	private String musica_path = "sonidos/";
	private AudioClip sonido_usernew;
	private AudioClip sonido_clic_ok;
	private AudioClip sonido_clic_nook;
	private boolean validaUser = false;
	private Integer cantConectados = 0;

	
	//constructor
	public LoginScreen(Connection connection) {
		
		setIconImage( new ImageIcon("imagenes/dig_dug_dragon.jpg").getImage() ); //iconito de la ventana
	    setSize(900,700);
		setTitle("Dig Dug 3");
	    setBounds(0, 0, 900, 700);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
	    //cargo sonidos
	    try{
         	sonido_usernew = Applet.newAudioClip( new URL("file:" + musica_path + "result_screen_beep.wav") );
         	sonido_clic_ok = Applet.newAudioClip( new URL("file:" + musica_path + "boss_fygar_firebreath.wav") );
         	sonido_clic_nook = Applet.newAudioClip( new URL("file:" + musica_path + "boss_fygar_firebreath.wav") );
           }
        catch (IOException e2) { }
	    
		//carga imagen de fondo en un JPanel: JPanelFondo
	    JPanelFondo jpf = new JPanelFondo();
		jpf.setBounds(0, 0, 900, 700);
		jpf.setSize(900, 700);
		jpf.setBackground("imagenes/Dig-Dug-pantalla2.png");
		add(jpf);


		CustomFont cft = new CustomFont("Gameplay.ttf");
		label_nada.setFont(cft.MyFont(0,20));
		label_nada.setForeground(Color.white);
		
		CustomFont cf = new CustomFont("Early_GameBoy.ttf");
		label_user.setFont(cf.MyFont(1,14));
		label_pass.setFont(cf.MyFont(1,14));
		label_nuser.setFont(cf.MyFont(1,14));
		label_npass.setFont(cf.MyFont(1,14));

		label_conectando.setFont(cf.MyFont(0,11));
		label_abajo.setFont(cf.MyFont(0,11));
		
		//label_user_registrado.setFont(cf.MyFont(0,11));
		//label_user_erroneo.setFont(cf.MyFont(0,11));
		//label_user_erroneo_update.setFont(cf.MyFont(0,11));
		//label_partida_en_juego.setFont(cf.MyFont(0,11));
		
		label_user.setForeground(Color.white);
		label_pass.setForeground(Color.white);
		label_pass.setForeground(Color.white);
		text_user.setToolTipText("Ingrese usuario");
		text_pass.setToolTipText("Ingrese password");
		
		label_nuser.setForeground(Color.white);
		label_npass.setForeground(Color.white);
		text_nuser.setToolTipText("Ingrese nuevo usuario");
		text_npass.setToolTipText("Ingrese nuevo password");

		label_conectando.setForeground(Color.WHITE);
		
		label_abajo.setFont(cf.MyFont(0,11));
		label_abajo.setForeground(Color.WHITE);
		//label_user_registrado.setForeground(Color.WHITE);
		//label_user_erroneo.setForeground(Color.WHITE);
		//label_user_erroneo_update.setForeground(Color.WHITE);
		//label_partida_en_juego.setForeground(Color.WHITE);
		
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
			label_conectando.setText("Users conectados: "+mensaje.getCantidadDeUsuarios());
			
			
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
								label_conectando.setText("Conectados: " + mensaje.getCantidadDeUsuarios());
								
								validaUser = mensaje.isAceptado();
								cantConectados = mensaje.getCantidadDeUsuarios();
								
								if(validaUser){
									label_abajo.setText("");
									//label_user_erroneo.setVisible(false);
									//label_user_erroneo.setText("");
									
									sonido_usernew.play();
									
									//System.out.println("SOY EL CLIENTE ACEPTADO CON NOMBRE:"+ mensaje.getName());
									//System.out.println("SOY EL CLIENTE ACEPTADO ID DE BASE DE DATOS:"+ mensaje.getIdUser());
									//System.out.println("SOY EL CLIENTE ACEPTADO ID DE POSICION DE ENTRADA:"+ mensaje.getUserIdPosicionDeEntrada());
	
									//habilito texts y boton update
									label_conectando.setText("Conectados: " + mensaje.getCantidadDeUsuarios());
									
							        label_nuser.setForeground(Color.white);
									label_nuser.setVisible(true);
									
									text_nuser.setEnabled(true);
							        text_nuser.setBackground(Color.white);
									text_nuser.setVisible(true);
									

									label_npass.setForeground(Color.white);
									label_npass.setVisible(true);
									
									text_npass.setEnabled(true);
							        text_npass.setBackground(Color.white);
									text_npass.setVisible(true);
							        
									
									boton_update.setEnabled(true);
									boton_update.setVisible(true);
									
									text_user.setEditable(false);
									text_pass.setEditable(false);
									text_user.setBackground(Color.lightGray);
									text_pass.setBackground(Color.lightGray);
									
									//deshbilito botones xq ya hay un usuario logineado
									boton_registrarse.setEnabled(false);
									boton_registrarse.setVisible(false);
							        label_conectando.setVisible(true);
									
									//label_user_registrado.setVisible(false);
									boton_iniciar.setEnabled(true);
									boton_iniciar.setText("Actualizar conectados");
									boton_iniciar.setToolTipText("Actualiza los usuarios conectados..");
									
									//if cantidad de usuarios es mayor a 1, true, pero no funciona para el primero conectado
									//if( mensaje.getCantidadDeUsuarios() > 1 ){
									  boton_jugar.setEnabled(true);
									  boton_jugar.setVisible(true);
									//}
								}else{
									label_abajo.setText("User y/o Pass no valido(s).");
									label_abajo.setVisible(true);
									//label_user_registrado.setVisible(false);
									//label_user_erroneo.setVisible(true);
									//label_user_erroneo.setText("User o pass invalida..");
									
							         	sonido_clic_nook.play();
									
								}
							}else{
								label_abajo.setText("Debe ingresar User y Pass para iniciar.");
								label_abajo.setVisible(true);
								//label_user_registrado.setVisible(false);
								//label_user_erroneo.setVisible(true);
								//label_user_erroneo.setText("Tiene que ingresar user y pass");
								
						         	sonido_clic_nook.play();
						           
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
					//MODIFICACION
						try {
							label_abajo.setText("");
							if(!text_nuser.getText().isEmpty() && !text_npass.getText().isEmpty()){
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
								mensaje.setLocacion(Clave.getNewInstancia(KEY_LOGIN_REGISTRAR_MODIFICAR));	
								mensaje.setName(text_user.getText());
								mensaje.setPass(text_pass.getText());
								mensaje.setNname(text_nuser.getText());
								mensaje.setNpass(text_npass.getText());
								obstrm.writeObject(mensaje);

								//recibe la info si se modico bien o no
								instrem = new ObjectInputStream(connection.getSocket().getInputStream());
								mensaje =(Message) instrem.readObject();
								if(mensaje.isAceptadoModificado()){
									label_abajo.setText("Datos actualizados.");
									label_abajo.setVisible(true);
									//label_user_erroneo_update.setVisible(true);	
									//label_user_erroneo_update.setText("User modificado");
									
									sonido_clic_ok.play();
							         									
								}
							
							}else{
								label_abajo.setText("Debe ingresar los nuevos User y Pass.");
								label_abajo.setVisible(true);
								//label_user_erroneo_update.setVisible(true);	
								//label_user_erroneo_update.setText("Ingresar user y pass");
								
								sonido_clic_nook.play();
						        
								
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
					
				});

		 boton_jugar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean agregar = false;
						try {
							label_abajo.setText("");
							ObjectOutputStream obstrm;
							ObjectInputStream  instrem;
							Message mensaje;
							//recibe.
							//esto para lo unico que es necesario.. es por que el juego (el mapa) tiene que recibir el mensaje del mapa...
							//capas se puede mejorar .. pero tenes que tocar todas las llamadas al server			
							instrem = new ObjectInputStream(connection.getSocket().getInputStream());
							mensaje =(Message) instrem.readObject();
							
							//manda donde esta osea logien
							obstrm = new ObjectOutputStream(connection.getSocket().getOutputStream());
							mensaje.setLocacion(Clave.getNewInstancia(KEY_LOGIN_AGREGAR_JUGADOR));				
							obstrm.writeObject(mensaje);
	
							//recibe la info de cantidad
							instrem = new ObjectInputStream(connection.getSocket().getInputStream());
							mensaje =(Message) instrem.readObject();
							agregar = mensaje.isAgregarJugador();
							
							if(!agregar){
								label_abajo.setText("Partida llena.");
								//label_partida_en_juego.setText("Partida llena");
								
								sonido_clic_nook.play();
								
							}else{
								label_abajo.setText("");
								//label_partida_en_juego.setText("");
								
							}
							
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						
						if(validaUser && cantConectados>1 && agregar){
							Juego pe = new Juego();
							boton_jugar.setEnabled(false);
							pe.inGame(connection);
						}
					}
				});
		 
		 boton_registrarse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						label_abajo.setText("");
						//label_user_erroneo.setText("");
						//label_user_erroneo.setVisible(false);
						
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
								label_abajo.setText("User registrado OK.");
								//label_user_registrado.setText("User registrado OK..");
								
								sonido_clic_ok.play();
								
							}
						}else{
							label_abajo.setText("Debe ingresar User y Pass.");
							//label_user_registrado.setText("Tiene que ingresar user y pass");
							
							sonido_clic_nook.play();
					           
							
						}
						//label_user_registrado.setVisible(true);
						label_abajo.setText("Usuario existente, try again...");
						
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
		
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		constraints.insets = new Insets(5, 5, 5, 5);
        //creo q es asi: (espacio desde arriba, espacio desde la izq ancho, espacio vertical entre lineas, espacio horiz entre cosos);

        constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.gridwidth = 1; //ancho
		constraints.gridheight = 2; //alto
		constraints.insets=new Insets(30,5,5,5);
		jp.add(label_nada, constraints);
		constraints.insets = new Insets(5, 5, 5, 5);
        
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.gridwidth = 1; //ancho
		constraints.gridheight = 1; //alto
	    jp.add(label_user, constraints);
	    constraints.gridx = 1;
	    jp.add(text_user, constraints);

	    constraints.gridy = 3; 
	    constraints.gridx = 0; 
		constraints.gridwidth = 1; //ancho
		constraints.gridheight = 1; //alto
	    jp.add(label_pass, constraints);
	    constraints.gridx = 1;
	    jp.add(text_pass, constraints);

	    

        constraints.gridy = 4;
        constraints.gridx = 0;
		constraints.gridwidth = 1; //ancho
		constraints.gridheight = 1; //alto
	    //constraints.anchor = GridBagConstraints.WEST;
        //constraints.fill = GridBagConstraints.WEST;
        jp.add(boton_registrarse, constraints);
        
        
        //constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 4;
	    constraints.gridx = 1;
		constraints.gridwidth = 1; //ancho
		constraints.gridheight = 1; //alto
        constraints.anchor = GridBagConstraints.EAST;
        jp.add(boton_iniciar, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 4;
		constraints.gridwidth = 1; //ancho
		constraints.gridheight = 1; //alto
        jp.add(label_conectando, constraints);
        label_conectando.setVisible(false);
        
        
        
        constraints.gridy = 5;
        constraints.gridx = 0;
		constraints.gridwidth = 1; //ancho
		constraints.gridheight = 1; //alto
        jp.add(label_nuser, constraints);
	    constraints.gridx = 1;
	    jp.add(text_nuser, constraints);
	    
	    constraints.gridy = 6;
        constraints.gridx = 0;
		constraints.gridwidth = 1; //ancho
		constraints.gridheight = 1; //alto
        jp.add(label_npass, constraints);
	    constraints.gridx = 1;
	    jp.add(text_npass, constraints);
	    

        constraints.gridy = 7;
        constraints.gridx = 1;
		constraints.gridwidth = 1; //ancho
		constraints.gridheight = 1; //alto
        jp.add(boton_update, constraints);
	    
        constraints.gridy = 8;
        constraints.gridx = 1;
		constraints.gridwidth = 1; //ancho
		constraints.gridheight = 1; //alto
        jp.add(boton_jugar, constraints);
        
        /*
        constraints.gridy = 9;
   	    constraints.gridx = 0;
        jp.add(label_user_registrado, constraints);
        
        constraints.gridy = 10;
   	    constraints.gridx = 0;
        jp.add(label_user_erroneo, constraints);
        
        constraints.gridy = 11;
   	    constraints.gridx = 0;
        jp.add(label_user_erroneo_update, constraints);
        
        constraints.gridy = 12;
   	    constraints.gridx = 0;
        jp.add(label_partida_en_juego, constraints);
        */

        constraints.gridy = 9;
   	    constraints.gridx = 0;
		constraints.gridwidth = 2; //ancho
		constraints.gridheight = 1; //alto
        constraints.fill = GridBagConstraints.WEST;
	    constraints.anchor = GridBagConstraints.WEST;
        jp.add(label_abajo, constraints);
        
        
        
        

	    //constraints.gridx = 0;
        //constraints.gridy = 6;
        //constraints.fill = GridBagConstraints.HORIZONTAL;
	    //constraints.anchor = GridBagConstraints.HORIZONTAL;
        //jp.add(label_, constraints);
	    
	    
        
        //desabilitado, en gris
        label_nuser.setVisible(false);
	    text_nuser.setEnabled(false);
	    text_nuser.setVisible(false);
       
        label_npass.setVisible(false);
	    text_npass.setEnabled(false);
	    text_npass.setVisible(false);
	    
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
        
	    setSize(899,699);
	    setSize(900,700);
        setLocationRelativeTo(null);
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
	    g.drawImage(imagenFondo, 0, 0, 900, 700, this);
	  }
	
	
	
		
	
	

}// class
