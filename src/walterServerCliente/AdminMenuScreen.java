package walterServerCliente;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import pruebaServer.CategoryPackage;
import pruebaServer.EndClientConnectionPackage;
import pruebaServer.PlayerJoinPackage;
import pruebaServer.TopTenUserPackage;

public class AdminMenuScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8573202483454046346L;
	private JPanel contentPane;
	private Connection connection = Connection.getInstance();

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public AdminMenuScreen() {

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new ClosingListener());
		setBounds(100, 100, 300, 500);
		setTitle(LoginScreen.getTitleGame());
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextArea txtrPreguntados = new JTextArea();
		txtrPreguntados.setEditable(false);
		txtrPreguntados.setFont(new Font("Century Gothic", Font.PLAIN, 32));
		txtrPreguntados.setText("Preguntados");
		txtrPreguntados.setBounds(45, 31, 202, 44);
		contentPane.add(txtrPreguntados);

		JButton jCrearPartidaButton = new JButton("Crear partida");
		jCrearPartidaButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		jCrearPartidaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//CreateGameScreen creategamescreen = new CreateGameScreen();
				//creategamescreen.setVisible(true);
				setVisible(false);
			}
		});
		jCrearPartidaButton.setBounds(65, 140, 162, 23);
		contentPane.add(jCrearPartidaButton);

		JButton jUnirsePartidaButton = new JButton("Unirse a una partida");
		jUnirsePartidaButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		jUnirsePartidaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlayerJoinPackage joinrequest = new PlayerJoinPackage();
				connection.sendPackage(joinrequest);
				dispose();
			}
		});
		jUnirsePartidaButton.setBounds(65, 194, 162, 23);
		contentPane.add(jUnirsePartidaButton);

		JButton btnNewButton = new JButton("Tabla hist\u00F3rica");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TopTenUserPackage toptenuserrequest = new TopTenUserPackage();
				connection.sendPackage(toptenuserrequest);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(65, 284, 162, 23);
		contentPane.add(btnNewButton);

		JButton jSalirButton = new JButton("Salir");
		jSalirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        EndClientConnectionPackage er = new EndClientConnectionPackage();
		        connection.sendPackage(er);
		        System.exit(0);
			}
		});
		jSalirButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		jSalirButton.setBounds(65, 327, 162, 23);
		contentPane.add(jSalirButton);

		JButton btnNewButton_2 = new JButton("Agregar pregunta ");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//AddQuestionScreen aq = new AddQuestionScreen();
				//ClientThread.recieveScreen(aq);
				connection.sendPackage(new CategoryPackage(2));
				//aq.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_2.setBounds(65, 240, 162, 23);
		contentPane.add(btnNewButton_2);
	}
}
