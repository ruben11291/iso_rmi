package client.tests;

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

public class Tablero9x9Test {

	Jugador j1 = new Jugador("pepe@gmail.com","pepe");
	Jugador j2 = new Jugador("juan@gmail.com","juan");
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
	public void TableroPequenyoGanadoTest() {
		try {
			
			j1.poner(0, 0, 0, 0);// poner(cTGrande,filaTGrande,cTpequenyo,filaTpequenyio)
			j2.poner(0, 0, 1, 0);
			j1.poner(1, 0, 0, 0);
			j2.poner(0, 0, 2, 1);
			j1.poner(2, 1, 0, 0);
			j2.poner(0, 0, 1, 1);
			j1.poner(1, 1, 1, 1);
			j2.poner(1, 1, 1, 0);
			j1.poner(1, 0, 2, 0);
			j2.poner(2, 0, 1, 0);
			j1.poner(1, 0, 1, 0);
			
			fail("No debería de llegar");
		} catch (NoTienesElTurnoException | NoEstaJugandoException
				| CoordenadasNoValidasException | MovimientoNoValidoException
				| PartidaFinalizadaException | CasillaOcupadaException
				| TableroEmpateException e) {
			
		}catch (TableroGanadoException e) {
			tablero.comprobarVencedor(j1.getEmail(), j2.getEmail());
			assertTrue(tablero.getVencedor().equals(""));
			assertTrue(e.getEmail().equals(j1.getEmail()));
			assertTrue(e.getcT() == 1 && e.getfT() == 0);
			assertTrue(tablero.hayVencedor()==false);
			assertTrue(tablero.getJugadorConElTurno().equals(j2));
		}
		
	}
	
	@Test
	public void CoordenadasNoValidasTest(){
		try{
			assertTrue(tablero.getJugadorConElTurno().equals(j1));
			j1.poner(4, 0, 1, 0);
			fail("No debería llegar");
		}
		catch(CoordenadasNoValidasException e){
			tablero.comprobarVencedor(j1.getEmail(), j2.getEmail());
			assertTrue(tablero.getJugadorConElTurno().equals(j1));
			assertTrue(tablero.hayVencedor()==false);
			assertTrue(tablero.getVencedor().equals(""));

		}
			catch (NoTienesElTurnoException | NoEstaJugandoException
				| MovimientoNoValidoException
				| PartidaFinalizadaException | CasillaOcupadaException
				| TableroGanadoException | TableroEmpateException e) {
				fail("No debería saltar excepcion");
		}
		
		try{
			assertTrue(tablero.getJugadorConElTurno().equals(j1));
			j1.poner(-1, 4, 1, 0);
			fail("No debería llegar");
		}
		catch(CoordenadasNoValidasException e){
			tablero.comprobarVencedor(j1.getEmail(), j2.getEmail());
			assertTrue(tablero.getJugadorConElTurno().equals(j1));
			assertTrue(tablero.hayVencedor()==false);
			assertTrue(tablero.getVencedor().equals(""));

		}
		catch (NoTienesElTurnoException | NoEstaJugandoException
				| MovimientoNoValidoException
				| PartidaFinalizadaException | CasillaOcupadaException
				| TableroGanadoException | TableroEmpateException e) {
				fail("No debería saltar excepcion");
		}
	}
	
	@Test 
	public void CasillaOcupadaTest(){
		try{
			assertTrue(tablero.getJugadorConElTurno().equals(j1));
			j1.poner(0, 0, 0, 0);// poner(cTGrande,filaTGrande,cTpequenyo,filaTpequenyio)
			j2.poner(0, 0, 1, 0);
			j1.poner(0, 0, 0, 0);
			fail("No debería llegar");
		}
		catch(CasillaOcupadaException e){
			assertTrue(tablero.getJugadorConElTurno().equals(j1));
			assertTrue(tablero.hayVencedor()==false);
			assertTrue(tablero.getVencedor().equals(""));
		}
		catch (NoTienesElTurnoException | NoEstaJugandoException
				| CoordenadasNoValidasException
				| PartidaFinalizadaException |MovimientoNoValidoException
				| TableroGanadoException | TableroEmpateException e) {
				fail("No debería saltar excepcion");
		}
		
		try{
			assertTrue(tablero.getJugadorConElTurno().equals(j1));
			j1.poner(1, 0, 0, 0);// poner(cTGrande,filaTGrande,cTpequenyo,filaTpequenyio)
			j2.poner(0, 0, 0, 0);
			fail("No debería llegar");
		}
		catch(CasillaOcupadaException e){
			assertTrue(tablero.getJugadorConElTurno().equals(j2));
			assertTrue(tablero.hayVencedor()==false);
			assertTrue(tablero.getVencedor().equals(""));
		}
		catch (NoTienesElTurnoException | NoEstaJugandoException
				| CoordenadasNoValidasException
				| PartidaFinalizadaException |MovimientoNoValidoException
				| TableroGanadoException | TableroEmpateException e) {
				fail("No debería saltar excepcion");
		}
	}
	
