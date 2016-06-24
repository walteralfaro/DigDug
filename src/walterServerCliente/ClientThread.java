package walterServerCliente;


public class ClientThread extends Thread {
	private LoginScreen loginscreen;
	private static Package packageIn;
	private Boolean endConnection = false;
	private Connection connection = Connection.getInstance();
	private static final int LOGINRESPONSEID = 1;
	private static final int ENDCONNECTIONRESPONSEID = 8;
	private Integer userType;
	private String userName;

	public ClientThread(LoginScreen loginscreen){
		this.loginscreen = loginscreen;
	}
	
	public void run() {
		try {	
			while (!endConnection) {
				packageIn = (Package) connection.recievePackage();
				switch (packageIn.getPackageID()) {
				case ENDCONNECTIONRESPONSEID: // Fin conexion
					endConnection = true;
					connection.endConnection();
				}
			}
			System.out.println("Conexion Finalizada");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
