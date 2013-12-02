package esi.uclm.iso.ultimate_tttoe.dominio.test;

import static org.junit.Assert.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import esi.uclm.iso.ultimate_tttoe.dominio.FTERD;
import esi.uclm.iso.ultimate_tttoe.dominio.Jugador;
import esi.uclm.iso.ultimate_tttoe.persistencia.Broker;

public class testJugador {

	@Test
public void testInsertar() {
		Jugador j = new Jugador ("macario7.polo@uclm.es");
		try{
		 
			j.insert();
		}catch(Exception e){
			fail("No se esperaba exception" +e.toString());
		}
		
		
	}
	@Test
	public void testEliminar(){
		Jugador j = new Jugador ("macario7.polo@uclm.es");
		try{
			j.delete();		

		}catch(Exception e){
			fail("No se esperaba exception" +e.toString());
		}
	}
	
	@Test
	public void testAutenticar() throws ClassNotFoundException, SQLException{
		Jugador j = new Jugador("javiMarchan@uclm.es");
		j.insert();
		FTERD fachada = new FTERD();
		try{
			Jugador respuesta = fachada.autenticar(j.getEmail());
			assertTrue(j.getEmail().equals(respuesta.getEmail()));
			j.delete();
		}
		catch(Exception e){
			j.delete();
			fail("No se esperaba exception" + e.toString());
		}
		
		
		
	}
	
/*	public void tearDown(){
		
		Connection bd = Broker.getBD();
		CallableStatement cs = bd.prepareCall("{call insert_player(?)}");
		cs.setString(1, jugador.getEmail());
		
		cs.executeUpdate();
		bd.close();
	}*/
	
	

}
