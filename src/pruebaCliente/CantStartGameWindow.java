package pruebaCliente;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Font;

import javax.swing.JTextArea;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CantStartGameWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3318299029244473079L;
	private final JPanel contentPanel = new JPanel();


	public CantStartGameWindow(final GameCreatedAdminScreen gamecreated) {
		setBounds(100, 100, 378, 263);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(LoginScreen.getTitleGame());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing (WindowEvent e){
				gamecreated.setEnabled(true);
				dispose();
			}
		});
		getContentPane().setLayout(null);
		contentPanel.setBackground(SystemColor.control);
		contentPanel.setBounds(0, 0, 434, 229);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JTextArea txtrNoEsPosible = new JTextArea();
		txtrNoEsPosible.setEditable(false);
		txtrNoEsPosible.setBackground(SystemColor.control);
		txtrNoEsPosible.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtrNoEsPosible.setLineWrap(true);
		txtrNoEsPosible.setText("No es posible iniciar la partida,\r\nse necesitan al menos dos jugadores.");
		txtrNoEsPosible.setBounds(31, 69, 310, 64);
		contentPanel.add(txtrNoEsPosible);
		{
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					gamecreated.setEnabled(true);
					dispose();
				}
			});
			okButton.setBounds(251, 196, 97, 23);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
	}
}
