package server.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import client.exceptions.JugadorNoExisteException;
import server.domain.FTERD;
import server.domain.Jugador;
import server.persistence.DAOTablero;

public class TestDAOMovimiento {
	
	@Before
	public void setUp(){
		FuncionesAuxiliaresTests.borrarBD();
	}

	@Test
	public void testCrearPartida() {
		FTERD fachada = FTERD.get();
		int valor;
		String consulta;
		/* Se prepara la partida*/
		Jugador jugadorA = new Jugador("jose.stalin@pcus.urrs", "jose");
		Jugador jugadorB = new Jugador("adol.hitler@nsdap.ger", "adol");
		
		try {
			jugadorA.insert();
			jugadorB.insert();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			fachada.add(jugadorA.getEmail(), "jose");
			fachada.add(jugadorB.getEmail(), "adol");
		}
		catch (JugadorNoExisteException jne){
			fail("Esperaba autenticar");
		}
		
		
		fachada.retar(jugadorA.getEmail(), jugadorB.getEmail());
		assertTrue(fachada.getRetosEnEspera().size() == 1); //¿Hay que volver a hacer estas aserciones (para eso esta el test TestReto)?
		
		fachada.respuestaAPeticionDeReto(jugadorA.getEmail(), jugadorB.getEmail(), true);
		assertTrue(fachada.getRetosEnEspera().isEmpty() == true); //¿Hay que volver a hacer estas aserciones (para eso esta el test TestReto)?

		int idPartida = FuncionesAuxiliaresTests.getIdPartida(fachada);
		
		fachada.poner(idPartida, jugadorA.getEmail(), 0,0,0,0);
		
		consulta = "select * from Casilla where idTablero=(select Tablero3x3.idTablero from Tablero3x3 where fila=0 and col=0) and fila=0 and col=0;";
		try {
			valor = DAOTablero.consultarCasilla(consulta);
			assertTrue(1==valor);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("No esperaba excepcion "+e.toString());
		}
		
		
		fachada.poner(idPartida, jugadorB.getEmail(), 0,0,0,1);
		
		consulta = "select * from Casilla where idTablero=(select Tablero3x3.idTablero from Tablero3x3 where fila=0 and col=0) and fila=0 and col=1;";
		try {
			valor = DAOTablero.consultarCasilla(consulta);
			assertTrue(-1==valor);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			fail("No esperaba excepcion "+e.toString());
		}
		
		
		
	}

}