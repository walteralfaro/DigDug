package pruebaCliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pruebaServer.CreateGamePackage;

public class CreateGameScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2365162775440150670L;
	private JPanel contentPane;
	private JTextField nombrePartidaTextField;
	private JTextField cantMaxTextField;
	private JLabel camposMalLlenadosLabel;
	private JLabel errorNombrePartidaLabel;
	private JLabel errorCantMaxLabel;
	private ArrayList<Integer> questionsID = new ArrayList<Integer>();
	private CreateGameScreen thisFrame;
	private static int maxPlayersInGame;
	private static String gameName;
	private Connection connection = Connection.getInstance();

	/**
	 * Launch the application.


	/**
	 * Create the frame.
	 */
	public CreateGameScreen() {
		for(int i = 0; i < 10; i++)
			questionsID.add(null);
		
		thisFrame = this;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				AdminMenuScreen adminmenuscreen = new AdminMenuScreen();
				adminmenuscreen.setVisible(true);
				dispose();
			}
		
		});
		setBounds(100, 100, 604, 300);
		setTitle(LoginScreen.getTitleGame());
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				validateFields();
			}
		});

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombreDeLa = new JLabel("Nombre de la partida");
		lblNombreDeLa.setBounds(10, 44, 168, 25);
		contentPane.add(lblNombreDeLa);
		
		nombrePartidaTextField = new JTextField();
		nombrePartidaTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				validateFields();
			}
		});
		nombrePartidaTextField.setBounds(189, 46, 111, 20);
		contentPane.add(nombrePartidaTextField);
		nombrePartidaTextField.setColumns(10);
		
		JLabel lblCantidadMaximaDe = new JLabel("Cantidad m\u00E1xima de jugadores");
		lblCantidadMaximaDe.setBounds(10, 98, 225, 20);
		contentPane.add(lblCantidadMaximaDe);
		
		cantMaxTextField = new JTextField();
		cantMaxTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				validateFields();
			}
		});
		cantMaxTextField.setBounds(189, 98, 111, 20);
		contentPane.add(cantMaxTextField);
		cantMaxTextField.setColumns(10);
		
		JButton btnElegirPreguntas = new JButton("Elegir preguntas");
		btnElegirPreguntas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChooseQuestionsScreen choosequestionsscreen = new ChooseQuestionsScreen(thisFrame);
				choosequestionsscreen.setVisible(true);
				setEnabled(false);
			}
		});
		btnElegirPreguntas.setBounds(10, 148, 131, 20);
		contentPane.add(btnElegirPreguntas);
		
		JButton btnNewButton = new JButton("Crear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(nombrePartidaTextField.getText().isEmpty() || !isValidNumber(cantMaxTextField.getText())){
					camposMalLlenadosLabel.setVisible(true);
				}
				else{
					gameName = nombrePartidaTextField.getText();
					maxPlayersInGame = Integer.parseInt(cantMaxTextField.getText());
					CreateGamePackage gamerequest = new CreateGamePackage(nombrePartidaTextField.getText(),  Integer.parseInt(cantMaxTextField.getText()), questionsID);
					connection.sendPackage(gamerequest);
					setVisible(false);
				}
				
				
				
			}
		});
		btnNewButton.setBounds(457, 230, 131, 21);
		contentPane.add(btnNewButton);
		
		camposMalLlenadosLabel = new JLabel("Los campos no est\u00E1n llenados correctamente.");
		camposMalLlenadosLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		camposMalLlenadosLabel.setBounds(36, 190, 514, 20);
		camposMalLlenadosLabel.setVisible(false);
		contentPane.add(camposMalLlenadosLabel);
		
		errorNombrePartidaLabel = new JLabel("ErrorNombrePartidaLabel");
		errorNombrePartidaLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		errorNombrePartidaLabel.setBounds(310, 49, 608, 14);
		errorNombrePartidaLabel.setVisible(false);
		contentPane.add(errorNombrePartidaLabel);
		
		errorCantMaxLabel = new JLabel("ErrorCantMaxLabel");
		errorCantMaxLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		errorCantMaxLabel.setBounds(310, 101, 278, 14);
		errorCantMaxLabel.setVisible(false);
		contentPane.add(errorCantMaxLabel);
		
		JButton btnNewButton_1 = new JButton("Atr\u00E1s");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdminMenuScreen adminmenuscreen = new AdminMenuScreen();
				adminmenuscreen.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(310, 230, 128, 21);
		contentPane.add(btnNewButton_1);
	}
	
	public static boolean isNumber (String string){
		try{
			Integer.parseInt(string);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	public static boolean isValidNumber (String string){
		try{
			if(Integer.parseInt(string) >= 2)
				return true;
			return false;
		}catch(Exception e){
			return false;
		}
	}
	
	public void validateFields(){
		
		if(nombrePartidaTextField.getText().isEmpty()){
			errorNombrePartidaLabel.setForeground(java.awt.Color.red);
			errorNombrePartidaLabel.setText("El campo est\u00E1 vacio.");
			errorNombrePartidaLabel.setVisible(true);
		}
		else{
			errorNombrePartidaLabel.setVisible(false);
		}
		
		
		if(cantMaxTextField.getText().isEmpty()){
			errorCantMaxLabel.setForeground(java.awt.Color.red);
			errorCantMaxLabel.setText("El campo est\u00E1 vacio.");
		}
		else{
			if(!isNumber(cantMaxTextField.getText())){
				errorCantMaxLabel.setForeground(java.awt.Color.red);
				errorCantMaxLabel.setText("Por favor, ingrese un n�mero.");
			}
			else{
				if(Integer.parseInt(cantMaxTextField.getText()) < 2){
					errorCantMaxLabel.setForeground(java.awt.Color.red);
					errorCantMaxLabel.setText("La cantidad minima de jugadores debe ser 2.");
					
				}
				else{
					errorCantMaxLabel.setForeground(new Color(67, 205, 128));
					errorCantMaxLabel.setText("Valor valido.");
				}
			}
		}
		
		errorCantMaxLabel.setVisible(true);
		
	}
	
	
	public void receiveQuestionsID(ArrayList<Integer> qID){
		questionsID = qID;
	}
	
	public static int getMaxPlayersInGame(){
		return maxPlayersInGame;
	}
	
	public static String getGameName(){
		return gameName;
	}
}
