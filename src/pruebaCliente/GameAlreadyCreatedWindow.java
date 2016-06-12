package pruebaCliente;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameAlreadyCreatedWindow extends JDialog {

	
	private static final long serialVersionUID = -4525875386020388805L;
	private final JPanel contentPanel = new JPanel();


	public GameAlreadyCreatedWindow() {
		setBounds(100, 100, 450, 270);
		setTitle(LoginScreen.getTitleGame());
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			 public void windowClosing(WindowEvent e) {
				 	AdminMenuScreen adminmenuscreen = new AdminMenuScreen();
					adminmenuscreen.setVisible(true);
					dispose();
	            }	
		});
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					AdminMenuScreen adminmenuscreen = new AdminMenuScreen();
					adminmenuscreen.setVisible(true);
					dispose();
				}
			});
			okButton.setBounds(296, 193, 100, 23);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		
		JLabel lblLaPartidaYa = new JLabel("La partida ya se encuentra creada.");
		lblLaPartidaYa.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLaPartidaYa.setBounds(60, 81, 302, 36);
		contentPanel.add(lblLaPartidaYa);
	}
}
