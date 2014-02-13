package server.tests;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.Before;

import server.domain.FTERD;
import server.domain.Jugador;
import server.domain.Tablero9x9;
import client.exceptions.JugadorNoExisteException;

public class TestReto extends TestCase{
	
	FTERD fachada = FTERD.get();
	Jugador jugadorA = new Jugador("stalin@gmail.com","stalin");
	Jugador jugadorB = new Jugador("hitler@gmail.com","hitler");
	
	@Before
	public void setUp(){
		FuncionesAuxiliaresTests.borrarBD();

		/* Registrar a los jugadores*/
		try {
			jugadorA.insert();
			jugadorB.insert();
		} catch (ClassNotFoundException | SQLException e) {
			fail("No esperaba fallar al registrar los jugadores");
		}
		
		/* Iniciar sesion*/
		try {
			fachada.add(jugadorA.getEmail(), jugadorA.getPasswd());
			fachada.add(jugadorB.getEmail(), jugadorB.getPasswd());
		} catch (JugadorNoExisteException e) {
			fail("Esperaba que el jugador estuviese registrado");
		}

	}

	@org.junit.Test
	public void testRetarYAceptarReto() throws ClassNotFoundException, SQLException {
		
		fachada.retar(jugadorA.getEmail(), jugadorB.getEmail());
		assertTrue(fachada.getRetosEnEspera().size() == 1);
		
		fachada.respuestaAPeticionDeReto(jugadorA.getEmail(), jugadorB.getEmail(), true);
		assertTrue(fachada.getRetosEnEspera().isEmpty() == true);
		
		Hashtable<Integer, Tablero9x9> tableros = fachada.getTableros();
		Collection c = tableros.values();
		assertTrue(c.size() == 1);
		Iterator it  = c.iterator();
		try{
			Tablero9x9 partida = (Tablero9x9)it.next();
			assertTrue(partida.getJugadorA().getEmail().equals(jugadorA.getEmail()));
			assertTrue(partida.getJugadorB().getEmail().equals(jugadorB.getEmail()));
									
		}
		catch(Exception e){ //BUscar la excepcion de hashtable vacio
			fail("No esperaba fallar al obtener la partida");
		}
	}

	@org.junit.Test
	public void testRetarYRechazarReto() throws ClassNotFoundException, SQLException {
		fachada.retar(jugadorA.getEmail(), jugadorB.getEmail());
		assertTrue(fachada.getRetosEnEspera().size() == 1);
		
		fachada.respuestaAPeticionDeReto(jugadorA.getEmail(), jugadorB.getEmail(), false);
		assertTrue(fachada.getRetosEnEspera().isEmpty() == true);
		assertTrue(fachada.getTableros().size() == 0);
	}

}
