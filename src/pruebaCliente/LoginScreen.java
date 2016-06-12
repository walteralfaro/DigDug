package pruebaCliente;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pruebaServer.EndClientConnectionPackage;
import pruebaServer.UserLoginPackage;



public class LoginScreen extends JFrame {

	public final class ActionLogin implements ActionListener {
		private  final JTextArea jUsuarioInexistenteTextArea;
		private final JTextArea jCamposVaciosTextArea;

		public ActionLogin(JTextArea jUsuarioInexistenteTextArea, JTextArea jCamposVaciosTextArea) {
			this.jUsuarioInexistenteTextArea = jUsuarioInexistenteTextArea;
			this.jCamposVaciosTextArea = jCamposVaciosTextArea;
		}

		public void actionPerformed(ActionEvent arg0) {

			if (jUserTextField.getText().isEmpty() || jPasswordField.getPassword().toString().isEmpty()){
				jUsuarioInexistenteTextArea.setVisible(false);
				jCamposVaciosTextArea.setVisible(true);					
			}
			else {
				UserLoginPackage loginrequest = new UserLoginPackage(jUserTextField.getText(), new String (jPasswordField.getPassword()));
				connection.sendPackage(loginrequest);
			}
		}
	}

	private static final long serialVersionUID = -560582234414629430L;
	private JPanel contentPane;
	private JTextField jUserTextField;
	private JPasswordField jPasswordField;
	private  JTextArea jUsuarioInexistenteTextArea;
	private  JTextArea jCamposVaciosTextArea;
	private JTextArea jUsuarioLogueadoTextArea;
	private Connection connection = Connection.getInstance();
	private static String title;


	public LoginScreen() {
		setTitle("Preguntados");
		setBackground(Color.BLUE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new ClosingListener());
		setBounds(100, 100, 300, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 153, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel("Usuario");
		lblUsername.setBounds(31, 170, 46, 14);
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
		
		

		jLoginButton.addActionListener(new ActionLogin(jUsuarioInexistenteTextArea, jCamposVaciosTextArea));
		jLoginButton.setBounds(84, 274, 89, 23);
		contentPane.add(jLoginButton);

		jPasswordField = new JPasswordField();
		
		jPasswordField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
					new ActionLogin (jUsuarioInexistenteTextArea, jCamposVaciosTextArea).actionPerformed(null);
			}
		});
		jPasswordField.setBounds(136, 215, 86, 20);
		contentPane.add(jPasswordField);

		JButton jSalirButton = new JButton("Salir");
		jSalirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        EndClientConnectionPackage er = new EndClientConnectionPackage();
		        connection.sendPackage(er);
		        System.exit(0);
			}
		});
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
	
	
	public void actionLogin(UserLoginPackage loginresponse){
		if (loginresponse.getUserType() == 0) {
			title = "Preguntados - Usuario: " + loginresponse.getUser();
			AdminMenuScreen adminscreen = new AdminMenuScreen();
			adminscreen.setVisible(true);
			setVisible(false);
			
		} else {
			if(loginresponse.getUserType() == 1){
				title = "Preguntados - Usuario: " + loginresponse.getUser();
				UserMenuScreen userscreen = new UserMenuScreen();
				//userscreen.setLocation(getLocation());
				userscreen.setVisible(true);
				setVisible(false);
				
			}else
				if(loginresponse.getUserType() == -2){
					jUsuarioLogueadoTextArea.setVisible(true);
					jCamposVaciosTextArea.setVisible(false);
					jUsuarioInexistenteTextArea.setVisible(false);
					
				}else{
					jCamposVaciosTextArea.setVisible(false);
					jUsuarioInexistenteTextArea.setVisible(true);
					jUsuarioLogueadoTextArea.setVisible(false);
				}
		}
		
		
	}
	
	public static String getTitleGame(){
		return title;
	}
}

