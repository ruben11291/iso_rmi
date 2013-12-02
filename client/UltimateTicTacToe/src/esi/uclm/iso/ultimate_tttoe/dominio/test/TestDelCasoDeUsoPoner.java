package esi.uclm.iso.ultimate_tttoe.dominio.test;

import static org.junit.Assert.*;

import org.junit.Test;

import esi.uclm.iso.ultimate_tttoe.dominio.FTERD;
import esi.uclm.iso.ultimate_tttoe.dominio.Jugador;
import esi.uclm.iso.ultimate_tttoe.dominio.Tablero9x9;
import esi.uclm.iso.ultimate_tttoe.excepciones.NoTienesElTurnoException;
import esi.uclm.iso.ultimate_tttoe.excepciones.RetoNoAceptadoException;

public class TestDelCasoDeUsoPoner {

	@Test
	public void test1() {
		FTERD fachada = new FTERD();
		Jugador pepe = new Jugador("pepe@gmail.com");
		Jugador ana  = new Jugador("ana@gmail.com");
		fachada.add(pepe);
		fachada.add(ana);
		try{
		fachada.solicitudDeJuego(pepe,ana);
		}catch(RetoNoAceptadoException e){
			fail("No se esperaba RetoNoAceptadoException");
		}
		fachada.unirAPartida(ana, pepe);
		
		assertTrue(fachada.getJugadores().size() == 2);
		assertTrue(fachada.getTableros().size() == 1);
		
		Tablero9x9 t = fachada.getTableros().get(pepe.getEmail());
		assertSame(t.getJugadorA(), pepe);
		assertSame(t.getJugadorB(), ana);
		assertSame(t.getJugadorA(), t.getJugadorConElTurno());
	    String cadena81 = "00000000000000000000000000000000000"
	    		+ "0000000000000000000000000000000000000000000000";
	    assertTrue(t.toString().equals(cadena81));
	   
	    try{
	    	fachada.poner("ana@gmail.com",0,0,0,0);
	    } catch (NoTienesElTurnoException e) {
	    } catch (Exception e) {
	    	fail("Se esperaba NoTienesElTurnoException y se ha lanzado"
	    			+ e.toString());
	    }
	    
	    /*
	    try{
	    	fachada.poner("pepe@gmail.com",0,0,0,0);
		    String cadena81 = "10000000000000000000000000000000000"
		    		+ "0000000000000000000000000000000000000000000000";
		    assertTrue
		*/
		
		
	}

}

