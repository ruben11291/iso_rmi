package server.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import client.exceptions.JugadorNoExisteException;
import server.domain.FTERD;
import server.domain.Jugador;
import server.domain.Tablero9x9;

public class TestDAOJugador {
	
	@Before
	public void setUp(){
		FuncionesAuxiliaresTests.borrarBD();
	}
	
	@Test
	public void test1InsertarYEliminar() {
		Jugador j = new Jugador ("jose.stalin@uclm.es","jose");
		try{
			j.insert();
			j.delete();
		}catch(Exception e){
			fail("No se esperaba exception" +e.toString());
		}	
		
	}
		
	@Test
	public void test2AutenticarYCerrarSesion() throws ClassNotFoundException, SQLException{
		Jugador j = new Jugador("javierMarchan@uclm.es","javi");
		FTERD fachada = FTERD.get();
		try{
			j.insert();
			fachada.add(j.getEmail(),"javi");
			
			Hashtable<String, Jugador> jugadores = fachada.getJugadores();
			assertTrue(jugadores.containsKey("javierMarchan@uclm.es"));
			
		}
		catch (JugadorNoExisteException jne){
			fail("Esperaba autenticar");
		}
		catch(Exception e){
			j.delete();
			fail("No se esperaba exception" + e.toString());
		}
		
		fachada.delete(j.getEmail());
		Hashtable<String, Jugador> jugadores = fachada.getJugadores();
		assertTrue(jugadores.isEmpty());
		
		j.delete();
	}
	
	
	@Test
	public void test3AutenticarDosVeces() throws ClassNotFoundException, SQLException{
		Jugador j = new Jugador("javierMarchan@uclm.es","javi");
		FTERD fachada = FTERD.get();
		try{
			j.insert();
			fachada.add(j.getEmail(),"javi");
			
			Hashtable<String, Jugador> jugadores = fachada.getJugadores();
			assertTrue(jugadores.containsKey("javierMarchan@uclm.es"));
		}
		catch (JugadorNoExisteException jne){
			fail("Esperaba autenticar");
		}
		catch(Exception e){
			j.delete();
			fail("No se esperaba exception" + e.toString());
		}
		
		// Se intenta autenticar otra vez con el mismo usuario
		try {
			fachada.add(j.getEmail(), j.getPasswd());
			fail("Esperaba fallar al autenticar dos veces con el mismo usuario");
		} catch (JugadorNoExisteException e) {
			Hashtable<String, Jugador> jugadores = fachada.getJugadores();
			assertTrue(jugadores.containsKey("javierMarchan@uclm.es"));
			assertTrue(jugadores.size()==1);
		}
			
	}
	
	

}
