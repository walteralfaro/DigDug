package walterServerCliente;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CantConnectWindow extends JDialog {

	
	private static final long serialVersionUID = -6161505484723455997L;
	private final JPanel contentPanel = new JPanel();


	public CantConnectWindow() {
		setTitle("Dig Dug - ERROR");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
               System.exit(ERROR);
            }	
		});
		setLocationRelativeTo(null);
		setResizable(false);
		setBounds(100, 100, 470, 178);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("No se ha podido establecer la conexi\u00F3n con el servidor.");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(21, 43, 417, 60);
		contentPanel.add(lblNewLabel);
	}
}
