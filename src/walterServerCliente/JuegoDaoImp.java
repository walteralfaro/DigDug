package walterServerCliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase simple que se encarga de pasar la conexión a la base de datos.
 */

// http://www.tutorialspoint.com/sqlite/sqlite_java.htm

public final class JuegoDaoImp {
	
	JuegoDaoImp instancia;

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
		System.out.println("IN - insertScore");

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db/baseDeDatos.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "INSERT INTO SCORE (idUsuario,idPartida,puntaje) "
					+ "VALUES ("+idUsuario+","+idPartida+","+puntaje+");";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("FIN - insertScore");
	}

	public List<User> obtenerUsuarios() {
		System.out.println("IN - obtenerUsuarios");

		Connection c = null;
		Statement stmt = null;
		List<User> users = new ArrayList<User>();

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db/baseDeDatos.db");
			c.setAutoCommit(false);
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
		System.out.println("END - obtenerUsuarios");
		return users;
	}
	
	public Integer obtenerScorePorUsuario(String nombre, Integer idPartida) {
		System.out.println("IN - obtenerScorePorUsuario");
		Integer score = 0;
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db/baseDeDatos.db");
			c.setAutoCommit(false);

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
		System.out.println("END - obtenerScorePorUsuario");
		return score;
	}

	public boolean validaUsuario(String name, String pass) {
		System.out.println("IN - validaUsuario");

		Connection c = null;
		Statement stmt = null;
		boolean existe= false;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db/baseDeDatos.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO where name ='"+name+"' and pass ='"+pass+"';");
			if (rs.next()) {
				stmt.close();
				c.close();
				rs.close();
				existe = true; //se logueo
				System.out.println("END - validaUsuario");
				return existe;
			}
			System.out.println("INFO - validaUsuario - USUARIO O CONTRASEÑA ERRONEA");

			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		System.out.println("END - validaUsuario");
		return existe;

	}
	
	public boolean registrarUsuario(String name, String pass) {
		System.out.println("IN - registrarUsuario");

		Connection c = null;
		Statement stmt = null;
		boolean existe= false;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db/baseDeDatos.db");
			c.setAutoCommit(false);

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
			
			String sql = "INSERT INTO USUARIO (name,pass) "
					   + "VALUES ('"+name+"', '"+pass+"') ";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		System.out.println("END - registrarUsuario");
		return existe;
	}
	
	public boolean registrarUsuario(String name, String pass,Integer id) {
		System.out.println("IN - registrarUsuario con id");

		Connection c = null;
		Statement stmt = null;
		boolean existe= false;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db/baseDeDatos.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO where name ='"+name+"';");
			if (rs.next()) {
				stmt.close();
				c.close();
				rs.close();
				System.out.println("END - registrarUsuario - Usuario ya registrado: " + name);
				return existe;
			}
			existe = true;
			
			String sql = "INSERT INTO USUARIO (name,pass) "
					   + "VALUES ("+id+",'"+name+"', '"+pass+"') ";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		System.out.println("END - registrarUsuario con id");
		return existe;
	}

	public void mostrarScores() {
		System.out.println("IN - obtenerScores");
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db/baseDeDatos.db");
			c.setAutoCommit(false);

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
		System.out.println("END - obtenerScores");
	}

	public Integer obtenerScores(Integer idUsuario) {
		System.out.println("IN - obtenerScores por idUsuario");
		Connection c = null;
		Statement stmt = null;
		Integer score = 0;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db/baseDeDatos.db");
			c.setAutoCommit(false);

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
		System.out.println("END - obtenerScores por idUsuario");
		return score;
	}


	public User obtenerUsuario(String nombre) {
		System.out.println("IN - obtenerUsuario por nombre de usuario");

		Connection c = null;
		Statement stmt = null;
		User user = new User(null,null);

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db/baseDeDatos.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

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
		System.out.println("END - obtenerUsuario por nombre de usuario");
		return user;
	}
}
