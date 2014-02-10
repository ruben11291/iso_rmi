package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import client.domain.Jugador;
import client.domain.Tablero9x9;
import client.exceptions.CasillaOcupadaException;
import client.exceptions.CoordenadasNoValidasException;
import client.exceptions.MovimientoNoValidoException;
import client.exceptions.NoEstaJugandoException;
import client.exceptions.NoTienesElTurnoException;
import client.exceptions.PartidaFinalizadaException;
import client.exceptions.TableroEmpateException;
import client.exceptions.TableroGanadoException;

public class JugadorTest {
	
	Jugador j1 = new Jugador("pep","pepe");
	Jugador j2 = new Jugador("juan","juan");
	Tablero9x9 tablero;
	
	@Before
	public void setUp(){
		tablero = new Tablero9x9();
		j1.setTablero(tablero);
		j2.setTablero(tablero);
		tablero.setJugadorA(j1);
		tablero.setJugadorB(j2);
		tablero.setJugadorConelTurno(j1);
	}
	
	@Test
	public void NoTienesElTurnoTest() {
		try{
			try {
				j2.poner(0, 0, 0, 0);
				fail("Se esperaba excepción");
			} catch (NoEstaJugandoException | CoordenadasNoValidasException
					| MovimientoNoValidoException | PartidaFinalizadaException
					| CasillaOcupadaException | TableroGanadoException
					| TableroEmpateException e) {
				fail("No se esperaba excepción");
			}
		} catch(NoTienesElTurnoException e){
			assertTrue(tablero.getJugadorConElTurno().equals(j1));
			try {
				j1.poner(0, 0, 0, 0);
			} catch (NoTienesElTurnoException | NoEstaJugandoException
					| CoordenadasNoValidasException
					| MovimientoNoValidoException | PartidaFinalizadaException
					| CasillaOcupadaException | TableroGanadoException
					| TableroEmpateException e1) {
				fail("No se esperaba excepción");
			}
			assertTrue(tablero.getJugadorConElTurno().equals(j2));
		}
	}
	
	@Test
	public void NoEstaJugandoExceptionTest(){
		j2.setTablero(null);
		try{
			try {
				j2.poner(0, 0, 0, 0);
			} catch (NoTienesElTurnoException | CoordenadasNoValidasException
					| MovimientoNoValidoException | PartidaFinalizadaException
					| CasillaOcupadaException | TableroGanadoException
					| TableroEmpateException e) {
				fail("No deberia de haber excepcion");
			}
		}catch(NoEstaJugandoException e){
			assertTrue(tablero.getJugadorA().equals(j1));
			assertTrue(tablero.getJugadorB().equals(j2));
		}
		
	}
	
}
