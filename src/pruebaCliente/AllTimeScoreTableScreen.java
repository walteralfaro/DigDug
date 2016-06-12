package pruebaCliente;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import pruebaServer.Score;
import pruebaServer.User;

import java.awt.SystemColor;
import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AllTimeScoreTableScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7768905512518131458L;
	private JPanel contentPane;
	private JPanel panel;
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


	public AllTimeScoreTableScreen(ArrayList<User> topTen, final Integer userType) {
		setTitle(LoginScreen.getTitleGame());
		Score historicalScore;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		setBounds(100, 100, 908, 508);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 93, 872, 339);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Puntuacion"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7385778525133478554L;
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		

		table.setRowHeight(30);
		tableModel.addColumn("Jugador");
		tableModel.addColumn("Partidas jugadas");
		tableModel.addColumn("Partidas ganadas");
		tableModel.addColumn("Partidas perdidas");
		tableModel.addColumn("Respuestas correctas");
		tableModel.addColumn("Respuestas incorrectas");
		
		for(int i = 0; i < topTen.size() && i < 10; i++ ){
			historicalScore = topTen.get(i).getHistoricalScore();
			tableModel.addRow(new String[] {topTen.get(i).getName(), historicalScore.getGamesPlayed().toString(), historicalScore.getGamesWon().toString(), historicalScore.getGamesLost().toString(), historicalScore.getCorrectAnswers().toString(), historicalScore.getWrongAnswers().toString()});		
		}
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		table.setModel(tableModel);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		for(int i = 0; i < table.getColumnModel().getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		
		table.setToolTipText("");
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setShowGrid(false);
		table.setRowSelectionAllowed(false);
		table.setRowHeight(30);
		table.setBackground(SystemColor.menu);
		panel.add(table, BorderLayout.CENTER);
		panel.add(header, BorderLayout.NORTH);
		
		JLabel lblTablaHistricaDe = new JLabel("Tabla hist\u00F3rica de puntuaci\u00F3n");
		lblTablaHistricaDe.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTablaHistricaDe.setBounds(282, 30, 326, 22);
		contentPane.add(lblTablaHistricaDe);
		
		JButton btnVolverAlMen = new JButton("Volver al men\u00FA");
		btnVolverAlMen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		btnVolverAlMen.setBounds(678, 446, 204, 23);
		contentPane.add(btnVolverAlMen);
		
	}
}
