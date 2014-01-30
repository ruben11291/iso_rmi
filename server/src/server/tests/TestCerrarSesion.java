package server.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import server.domain.FTERD;
import server.domain.Jugador;
import client.exceptions.JugadorNoExisteException;

public class TestCerrarSesion {

	@Before
	public void setUp(){
		FuncionesAuxiliaresTests.borrarBD();
	}
	
	@Test
	public void testCerrarSesion() {
		
		FTERD fachada = FTERD.get();
		Jugador jugadorA = new Jugador("jose.stalin@pcus.urrs", "jose");
		
		// Se crea un jugador y se inserta
		try {
			jugadorA.insert();
			fachada.add(jugadorA.getEmail(), "jose");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (JugadorNoExisteException jne){
			fail("Esperaba autenticar");
		}
		
		// Se desconecta
		fachada.delete(jugadorA.getEmail());
		assertTrue(fachada.getJugadores().isEmpty());
		
	}

}
