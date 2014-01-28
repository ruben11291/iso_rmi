package test;

import static org.junit.Assert.*;

import org.junit.Test;

import client.communications.Cliente;
import client.domain.FTERD;
import client.domain.Jugador;
import client.domain.Tablero9x9;

public class FTERDTest {

	public FTERD fachada1;
	public FTERD fachada2;
	Jugador j1,j2;
	public void setUp(){
		try {
		 fachada1 = new FTERD();
		 fachada2 = new FTERD();
		  j1 = new Jugador("hitler@uclm.es","");
		  j2 = new Jugador("mussolini@uclm.es","");
		} catch (Exception e) {
			fail("No se esperaba excepcion");
		}
	}
	@Test
	public void testCrearPartida() {
		Cliente c1 = new Cliente(fachada1);
		Cliente c2 = new Cliente(fachada2);
		
		
		Tablero9x9 tablero = new Tablero9x9(1000);
		
		
		tablero.setJugadorA(j1);
		tablero.setJugadorB(j2);
		tablero.setJugadorConelTurno(j1);
		this.tableros.put(retador, tablero);
	}

}
