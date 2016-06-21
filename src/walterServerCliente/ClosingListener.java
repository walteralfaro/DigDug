package walterServerCliente;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import pruebaServer.EndClientConnectionPackage;

public class ClosingListener implements WindowListener {

	private Connection connection = Connection.getInstance();
	
	public void windowActivated(WindowEvent e) {
	}

	
	public void windowClosed(WindowEvent e) {
		
	}

	
	public void windowClosing(WindowEvent e) {
        EndClientConnectionPackage er = new EndClientConnectionPackage();
        connection.sendPackage(er);
        System.exit(0);
	}

	
	public void windowDeactivated(WindowEvent e) {
		
	}

	
	public void windowDeiconified(WindowEvent e) {
	}

	
	public void windowIconified(WindowEvent e) {
	}

	
	public void windowOpened(WindowEvent e) {
	}

}
