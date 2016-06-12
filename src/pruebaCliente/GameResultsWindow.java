package pruebaCliente;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import pruebaServer.Player;

public class GameResultsWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -491986429287473780L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea tiedManyUsersLabel;
	private JTextArea tiedWith1Label;
	private JLabel winnerLabel;
	private JLabel loserLabel;
	private ArrayList<Player> scoreTable;
	private Integer userType;
	

	/**
	 * Launch the application.
	 */


	/**
	 * Create the dialog.
	 */
	public GameResultsWindow() {
		setResizable(false);
		setBounds(100, 100, 444, 235);
		setLocationRelativeTo(null);
		setTitle(LoginScreen.getTitleGame());
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 434, 229);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing (WindowEvent e){
				if(userType == 0){
					AdminMenuScreen adminmenuscreen = new AdminMenuScreen();
					adminmenuscreen.setVisible(true);
				}
				else{
					UserMenuScreen usermenuscreen = new UserMenuScreen();
					usermenuscreen.setVisible(true);
				}
				dispose();
			}
		});
		winnerLabel = new JLabel("Ganaste la partida!");
		winnerLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		winnerLabel.setBounds(70, 69, 292, 43);
		contentPanel.add(winnerLabel);
		{
			JButton okButton = new JButton("Ver tabla de puntuaciones");
			okButton.setBounds(178, 164, 229, 22);
			contentPanel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ScoreGameTableScreen scoreTableScreen = new ScoreGameTableScreen(scoreTable, userType);
					scoreTableScreen.setVisible(true);
					dispose();
			
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		
		loserLabel = new JLabel("Perdiste la partida!");
		loserLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		loserLabel.setBounds(70, 49, 292, 43);
		contentPanel.add(loserLabel);
		
		tiedWith1Label = new JTextArea();
		tiedWith1Label.setEditable(false);
		tiedWith1Label.setBackground(SystemColor.control);
		tiedWith1Label.setLineWrap(true);
		tiedWith1Label.setFont(new Font("Tahoma", Font.BOLD, 16));
		tiedWith1Label.setText("Saliste empatado en el primer puesto con otro usuario!");
		tiedWith1Label.setBounds(23, 49, 351, 97);
		contentPanel.add(tiedWith1Label);
		
		tiedManyUsersLabel = new JTextArea();
		tiedManyUsersLabel.setEditable(false);
		tiedManyUsersLabel.setText("Saliste empatado en el primer puesto con otros usuarios!");
		tiedManyUsersLabel.setLineWrap(true);
		tiedManyUsersLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		tiedManyUsersLabel.setBackground(SystemColor.menu);
		tiedManyUsersLabel.setBounds(23, 49, 351, 97);
		contentPanel.add(tiedManyUsersLabel);
		
		winnerLabel.setVisible(false);
		loserLabel.setVisible(false);
		tiedManyUsersLabel.setVisible(false);
		tiedWith1Label.setVisible(false);
	}
	
	public void setLabelWinnerStatus(Integer winnerstatus, Integer numberofwinners){
		if(winnerstatus == 1)
			winnerLabel.setVisible(true);
		else
			if(winnerstatus == -1)
				loserLabel.setVisible(true);
			else
				if(numberofwinners == 2)
					tiedWith1Label.setVisible(true);
				else
					tiedManyUsersLabel.setVisible(true);
	}

	public void setScoreTableAndUserType(ArrayList<Player> scoreTable, Integer userType) {
		this.scoreTable = scoreTable;
		this.userType = userType;
	}
}
