package walterServerCliente;

public class Client {

	private Connection connection;

	public Client() {
		connection = Connection.getInstance();
	
		if(connection.getSocket() != null){
			new ClientThread().start();
		}
	}
	
	public static void main(String[] args) {
		new Client();
	}

}
