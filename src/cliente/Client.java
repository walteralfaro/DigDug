package cliente;

public class Client {

	private Connection connection;

	public Client() {
//		connection = Connection.getInstance();
	
//		if(connection.getSocket() != null){
			LoginScreen loginscreen = new LoginScreen();
//			new ClientThread(loginscreen).start();
			loginscreen.setVisible(true);			
//		}else{
//			CantConnectWindow cantconnectwindow = new CantConnectWindow();
//			cantconnectwindow.setVisible(true);
//		}
	}
	
	public static void main(String[] args) {
		new Client();
	}

}
