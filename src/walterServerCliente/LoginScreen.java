package walterServerCliente;

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
	private JButton    boton_update  = new JButton("Actualizar datos");
	private JButton    boton_registrarse  = new JButton("Registrarse");
	
	private JLabel     label_conectando    = new JLabel("LOGIN FOR PLAY...");
	
	private Image imagenFondo;
	
	
	
	//constructor
	public LoginScreen(Connection connection) {
		
		setIconImage( new ImageIcon("imagenes/dig_dug_dragon.jpg").getImage() ); //iconito de la ventana
	    setSize(580,700);
		setTitle("Dig Dug");
	    setBounds(0, 0, 580, 700);
	    //setResizable(false);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
		//carga imagen de fondo en un JPanel: JPanelFondo
	    JPanelFondo jpf = new JPanelFondo();
		jpf.setBackground("imagenes/dig-dug-japan-copia.png");
		add(jpf);

		label_user.setForeground(Color.white);
		label_pass.setForeground(Color.white);
		label_nuser.setForeground(Color.white);
		label_npass.setForeground(Color.white);
		label_conectando.setForeground(Color.WHITE);
		
		ObjectOutputStream obstrm;
		ObjectInputStream  instrem;
		Message mensaje;
		Integer cantidad = 0;

		String esperando = "LOGIN FOR PLAY.. ";

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
			label_conectando.setText(esperando +" -> "+mensaje.getCantidadDeUsuarios());
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		
		 boton_iniciar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						try {
							ObjectOutputStream obstrm;
							ObjectInputStream  instrem;
							Message mensaje;
						
							JuegoDaoImp n = new JuegoDaoImp(); //esto no va no?
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
							label_conectando.setText(esperando +" -> "+mensaje.getCantidadDeUsuarios());
							

							if(mensaje.isAceptado()){
								System.out.println("SOY EL CLIENTE ACEPTADO CON NOMBRE:"+ mensaje.getName());
								System.out.println("SOY EL CLIENTE ACEPTADO ID DE BASE DE DATOS:"+ mensaje.getIdUser());
								System.out.println("SOY EL CLIENTE ACEPTADO ID DE POSICION DE ENTRADA:"+ mensaje.getUserIdPosicionDeEntrada());

								//habilito texts y boton update
								label_conectando.setText("Usuarios conectados: " + mensaje.getCantidadDeUsuarios());
								label_nuser.setEnabled(true);
								label_npass.setEnabled(true);
								boton_update.setEnabled(true);
								
								//deshbilito registrarse xq ya hay un usuario logineado
								boton_registrarse.setEnabled(false);
								
								
								if(mensaje.getCantidadDeUsuarios()>1){
									Juego pe = new Juego();
									pe.inGame(connection);
								}
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
//			        EndClientConnectionPackage er = new EndClientConnectionPackage();
//			        connection.sendPackage(er);
			        System.exit(0);
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
        
	    constraints.gridx = 0;
        jp.add(label_conectando, constraints);
	    
        
        constraints.fill = GridBagConstraints.EAST;
        
	    constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        
        constraints.anchor = GridBagConstraints.EAST;
        jp.add(boton_iniciar, constraints);
        constraints.gridx = 0;
	    constraints.anchor = GridBagConstraints.WEST;
        jp.add(boton_registrarse, constraints);

	    constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
	    constraints.anchor = GridBagConstraints.EAST;
        jp.add(boton_update, constraints);
	    
        
        //desabilitado, en gris
        label_nuser.setEnabled(false);
	    text_nuser.setEnabled(false);
        label_npass.setEnabled(false);
	    text_npass.setEnabled(false);
	    boton_update.setEnabled(false);
        
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
			public void windowClosing(WindowEvent e) {System.exit(0);}
			});
	    
	}//constructor Login()
	
	
	
	public void paintComponent(Graphics g) {
	    paintComponent(g);
	    // Draw the background image.
	    g.drawImage(imagenFondo, 0, 0, this);
	  }
	
	
	
		
	
	

}// class
