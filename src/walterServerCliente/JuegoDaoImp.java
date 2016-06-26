package walterServerCliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase simple que se encarga de pasar la conexión a la base de datos.
 */

// http://www.tutorialspoint.com/sqlite/sqlite_java.htm

public final class JuegoDaoImp {
	
	public void crearTablasUsuario() {

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db/baseDeDatos.db");
			stmt = c.createStatement();
			String sql = " CREATE TABLE USUARIO "
						// + " (id INT PRIMARY KEY     NOT NULL, "
						 + " (id integer primary key autoincrement, "
						 + " name           TEXT     NOT NULL, " 
						 + " pass           TEXT     NOT NULL ) ";			
			
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
			System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created USUARIO successfully");
	}

	public void crearTablasScore() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db/baseDeDatos.db");
			stmt = c.createStatement();
			String sql = " CREATE TABLE SCORE "
					+ " (id integer primary key autoincrement, "
					+ " idUsuario        INT      NOT NULL, " 
					+ " idPartida        INT      NOT NULL, "
					+ " puntaje          INT )";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
			System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created SCORE successfully");
	}

	public void insertScore(Integer idUsuario, Integer idPartida, Integer puntaje ) {
		Logger.init("insertScore");
		Connection c = null;
		Statement stmt = null;
		try {
			c = getConnectionDB();

			stmt = c.createStatement();
			String sql = "INSERT INTO SCORE (idUsuario,idPartida,puntaje) "
					+ "VALUES ("+idUsuario+","+idPartida+","+puntaje+");";
			stmt.executeUpdate(sql);
			stmt.close();
			commitAndClose(c);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Logger.end("insertScore");
	}

	public List<User> obtenerUsuarios() {
		Logger.init("obtenerUsuarios");
		Connection c = null;
		Statement stmt = null;
		List<User> users = new ArrayList<User>();

		try {
			c = getConnectionDB();
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO;");
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String pass = rs.getString("pass");
				User u = new User(null,null);
				u.setName(name);
				u.setId(id);
				u.setPass(pass);
				users.add(u);
				System.out.println("ID = " + id);
				System.out.println("NAME = " + name);
				System.out.println("PASS = " + pass);
				System.out.println();
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Logger.end("obtenerUsuarios");
		return users;
	}
	
	public Integer obtenerScorePorUsuario(String nombre, Integer idPartida) {
		Logger.init("obtenerScorePorUsuario");
		Integer score = 0;
		Connection c = null;
		Statement stmt = null;
		try {
			c = getConnectionDB();

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO WHERE name = '"+nombre+"';");
			int idUser=-1 ;
			if(rs.next()){
				idUser = rs.getInt("id");
			}
			
			if(idUser!=-1 ){
				rs = stmt.executeQuery("SELECT * FROM SCORE where idUsuario="+idUser+" and idPartida ='"+idPartida+"';");
				while (rs.next()) {
					/*int id = rs.getInt("id");
					Integer idUsuario = rs.getInt("idUsuario");
					Integer idPartidaa = rs.getInt("idPartida");
					Integer puntaje   = rs.getInt("puntaje");

					System.out.println("ID = " + id);
					System.out.println("idUsuario = " + idUsuario);
					System.out.println("idPartida = " + idPartidaa);
					System.out.println("puntaje = " + puntaje);*/
					score = score + rs.getInt("puntaje");;
				}
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		Logger.end("obtenerScorePorUsuario");

		return score;
	}

	public boolean validaUsuario(String name, String pass) {
		Logger.init("validaUsuario");
		Connection c = null;
		Statement stmt = null;
		boolean existe= false;

		try {
			c = getConnectionDB();

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO where name ='"+name+"' and pass ='"+pass+"';");
			if (rs.next()) {
				stmt.close();
				c.close();
				rs.close();
				existe = true; //se logueo
				Logger.end("validaUsuario");
				return existe;
			}
			Logger.info("validaUsuario - USUARIO O CONTRASEÑA ERRONEA");

			rs.close();
			stmt.close();
			
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Logger.end("validaUsuario");
		return existe;
	}
	
	public boolean registrarUsuario(String name, String pass) {
		Logger.init("registrarUsuario");
		Connection c = null;
		Statement stmt = null;
		boolean existe= false;
		try {
			c = getConnectionDB();

			stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO where name ='"+name+"';");
			if (rs.next()) {
				stmt.close();
				c.close();
				rs.close();
				System.out.println("FIN - RegistrarUsuario - Usuario ya registrado: " + name);
				return existe;
			}
			existe = true;
			
			String sql = "INSERT INTO USUARIO (name,pass)  VALUES ('"+name+"', '"+pass+"') ";
			stmt.executeUpdate(sql);
			stmt.close();
			
			commitAndClose(c);
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Logger.end("registrarUsuario");
		return existe;
	}
	
	public boolean registrarUsuario(String name, String pass,Integer id) {
		Logger.init("registrarUsuario con id");


		Connection c = null;
		Statement stmt = null;
		boolean existe= false;
		try {
			c = getConnectionDB();

			stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO where name ='"+name+"';");
			if (rs.next()) {
				stmt.close();
				c.close();
				rs.close();
				Logger.info("registrarUsuario - Usuario ya registrado: " + name);
				Logger.end("registrarUsuario con id");
				return existe;
			}
			existe = true;
			
			String sql = "INSERT INTO USUARIO (name,pass) "
					   + "VALUES ("+id+",'"+name+"', '"+pass+"') ";
			stmt.executeUpdate(sql);
			stmt.close();
			
			commitAndClose(c);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		Logger.end("registrarUsuario con id");
		return existe;
	}

	public void mostrarScores() {
		Logger.init("obtenerScores");
		Connection c = null;
		Statement stmt = null;
		try {
			c = getConnectionDB();

			stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM SCORE ;");
				while (rs.next()) {
					int id = rs.getInt("id");
					Integer idUsuario = rs.getInt("idUsuario");
					Integer idPartidaa = rs.getInt("idPartida");
					Integer puntaje   = rs.getInt("puntaje");

					System.out.println("ID = " + id);
					System.out.println("idUsuario = " + idUsuario);
					System.out.println("idPartida = " + idPartidaa);
					System.out.println("puntaje = "   + puntaje);
				}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Logger.end("obtenerScores");
	}

	public Integer obtenerScores(Integer idUsuario) {
		Logger.init("obtenerScores por idUsuario");
		Connection c = null;
		Statement stmt = null;
		Integer score = 0;
		try {
			c = getConnectionDB();

			stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM SCORE where idUsuario ="+idUsuario+";");
				while (rs.next()) {
					/*int id = rs.getInt("id");
					Integer idUser = rs.getInt("idUsuario");
					Integer idPartidaa = rs.getInt("idPartida");
					Integer puntaje   = rs.getInt("puntaje");*/

					/*System.out.println("ID = " + id);
					System.out.println("idUsuario = " + idUser);
					System.out.println("idPartida = " + idPartidaa);
					System.out.println("puntaje = "   + puntaje);*/
					score = score + rs.getInt("puntaje");;
				}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Logger.end("obtenerScores por idUsuario");
		return score;
	}


	public User obtenerUsuario(String nombre) {
		Logger.init("obtenerUsuario por nombre de usuario");
		Connection c = null;
		Statement stmt = null;
		User user = new User(null,null);

		try {
			c = getConnectionDB();

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO where name ='"+nombre+"';");
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				user.setName(name);
				user.setId(id);
				/*System.out.println("ID = " + id);
				System.out.println("NAME = " + name);*/
			}
			rs.close();
			stmt.close();
			
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Logger.end("obtenerUsuario por nombre de usuario");

		return user;
	}
	


	public boolean actualizarUsuario(String nombre,String nuevoNombre,String nuevoPass) {
		Logger.init("actualizarUsuario nombre y pass");
		Connection c = null;
		Statement stmt = null;
		boolean flag = false;
		try {
			c = getConnectionDB();

			stmt = c.createStatement();
			int value = stmt.executeUpdate("UPDATE USUARIO SET name ='"+nuevoNombre+"' , pass = '"+nuevoPass+"' where name = '"+nombre+"';");
			if(value != 0){
				flag = true;
				System.out.println("Se actulizo nombre y pass del usuario");
			}
			stmt.close();
			
			commitAndClose(c);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Logger.end("actualizarUsuario nombre y pass");

		return flag;
	}

	private void commitAndClose(Connection c) throws SQLException {
		c.commit();
		c.close();
	}

	private Connection getConnectionDB() throws ClassNotFoundException, SQLException {
		Connection c;
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:db/baseDeDatos.db");
		c.setAutoCommit(false);
		return c;
	}
	
}
