package cliente;

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

public class ServerErrorWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4435428376666667609L;

	private final JPanel contentPanel = new JPanel();


	
	public ServerErrorWindow() {
		setBounds(100, 100, 496, 158);
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Preguntados - ERROR");
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
               System.exit(ERROR);
            }	
		});
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton okButton = new JButton("OK");
			okButton.setFont(new Font("Tahoma", Font.BOLD, 13));
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.exit(ERROR);
				}
			});
			okButton.setBounds(335, 86, 124, 23);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JLabel lblNewLabel = new JLabel("Se produjo un error en la conexion con el servidor, el juego se cerrar\u00E1.");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(10, 11, 499, 68);
			contentPanel.add(lblNewLabel);
		}
	}

}
