package server.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import client.exceptions.JugadorNoExisteException;
import server.domain.FTERD;
import server.domain.Jugador;
import server.domain.Tablero9x9;
import server.persistence.DAOTablero;

/**
 * Test unitario
 * Este clase comprueba que en el servidor se crea la partida cuando a una solicitud
 * de reto se contesta afirmativamente
 * 
 * Notar que no intervienen clientes remotos. No se prueba la comunicaciOn*/

public class TestDAOCrearPartida extends TestCase{
	
	FTERD fachada = FTERD.get();
	Jugador jA = new Jugador("jose.stalin@pcus.urrs","passwd1");
	Jugador jB = new Jugador("adol.hitler@nsdap.ger","passwd2");
	
	Jugador x;

	@Before
	public void setUp(){
		FuncionesAuxiliaresTests.borrarBD();
	}

	@Test
	public void test1CrearPartida() throws ClassNotFoundException, SQLException {
		
		jA.insert();
		jB.insert();

		try {
			fachada.add("jose.stalin@pcus.urrs","passwd1");
		} catch (JugadorNoExisteException e1) {
			fail("NO se esperaba excepcion");
		}
		try {
			fachada.add("adol.hitler@nsdap.ger","passwd2");
		} catch (JugadorNoExisteException e1) {
			fail("NO se esperaba excepcion");
		}
		
		
		fachada.retar("jose.stalin@pcus.urrs", "adol.hitler@nsdap.ger");	
		fachada.respuestaAPeticionDeReto("jose.stalin@pcus.urrs", "adol.hitler@nsdap.ger", true);
		
		
		try{
			int id = FuncionesAuxiliaresTests.getIdPartida(fachada);
			assertTrue(DAOTablero.existeTablero(id)==true);
		}
		catch(Exception e){
			fail("No esperaba fallar obteniendo el id del tablero en la bd");
		}
		finally{
			jA.delete();
			jB.delete();
		}
	
	}
	
}
