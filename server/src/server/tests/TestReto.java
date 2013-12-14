package server.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import server.domain.FTERD;
import server.domain.Jugador;
import server.domain.Tablero9x9;

public class TestReto {

	@org.junit.Test
	public void testRetarYAceptarReto() throws ClassNotFoundException, SQLException {
		FTERD fachada = FTERD.get();
		Jugador jugadorA = new Jugador("stalin@gmail.com");
		Jugador jugadorB = new Jugador("hitler@gmail.com");
		
		/* Iniciar sesion*/
		fachada.add(jugadorA);
		fachada.add(jugadorB);
		
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
		FTERD fachada = FTERD.get();
		Jugador jugadorA = new Jugador("stalin@gmail.com");
		Jugador jugadorB = new Jugador("hitler@gmail.com");
		
		/* Iniciar sesion*/
		fachada.add(jugadorA);
		fachada.add(jugadorB);
		
		fachada.retar(jugadorA.getEmail(), jugadorB.getEmail());
		assertTrue(fachada.getRetosEnEspera().size() == 1);
		
		fachada.respuestaAPeticionDeReto(jugadorA.getEmail(), jugadorB.getEmail(), false);
		assertTrue(fachada.getRetosEnEspera().isEmpty() == true);
		assertTrue(fachada.getTableros().size() == 0);
		
	}
	
	
	


}