	@Test 
	public void TableroEmpateTest(){
		try{
			j1.poner(0, 0, 0, 0);// poner(cTGrande,filaTGrande,cTpequenyo,filaTpequenyio)
			j2.poner(0, 0, 1, 0);
			j1.poner(1, 0, 0, 0);
			j2.poner(0, 0, 2, 1);
			j1.poner(2, 1, 0, 0);
			j2.poner(0, 0, 1, 1);
			j1.poner(1, 1, 1, 1);
			j2.poner(1, 1, 1, 0);
			j1.poner(1, 0, 2, 0);
			j2.poner(2, 0, 1, 0);
			j1.poner(1, 0, 1, 0);//aqui se gana el 1,0
			
			j2.poner(1, 0, 0, 1);
			j1.poner(0, 1, 0, 0);
			j2.poner(0, 0, 2, 2);
			j1.poner(2, 2, 0, 0);
			j2.poner(0, 0, 0, 2);
			j1.poner(0, 2, 0, 2);
			j2.poner(0, 2, 0, 0);
			j1.poner(0, 0, 2, 0);
			j2.poner(2, 0, 0, 0);
			j1.poner(0, 0, 1, 2);
			j2.poner(1, 2, 0, 0);
			j1.poner(0, 0, 0, 1);
				
		}
		catch(TableroEmpateException e){
			assertTrue(tablero.getJugadorConElTurno().equals(j2));
			assertTrue(tablero.hayVencedor()==false);
			assertTrue(tablero.getVencedor().equals(""));
			assertTrue(e.getcT()==0 && e.getfT()==0);
		}catch (TableroGanadoException e) {
			assertTrue(tablero.getJugadorConElTurno().equals(j2));
			assertTrue(e.getEmail().equals(j1.getEmail()));
			
		}catch (NoTienesElTurnoException | NoEstaJugandoException
				| CoordenadasNoValidasException
				| PartidaFinalizadaException |MovimientoNoValidoException
				| CasillaOcupadaException e) {
				fail("No debería saltar excepcion" +e);
		}
		
	}
	
	@Test
	public void NoTienesElTurnoTest(){
		assertTrue(tablero.getJugadorConElTurno().equals(j1));

		try{
				j2.poner(0, 0, 0, 0);
			
		}
		catch(NoTienesElTurnoException e){
			assertTrue(tablero.getJugadorConElTurno().equals(j1));
			assertTrue(tablero.hayVencedor()==false);
			assertTrue(tablero.getVencedor().equals(""));
		} catch (NoEstaJugandoException | CoordenadasNoValidasException
				| MovimientoNoValidoException | PartidaFinalizadaException
				| CasillaOcupadaException | TableroGanadoException
				| TableroEmpateException e) {
			fail("No debería saltar excepcion");
		}
	}
	

	
//	@Test
//	public void PartidaGanadaTest(){
//		try{
//			
//			j1.poner(0, 0, 0, 0);
//			
//			
//			
//			fail("No debería llegar aquí");
//			
//		}
//		catch(TableroGanadoException | TableroEmpateException e ){
//			assertTrue(tablero.hayVencedor() == false);
//		}
//		catch (PartidaFinalizadaException e) {
//			assertTrue(e.getEmpate()==false);
//			assertTrue(tablero.getVencedor().equals("alguno"));//TODO:
//		
//		}catch (NoEstaJugandoException | CoordenadasNoValidasException
//					| MovimientoNoValidoException | NoTienesElTurnoException
//					| CasillaOcupadaException  e) {
//				fail("No debería saltar excepcion");
//		}
//	}
	
	@Test
	public void PartidaGanadaTest(){
		try {
			this.GanarTablero(0, 0, j1);
			this.GanarTablero(0, 1, j1);
			this.GanarTablero(0, 2, j1);
			fail("Esperaba PartidaFinalizadaException");
		} catch (PartidaFinalizadaException e) {
			assertSame(e.getEmail(), j1.getEmail());
			assertSame(tablero.getVencedor(), e.getEmail());
		} catch (TableroGanadoException e) {
			assertSame(e.getEmail(), j1.getEmail());
		} catch (TableroEmpateException e) {
			fail("No esperaba empate");
		}
	}
	
	@Test
	public void PartidaEmpatadaTest(){

		/*  X  0  X
		 *  0  X  0
		 *  0  X  0
		 * */
		try {
			this.GanarTablero(0,0,j1);
			this.GanarTablero(0,1,j2);
			this.GanarTablero(0,2,j1);
			
			this.GanarTablero(1,0,j2);
			this.GanarTablero(1,1,j1);
			this.GanarTablero(1,2,j2);
			
			this.GanarTablero(2,0,j2);
			this.GanarTablero(2,1,j1);
			this.GanarTablero(2,2,j2);
			
		} catch (PartidaFinalizadaException e) {
			assertTrue(e.getEmpate());
		} catch (TableroEmpateException e) {
			fail("No esperaba empatar ningun tablerillo");
		} catch (TableroGanadoException e) {
		} 
	}
	
	private void GanarTablero(int fTablero, int cTablero, Jugador j) throws PartidaFinalizadaException, TableroGanadoException, TableroEmpateException{
		if (tablero.getJugadorConElTurno() != j) tablero.setJugadorConelTurno(j);
		for (int f=0, c=0; f<3; c++){
			tablero.colocar(fTablero, cTablero, 0, c); // pone j
			tablero.colocar(fTablero, cTablero, 1, c); // pone oponene a j. 
													   // La ultima no coloca porque salta TableroGanado
		}
	}

}
