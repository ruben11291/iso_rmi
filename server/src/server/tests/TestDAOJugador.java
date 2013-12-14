package server.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.junit.Test;

import server.domain.FTERD;
import server.domain.Jugador;
import server.domain.Tablero9x9;

public class TestDAOJugador {

	Jugador j = new Jugador ("jose.stalin@uclm.es");
	@Test
	public void test1InsertarYEliminar() {
//		Jugador j = new Jugador ("jose.stalin@uclm.es");
		try{
			j.insert();
			j.delete();
		}catch(Exception e){
			fail("No se esperaba exception" +e.toString());
		}	
		
	}
		
	@Test
	public void test2Autenticar() throws ClassNotFoundException, SQLException{
		Jugador j = new Jugador("javierMarchan@uclm.es");
		FTERD fachada = FTERD.get();
		try{
			j.insert();
			Jugador respuesta = fachada.autenticar(j.getEmail());
			assertTrue(j.getEmail().equals(respuesta.getEmail()));
			
			Hashtable<String, Jugador> jugadores = fachada.getJugadores();
			assertTrue(jugadores.containsKey("javierMarchan@uclm.es"));
			
			j.delete();
		}
		catch(Exception e){
			j.delete();
			fail("No se esperaba exception" + e.toString());
		}
		
		
		
	}

}
