package test;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import walterServerCliente.JuegoDaoImp;
import walterServerCliente.User;
 
public class TestBaseDeDatos {

	JuegoDaoImp db = new JuegoDaoImp();
	@Before  
	public void inicio(){
		db.registrarUsuario("toni", "lalala");
	    db.registrarUsuario("romina", "lololo");
		db.registrarUsuario("walter", "pro");
		db.registrarUsuario("esteban", "pro");
	}
	@Test
	public void testPrintMessage() {	
		assertFalse(db.registrarUsuario("toni", "lalala"));
		assertFalse(db.registrarUsuario("romina", "lololo"));
		assertFalse(db.registrarUsuario("walter", "pro"));
		assertFalse(db.registrarUsuario("esteban", "pro"));
    }
	
	@Test
	public void testValidacionParaLoguearAceptadas(){
		assertTrue(db.validaUsuario("esteban", "pro"));
	}
	
	@Test
	public void testValidacionParaLoguearNegadas(){
		assertFalse(db.validaUsuario("esteban", "BORACHITO"));
		assertFalse(db.validaUsuario("TOBAL", "pro"));
	}
	
	@Test
	public void testSeObtienenTodosLosUsuario(){
		List<User> userList = db.obtenerUsuarios();
		assertFalse(userList.isEmpty());
	}

	@Test
	public void  testObtenerScore(){
		 db.obtenerUsuario("romina");
	 	 db.obtenerScores(2);
	}

	@Test
	public void  testObtenerScoreDeUsuario(){
		 Integer score = db.obtenerScorePorUsuario("romina",1);
		 long value = 20;
		 assertEquals(value, score.longValue());
	}
	
	@Test
	public void  testActualizarUsuarioYpass(){
		 assertFalse(db.registrarUsuario("calamaro", "cal"));
		 assertTrue(db.actualizarUsuario("calamaro","calamaro","cal1"));
	}
}
