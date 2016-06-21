package walterServerCliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerEnd extends Thread {

	private static final ServerEnd instance = new ServerEnd();
	private Boolean isTheEnd = false;
			
	private ServerEnd() {
	}
	
	public static final ServerEnd getInstance() {
		return instance;
	}
	
	public Boolean isTheEnd() {
		return isTheEnd;
	}
	
	public void run() {
		while(isTheEnd == false) {
			try {
				char c; 
				BufferedReader br = new 
				BufferedReader(new InputStreamReader(System.in)); 
				c = (char) br.read(); 
				if(c == 'q') isTheEnd = true;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
