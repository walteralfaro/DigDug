package pruebaCliente;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pruebaServer.PlayerDisconnectPackage;
import pruebaServer.StartGamePackage;

public class GameCreatedAdminScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6472066543008643691L;
	private JPanel contentPane;
	private JList<String> playersList;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JLabel playersInGameLabel;
	private Connection connection = Connection.getInstance();
	private JButton btnNewButton;


	public GameCreatedAdminScreen(Integer maxPlayersInGame, String adminName) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			 public void windowClosing(WindowEvent e) {
				 	AdminMenuScreen adminmenuscreen = new AdminMenuScreen();
					adminmenuscreen.setVisible(true);
					connection.sendPackage(new PlayerDisconnectPackage(null, true));
					dispose();
	            }	
		});
		setBounds(100, 100, 407, 445);
		setTitle(LoginScreen.getTitleGame());
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIniciarPartida = new JButton("INICIAR PARTIDA");
		btnIniciarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StartGamePackage start = new StartGamePackage();
				connection.sendPackage(start);
				
			}
		});
		btnIniciarPartida.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnIniciarPartida.setBounds(90, 309, 217, 59);
		contentPane.add(btnIniciarPartida);
		
		playersList = new JList<String>();
		playersList.setBounds(110, 107, 182, 191);
		contentPane.add(playersList);
		
		JLabel lblJugadoresDentroDe = new JLabel("Jugadores dentro de la partida: ");
		lblJugadoresDentroDe.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblJugadoresDentroDe.setBounds(79, 40, 248, 46);
		contentPane.add(lblJugadoresDentroDe);
		
		JLabel lblXxmax = new JLabel("/xMax");
		lblXxmax.setText("/" + maxPlayersInGame);
		lblXxmax.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblXxmax.setBounds(293, 56, 73, 14);
		contentPane.add(lblXxmax);
		
		playersInGameLabel = new JLabel("1");
		playersInGameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		playersInGameLabel.setBounds(282, 56, 25, 14);
		contentPane.add(playersInGameLabel);
		
		listModel.addElement(adminName);
		playersList.setModel(listModel);
		
		btnNewButton = new JButton("Cancelar partida y volver al menu principal");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenuScreen adminmenuscreen = new AdminMenuScreen();
				adminmenuscreen.setVisible(true);
				connection.sendPackage(new PlayerDisconnectPackage(null, true));
				dispose();
			}
		});
		btnNewButton.setBounds(53, 379, 317, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblGameName = new JLabel("Nombre de la partida");
		lblGameName.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameName.setText(CreateGameScreen.getGameName());
		lblGameName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGameName.setBounds(79, 11, 248, 34);
		contentPane.add(lblGameName);
	}


	public void playerHasJoined(String userName) {
		Integer players = Integer.parseInt(playersInGameLabel.getText());
		players++;
		playersInGameLabel.setText(players.toString());
		listModel.addElement(userName);
		playersList.setModel(listModel);
	}


	public void playerHasLeft(String userName) {
		Integer players = Integer.parseInt(playersInGameLabel.getText());
		players--;
		playersInGameLabel.setText(players.toString());
		listModel.removeElement(userName);		
		playersList.setModel(listModel);
	}
}
