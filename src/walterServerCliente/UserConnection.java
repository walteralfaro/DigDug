package walterServerCliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class UserConnection {
	
	private static final UserConnection instance = new UserConnection();
	private static final int MAXCONNECTIONS = 50;
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<ObjectOutputStream> outputStream = new ArrayList<ObjectOutputStream>();;
	private ArrayList<ObjectInputStream> inputStream = new ArrayList<ObjectInputStream>();;
	private ArrayList<Semaphore> semaphores = new ArrayList<Semaphore>();
	
	private UserConnection() {
		for(int x = 0; x < MAXCONNECTIONS; x++) {
			users.add(null);
			outputStream.add(null);
			inputStream.add(null);
			semaphores.add(new Semaphore(1));
		}
	}
	
	public static UserConnection getInstance() {
		return instance;
	}
	
	public void sendPackage(int index, Package pack) throws Exception {
		outputStream.get(index).writeObject(pack);
	}

	public Package readPackage(int index) throws Exception {
		return ((Package) inputStream.get(index).readObject());
	}

	public void closeOutputStream(int index) throws Exception {
		if(outputStream.get(index) != null) {
			outputStream.get(index).close();
			outputStream.set(index, null);
		}
	}
	
	public void closeInputStream(int index) throws Exception {
		if(inputStream.get(index) != null) {
			inputStream.get(index).close();
			inputStream.set(index, null);
		}
	}

	public void blockSocket(int index) {
		try {
			semaphores.get(index).acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void releaseSocket(int index) {
		try {
			semaphores.get(index).release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setUserConnection(User user) {
		try {
			users.set(user.getId(), user);
			outputStream.set(user.getId(), new ObjectOutputStream(user.getSocket().getOutputStream()));
			inputStream.set(user.getId(), new ObjectInputStream(user.getSocket().getInputStream()));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public User getUser(int index) {
		return users.get(index);
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public int getFreeIndexUser() {
		for(int x = 0; x < MAXCONNECTIONS; x++) 
			if(users.get(x) == null) return x; 
		return -1;
	}
	
	public void freeUser(int index) {
		users.set(index, null);
	}
	
	public Boolean userIsLogged(String userName) {
		for(User eachUser: users)
			if(eachUser != null && eachUser.getName().equals(userName))
				return true;
		return false;
	}
	
}
