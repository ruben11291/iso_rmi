package server.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import server.domain.FTERD;
import server.domain.Jugador;
import client.exceptions.*;
import server.persistence.DAOTablero;

public class TestDAOMovimiento {

	@Test
	public void testCrearPartida() throws NoTienesElTurnoException, JugadorNoExisteException, NoEstaJugandoException, CoordenadasNoValidasException, ClassNotFoundException, SQLException {
		FTERD fachada = FTERD.get();
		int valor;
		String consulta;
		/* Se prepara la partida*/
		Jugador jugadorA = new Jugador("jose.stalin@pcus.urrs");
		Jugador jugadorB = new Jugador("adol.hitler@nsdap.ger");
		
		jugadorA.insert();
		jugadorB.insert();
		
		fachada.add(jugadorA);
		fachada.add(jugadorB);
		
		fachada.retar(jugadorA.getEmail(), jugadorB.getEmail());
		assertTrue(fachada.getRetosEnEspera().size() == 1); //¿Hay que volver a hacer estas aserciones (para eso esta el test TestReto)?
		
		fachada.respuestaAPeticionDeReto(jugadorA.getEmail(), jugadorB.getEmail(), true);
		assertTrue(fachada.getRetosEnEspera().isEmpty() == true); //¿Hay que volver a hacer estas aserciones (para eso esta el test TestReto)?

		int idPartida = FuncionesAuxiliaresTests.getIdPartida(fachada);
		
		fachada.poner(idPartida, jugadorA.getEmail(), 0,0,0,0);
		
		consulta = "select * from Casilla where idTablero=(select Tablero3x3.idTablero from Tablero3x3 where fila=0 and col=0) and fila=0 and col=0;";
		valor = DAOTablero.consultarCasilla(consulta);
		assertTrue(1==valor);
		
		fachada.poner(idPartida, jugadorB.getEmail(), 0,0,0,1);
		
		consulta = "select * from Casilla where idTablero=(select Tablero3x3.idTablero from Tablero3x3 where fila=0 and col=0) and fila=0 and col=1;";
		valor = DAOTablero.consultarCasilla(consulta);
		assertTrue(-1==valor);
		
		
	}

}
