package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.domain.Tablero3x3;

public class Tablero3x3Test {

	Tablero3x3 tablero;
	@Before
	public void setUp(){
		tablero = new Tablero3x3();
	}
	@Test
	public void ConstructorTest() {
		for(int i = 0; i< tablero.getCasillas().length;i++)
			for(int j=0;j<tablero.getCasillas()[i].length;j++)
				assertTrue(tablero.getCasillas()[i][j].getValor() == 0);
		assertTrue(tablero.getVencedor() == "");
		assertTrue(tablero.getEmpate()==false);
	}
	
	public void ColocarTest(){
		tablero.colocar(0, 0, 1);
		assertTrue(tablero.getCasillas()[0][0].getValor() == 1);
		tablero.colocar(4, 5, 1);
		assertTrue(tablero.getCasillas()[4][5].getValor() == 1);
		tablero.colocar(0, 0, 0);
		assertTrue(tablero.getCasillas()[0][0].getValor() == 0);
		tablero.colocar(4, 5, 0);
		assertTrue(tablero.getCasillas()[4][5].getValor() == 0);
	}
	@Test
	public void isFullTest(){
		for(int i = 0; i< tablero.getCasillas().length;i++)
			for(int j=0;j<tablero.getCasillas()[i].length;j++)
				tablero.colocar(i, j, 1);
		for(int i = 0; i< tablero.getCasillas().length;i++)
			for(int j=0;j<tablero.getCasillas()[i].length;j++)
				assertTrue(tablero.getCasillas()[i][j].getValor() == 1);
		
		for(int i = 0; i< tablero.getCasillas().length;i++)
			for(int j=0;j<tablero.getCasillas()[i].length;j++)
				tablero.colocar(i, j, -1);
		
		for(int i = 0; i< tablero.getCasillas().length;i++)
			for(int j=0;j<tablero.getCasillas()[i].length;j++)
				assertTrue(tablero.getCasillas()[i][j].getValor() == -1);
	}
	@Test
	public void ComprobarVencedorTest(){
		String jugadorA = "j1",jugadorB= "j2";
		this.tablero = new Tablero3x3();
		for(int i = 0; i< 3; i++)
				tablero.colocar(i, 0, 1);
		tablero.comprobarVencedor(jugadorA, jugadorB);
		assertTrue(tablero.getVencedor().equals(jugadorA));
		assertFalse(tablero.getVencedor().equals(jugadorB));
		assertFalse(tablero.getVencedor().equals(""));

		this.tablero = new Tablero3x3();
		for(int i = 0; i< 3; i++)
				tablero.colocar(i, 0, -1);
		tablero.comprobarVencedor(jugadorA, jugadorB);
		assertTrue(tablero.getVencedor().equals(jugadorB));
		assertFalse(tablero.getVencedor().equals(jugadorA));
		assertFalse(tablero.getVencedor().equals(""));

		this.tablero = new Tablero3x3();
		for(int i = 0; i< 3; i++)
				tablero.colocar(i, 0, 0);
		tablero.comprobarVencedor(jugadorA, jugadorB);
		assertTrue(tablero.getVencedor().equals(""));
		assertFalse(tablero.getVencedor().equals(jugadorA));
		assertFalse(tablero.getVencedor().equals(jugadorB));

		this.tablero = new Tablero3x3();
		for(int i = 0; i< 3; i++)
				tablero.colocar(i, 0, 0);
		tablero.comprobarVencedor(jugadorA, jugadorB);//Horizontales
		assertTrue(tablero.getVencedor().equals(""));
		assertFalse(tablero.getVencedor().equals(jugadorA));
		assertFalse(tablero.getVencedor().equals(jugadorB));

		this.tablero = new Tablero3x3();
		for(int i = 0; i< 3; i++)
				tablero.colocar(i, i, 1);//Diagonal
		tablero.comprobarVencedor(jugadorA, jugadorB);
		assertTrue(tablero.getVencedor().equals(jugadorA));
		assertFalse(tablero.getVencedor().equals(jugadorB));
		assertFalse(tablero.getVencedor().equals(""));

		this.tablero = new Tablero3x3();
		for(int i = 0; i< 3; i++)
				tablero.colocar(i, i, -1);//Diagonal
		tablero.comprobarVencedor(jugadorA, jugadorB);
		assertTrue(tablero.getVencedor().equals(jugadorB));
		assertFalse(tablero.getVencedor().equals(jugadorA));
		assertFalse(tablero.getVencedor().equals(""));

		
		this.tablero = new Tablero3x3();
		for(int i = 0; i< 3; i++)
				tablero.colocar(i, i, -1);//Diagonal
		tablero.colocar(0, 2, 1);
		tablero.colocar(1, 0, 1);
		tablero.comprobarVencedor(jugadorA, jugadorB);
		assertTrue(tablero.getVencedor().equals(jugadorB));
		assertFalse(tablero.getVencedor().equals(jugadorA));
		assertFalse(tablero.getVencedor().equals(""));

		
		this.tablero = new Tablero3x3();
		for(int i = 0; i< 3; i++)
				tablero.colocar(0, i, 0);//vertical
		tablero.comprobarVencedor(jugadorA, jugadorB);
		assertTrue(tablero.getVencedor().equals(""));
		assertFalse(tablero.getVencedor().equals(jugadorA));
		assertFalse(tablero.getVencedor().equals(jugadorB));

		this.tablero = new Tablero3x3();
		for(int i = 0; i< 3; i++)
				tablero.colocar(0, i, 1);
		tablero.comprobarVencedor(jugadorA, jugadorB);//Vertical
		assertTrue(tablero.getVencedor().equals(jugadorA));
		assertFalse(tablero.getVencedor().equals(""));
		assertFalse(tablero.getVencedor().equals(jugadorB));
		
		this.tablero = new Tablero3x3();
		for(int i = 0; i< 3; i++)
				tablero.colocar(0, i, -1);
		tablero.comprobarVencedor(jugadorA, jugadorB);//Horizontales
		assertTrue(tablero.getVencedor().equals(jugadorB));
		assertFalse(tablero.getVencedor().equals(""));
		assertFalse(tablero.getVencedor().equals(jugadorA));
	}

}
