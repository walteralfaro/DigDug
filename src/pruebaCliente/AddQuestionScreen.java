package pruebaCliente;


import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pruebaServer.CategoryPackage;
import pruebaServer.Question;

public class AddQuestionScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4009979497918877231L;
	private JPanel contentPane;
	private JTextArea jPreguntaTextArea;
	private JTextField jRespuestaCorrectaTextField;
	private JTextField jRespuestaInc1tf;
	private JTextField jRespuestaInc2tf;
	private JTextField jRespuestaInc3tf;
	private JComboBox<String> jCategoriaComboBox;
	private JTextArea jImposibleAgregarPreguntaTextArea; 
	private Connection connection = Connection.getInstance();


	public AddQuestionScreen() {
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing (WindowEvent e){
				AdminMenuScreen adminmenuscreen = new AdminMenuScreen();
				adminmenuscreen.setVisible(true);
				setVisible(false);	
			}
		});
		setBounds(100, 100, 336, 561);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(LoginScreen.getTitleGame());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPregunta = new JLabel("Pregunta:");
		lblPregunta.setBounds(43, 36, 71, 14);
		contentPane.add(lblPregunta);
		
		jPreguntaTextArea = new JTextArea();
		jPreguntaTextArea.setBounds(43, 61, 233, 69);
		contentPane.add(jPreguntaTextArea);
		
		JLabel lblRespuestaCorrecta = new JLabel("Respuesta Correcta");
		lblRespuestaCorrecta.setBounds(43, 141, 162, 14);
		contentPane.add(lblRespuestaCorrecta);
		
		jRespuestaCorrectaTextField = new JTextField();
		jRespuestaCorrectaTextField.setBounds(43, 170, 243, 20);
		contentPane.add(jRespuestaCorrectaTextField);
		jRespuestaCorrectaTextField.setColumns(10);
		
		JLabel lblRespuestaInorrecta = new JLabel("Respuesta Incorrecta 1");
		lblRespuestaInorrecta.setBounds(43, 201, 162, 14);
		contentPane.add(lblRespuestaInorrecta);
		
		jRespuestaInc1tf = new JTextField();
		jRespuestaInc1tf.setColumns(10);
		jRespuestaInc1tf.setBounds(43, 226, 243, 20);
		contentPane.add(jRespuestaInc1tf);
		
		JLabel lblRespuestaIncorrecta = new JLabel("Respuesta Incorrecta 2");
		lblRespuestaIncorrecta.setBounds(43, 257, 162, 14);
		contentPane.add(lblRespuestaIncorrecta);
		
		jRespuestaInc2tf = new JTextField();
		jRespuestaInc2tf.setColumns(10);
		jRespuestaInc2tf.setBounds(43, 282, 243, 20);
		contentPane.add(jRespuestaInc2tf);
		
		JLabel lblRespuestaIncorrecta_1 = new JLabel("Respuesta Incorrecta 3");
		lblRespuestaIncorrecta_1.setBounds(43, 313, 162, 14);
		contentPane.add(lblRespuestaIncorrecta_1);
		
		jRespuestaInc3tf = new JTextField();
		jRespuestaInc3tf.setColumns(10);
		jRespuestaInc3tf.setBounds(43, 338, 243, 20);
		contentPane.add(jRespuestaInc3tf);
		
		JLabel lblCategora = new JLabel("Categor\u00EDa");
		lblCategora.setBounds(43, 381, 57, 14);
		contentPane.add(lblCategora);
		
		jCategoriaComboBox = new JComboBox<String>();
		jCategoriaComboBox.setBounds(110, 378, 176, 20);
		contentPane.add(jCategoriaComboBox);

		
		
		jImposibleAgregarPreguntaTextArea = new JTextArea();
		jImposibleAgregarPreguntaTextArea.setBackground(SystemColor.menu);
		jImposibleAgregarPreguntaTextArea.setEditable(false);
		jImposibleAgregarPreguntaTextArea.setVisible(false);
		jImposibleAgregarPreguntaTextArea.setLineWrap(true);
		jImposibleAgregarPreguntaTextArea.setText("No se pudo agregar la pregunta. Por favor, complete todos los campos.");
		jImposibleAgregarPreguntaTextArea.setBounds(10, 420, 303, 60);
		contentPane.add(jImposibleAgregarPreguntaTextArea);
		
		final JLabel lblPreguntaAgregada = new JLabel("La pregunta se agreg\u00F3 correctamente a la base de datos");
		lblPreguntaAgregada.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPreguntaAgregada.setBounds(10, 436, 433, 14);
		lblPreguntaAgregada.setVisible(false);
		contentPane.add(lblPreguntaAgregada);
		
		JButton jAgregarPreguntaButton = new JButton("Agregar Pregunta");
		jAgregarPreguntaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jPreguntaTextArea.getText().isEmpty() || jRespuestaCorrectaTextField.getText().isEmpty() || jRespuestaInc1tf.getText().isEmpty()
						|| jRespuestaInc2tf.getText().isEmpty() || jRespuestaInc3tf.getText().isEmpty() ){
					jImposibleAgregarPreguntaTextArea.setVisible(true);
					lblPreguntaAgregada.setVisible(false);
					
				}else{
					ArrayList <String> incorrectas = new ArrayList <String> ();
					incorrectas.add(jRespuestaInc1tf.getText());
					incorrectas.add(jRespuestaInc2tf.getText());
					incorrectas.add(jRespuestaInc3tf.getText());
					String categoria = new String ();
					categoria = (String)jCategoriaComboBox.getSelectedItem();
					Question question = new Question (null, jPreguntaTextArea.getText(), categoria, jRespuestaCorrectaTextField.getText(), incorrectas);
					connection.sendPackage(question);
					jImposibleAgregarPreguntaTextArea.setVisible(false);
					lblPreguntaAgregada.setVisible(true);
					
				}
			}
		});
		jAgregarPreguntaButton.setBounds(43, 499, 243, 23);
		contentPane.add(jAgregarPreguntaButton);
		
		JButton jAtrasButton = new JButton("Atr\u00E1s");
		jAtrasButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdminMenuScreen ams = new AdminMenuScreen();
				ams.setVisible(true);
				setVisible(false);
				
			}
		});
		jAtrasButton.setBounds(177, 11, 89, 23);
		contentPane.add(jAtrasButton);
		
		
	}
	
	public void clearScreen(){
		jPreguntaTextArea.setText("");
		jRespuestaCorrectaTextField.setText("");
		jRespuestaInc1tf.setText("");
		jRespuestaInc2tf.setText("");
		jRespuestaInc3tf.setText("");
	}

	public void setCategories(CategoryPackage categoryresponse) {
		for(int i = 0; i < categoryresponse.getCategories().size(); i++){
			jCategoriaComboBox.addItem(categoryresponse.getCategories().get(i).getCategory());
		}
		
	}
}
