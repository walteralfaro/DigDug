package pruebaCliente;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import pruebaServer.Player;

public class ScoreGameTableScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -932243294791106324L;
	
	private JPanel contentPane;
	private DefaultTableModel tableModel = new DefaultTableModel(){

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
	    public boolean isCellEditable(int row, int column) {
	       //Que todas las celdas no sean editables.
	       return false;
	    }
	};;
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	private JTable table;

	public ScoreGameTableScreen(ArrayList<Player> scoreTable, final Integer userType) {
		setTitle(LoginScreen.getTitleGame());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				if(userType == 0){
					AdminMenuScreen adminmenuscreen = new AdminMenuScreen();
					adminmenuscreen.setVisible(true);
				}else{
					UserMenuScreen usermenuscreen = new UserMenuScreen();
					usermenuscreen.setVisible(true);
				}
				setVisible(false);
			}
		});
		setBounds(100, 100, 352, 526);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTablaDePuntuaciones = new JLabel("Tabla de puntuaciones de la partida");
		lblTablaDePuntuaciones.setFont(new Font("Verdana", Font.BOLD, 15));
		lblTablaDePuntuaciones.setBounds(21, 21, 327, 35);
		contentPane.add(lblTablaDePuntuaciones);
		
		table = new JTable();
		table.setShowGrid(false);
		table.setRowSelectionAllowed(false);
		table.setToolTipText("");
		table.setBackground(SystemColor.control);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
			},
			new String[] {
				"Puntuaci\u00F3n"
			}
		));
		table.setBounds(52, 110, 239, 201);

		
		tableModel.addColumn("Jugador");
		tableModel.addColumn("Puntuaci�n");
		

		table.setRowHeight(30);
		for(int i = 0; i < scoreTable.size() && i < 10; i++){
			if(!scoreTable.get(i).disconnectedWhilePlaying())
				tableModel.addRow(new String[] {scoreTable.get(i).getName(), scoreTable.get(i).getScore().toString()});
			else
				tableModel.addRow(new String []{scoreTable.get(i).getName(), "Desconectado"});
		}
		
		
		table.setModel(tableModel);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);

		JPanel panel = new JPanel();
		panel.setBounds(34, 75, 264, 343);
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(header, BorderLayout.NORTH);
		panel.add(table, BorderLayout.CENTER);
		
		contentPane.add(panel);
		
		JButton btnVolverAlMen = new JButton("Volver al men\u00FA principal");
		btnVolverAlMen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(userType == 0){
					AdminMenuScreen adminmenuscreen = new AdminMenuScreen();
					adminmenuscreen.setVisible(true);
				}else{
					UserMenuScreen usermenuscreen = new UserMenuScreen();
					usermenuscreen.setVisible(true);
				}
				setVisible(false);
			}
		});
		btnVolverAlMen.setBounds(135, 453, 180, 23);
		contentPane.add(btnVolverAlMen);
		
	}
}
