package walterServerCliente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import walterServerCliente.Juego;


public class LoginScreen extends JFrame {

	private static final long serialVersionUID = -560582234414629430L;
	private JPanel contentPane;
	private JTextField jUserTextField;
	private JPasswordField jPasswordField;
	private  JTextArea jUsuarioInexistenteTextArea;
	private  JTextArea jCamposVaciosTextArea;
	private JTextArea jUsuarioLogueadoTextArea;
	private static String title;
	
	private static Image img;
	
	
	
	public LoginScreen(){
	//setBackground(Color.black);

			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setBounds(100, 100, 800, 500);
			setLocationRelativeTo(null);
			setResizable(true);
			contentPane = new JPanel();
			
			

			
	 		//contentPane.setBackground(Color.LIGHT_GRAY);//new Color(255, 153, 0));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			JLabel lblUsername = new JLabel("Usuario");
			lblUsername.setBounds(31, 170, 100, 14);
			contentPane.add(lblUsername);

			JLabel lblPassword = new JLabel("Contrase\u00F1a");
			lblPassword.setBounds(31, 218, 74, 14);
			contentPane.add(lblPassword);

			jUserTextField = new JTextField();
			jUserTextField.setBounds(136, 166, 86, 23);
			contentPane.add(jUserTextField);
			jUserTextField.setColumns(10);

			/*final JTextArea*/ jCamposVaciosTextArea = new JTextArea();
			jCamposVaciosTextArea.setEditable(false);
			jCamposVaciosTextArea.setBackground(new Color(255, 153, 0));
			jCamposVaciosTextArea.setLineWrap(true);
			jCamposVaciosTextArea.setText("Alguno de los dos campos est\u00E1 vac\u00EDo, por favor completelos.");
			jCamposVaciosTextArea.setBounds(32, 367, 208, 67);
			jCamposVaciosTextArea.setVisible(false);
			contentPane.add(jCamposVaciosTextArea);
			
			/*final JTextArea*/ jUsuarioInexistenteTextArea = new JTextArea();
			jUsuarioInexistenteTextArea.setBackground(new Color(255, 153, 0));
			jUsuarioInexistenteTextArea.setLineWrap(true);
			jUsuarioInexistenteTextArea.setEditable(false);
			jUsuarioInexistenteTextArea.setVisible(false);
			jUsuarioInexistenteTextArea.setText("Los datos ingresados no se encuentran en la base de datos.");
			jUsuarioInexistenteTextArea.setBounds(31, 367, 240, 74);
			contentPane.add(jUsuarioInexistenteTextArea);

			JButton jLoginButton = new JButton("Entrar");

			jLoginButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Juego pe = new Juego();
					pe.inGame();
				}
			});
			
			//jLoginButton.addActionListener(new ActionLogin(jUsuarioInexistenteTextArea, jCamposVaciosTextArea));
			jLoginButton.setBounds(84, 274, 89, 23);
			contentPane.add(jLoginButton);

			jPasswordField = new JPasswordField();
			

			jPasswordField.setBounds(136, 215, 86, 20);
			contentPane.add(jPasswordField);

			JButton jSalirButton = new JButton("Salir");

			jSalirButton.setBounds(84, 322, 89, 23);
			contentPane.add(jSalirButton);
			
			JLabel lblLogoPreguntados = new JLabel(new ImageIcon("Logo_Preguntados3.png"));
			lblLogoPreguntados.setBounds(52, 0, 170, 160);
			contentPane.add(lblLogoPreguntados);
			
			jUsuarioLogueadoTextArea = new JTextArea();
			jUsuarioLogueadoTextArea.setEditable(false);
			jUsuarioLogueadoTextArea.setBackground(new Color(255, 153, 0));
			jUsuarioLogueadoTextArea.setVisible(false);
			jUsuarioLogueadoTextArea.setText("El usuario ya est\u00E1 logueado.");
			jUsuarioLogueadoTextArea.setBounds(38, 381, 246, 35);
			contentPane.add(jUsuarioLogueadoTextArea);
			
		}
		
	
	
	 public void paintComponent(Graphics g) {
		    g.drawImage(img, 30, 30, null);
		  }
	 
	
	public static void main(String argv[]) {
		LoginScreen log = new LoginScreen();
		
		log.addWindowListener(new WindowAdapter() {
	            public void windowClosing(WindowEvent e) {System.exit(0);}
	        });
		
		
		ImageIcon icon = new ImageIcon("imagenes/dig-dug-japan.png"); 
		JLabel thumb = new JLabel();
		
		img = ((ImageIcon)icon).getImage();
		log.add(thumb);
		
		
		/*try {
			JPanelWithBackground jp = new JPanelWithBackground("imagenes/dig-dug-japan.png");
			log.add(jp);
			//log.getContentPane().add(jp);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		log.setBounds(300, 300, 400, 400);
		log.show();
	}
	
	public static String getTitleGame(){
		return title;
	}
	
	
	
	
}

