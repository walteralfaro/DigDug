package walterServerCliente;


public class ServerThread extends Thread {

	private Integer userId;
	private String userName = null;
	private static final int ENDCONECTIONREQUESTID = 8;

	
	public ServerThread(Integer userId) {
		this.userId = userId;
	}

	public void run() {
		Boolean endConection = false;
		
		try {
			UserConnection userConnectionInstance = UserConnection.getInstance();
			
			while (!endConection) {
				Package packageOut = null;
				Package packageIn = userConnectionInstance.readPackage(userId);
				userConnectionInstance.blockSocket(userId);
			
				switch (packageIn.getPackageID()) {
				
				case ENDCONECTIONREQUESTID: // Fin conexion
					Logger.info("Finalizando conexion con el cliente " + userId);
					packageOut = new EndClientConnectionPackage();
					endConection = true;
				}
				
				if(packageOut != null)
					 userConnectionInstance.sendPackage(userId, packageOut);
				
				userConnectionInstance.releaseSocket(userId);
			}
			
			if(userName != null)
				Logger.info("El usuario " + userName + " se ha desconectado.");

			userConnectionInstance.closeOutputStream(userId);
			userConnectionInstance.closeInputStream(userId);
			userConnectionInstance.freeUser(userId);
			
			Logger.info("Conexion finalizada correctamente con el cliente: " + userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
