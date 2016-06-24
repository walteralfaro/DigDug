package server;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import commons.Maps;
import server.Listener;
//import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

//import net.miginfocom.swing.MigLayout;
public class ServerMain {

	private static final String STOP_SERVER = "Stop";
	private static final String STAR_SERVER = "StartUp";

	private JFrame home;
	private static Listener serviceListener;
	private JTextField textFieldPuerto;
	JButton btnInit = new JButton("Iniciar Server");
	private Maps mapas = new Maps();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerMain serverMain = new ServerMain();
					serverMain.home.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerMain() {
		prepare();
	}

	private void prepare() {

		home = new JFrame();
		home.setTitle("Server");
		home.setBounds(100, 100, 900, 700);
		home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		home.getContentPane().setLayout(null);
		home.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Puerto:");
		lblNewLabel.setBounds(55, 37, 61, 16);
		home.getContentPane().add(lblNewLabel);
		
		textFieldPuerto = new JTextField();
		textFieldPuerto.setText("5555");
		textFieldPuerto.setBounds(119, 32, 130, 26);
		home.getContentPane().add(textFieldPuerto);
		textFieldPuerto.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 86, 797, 464);
		home.getContentPane().add(scrollPane);
		
//		JButton btnInit = new JButton("Iniciar Server");
		btnInit.setBounds(55, 562, 117, 29);
		btnInit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initServer();
			}
		});
		home.getContentPane().add(btnInit);

	}

	private synchronized void initServer() {
		btnInit.setEnabled(false);
		new Thread() {
			@Override
			public void run() {
				try {
					serviceListener = new Listener(Integer.parseInt(textFieldPuerto.getText()));
					serviceListener.start();
					btnInit.setText(STOP_SERVER);
					btnInit.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							stopServer();
						}
					});
				} catch (Exception e) {
					serviceListener = null;
				} finally {
					btnInit.setEnabled(true);
				}
			}
		}.start();

	}

	private synchronized void stopServer() {
		serviceListener.cerrar();
		try {
			serviceListener.join();
			serviceListener = null;
			btnInit.setText(STAR_SERVER);
			btnInit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					initServer();
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static Listener getServicioEscucha() {
		return serviceListener;
	}
}
