package server.domain;

import java.sql.SQLException;

import server.persistence.DAOTablero;

public class Tablero9x9 {

	/*
	 * Jugador A juega con +1
	 * Jugador B juega con -1
	 * */
	private Jugador jugadorA, jugadorB;
	private int ultimoValor;
	private Tablero3x3[][] subTableros;
	private int id;
	
	public Tablero9x9() {
		this.id=Math.abs((new java.util.Random()).nextInt());
		this.ultimoValor=+1;
		this.subTableros=new Tablero3x3[3][3];
		for (int fila=0; fila<3; fila++) {
			for (int col=0; col<3; col++) {
				this.subTableros[col][fila]=new Tablero3x3();
			}
		}
		this.jugadorA=null;
		this.jugadorB=null;
	}


	/**
	 * cT = columna Tablero
	 * fT = fila Talbero
	 * cC = columna Casilla
	 * fC = fila Casilla
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 *  */
	
	public void colocar(String email, int cT, int fT, int cC, int fC)throws SQLException, ClassNotFoundException {
		
		int ficha = 0;
	
		//Quien ha hecho el movimiento?
		if (this.jugadorA.getEmail().equals(email))
			ficha = 1;
		if (this.jugadorB.getEmail().equals(email))
			ficha = -1;
		
		Tablero3x3 tablerillo=this.subTableros[cT][fT];
		int idCasilla = tablerillo.colocar(cC, fC, ficha);
		DAOTablero.nuevoMovimiento(email, idCasilla, ficha);

	}

	public void setJugadorA(Jugador a) {
		this.jugadorA=a;
	}

	public void setJugadorB(Jugador b) {
		this.jugadorB=b;
	}

	public Jugador getJugadorA() {
		return this.jugadorA;
	}
	
	public Jugador getJugadorB() {
		return this.jugadorB;
	}
	
	public Jugador getOpenenteDE(String email){
		Jugador oponente = null;
		if (this.jugadorA.getEmail().equals(email))
			oponente = this.jugadorB;
		if (this.jugadorB.getEmail().equals(email))
			oponente = this.jugadorA;
		return oponente;
	}
	public String getCadena() {
		String r="";
		for (int fila=0; fila<3; fila++) {
			for (int col=0; col<3; col++) {
				r+=this.subTableros[col][fila].toString();
			}
		}
		return r;
	}
	
	@Override
	public String toString() {
		String r="Jugadores: " + this.jugadorA + " VS " + this.jugadorB + " \n"; 
		r+=this.getCadena();
		return r;
	}

	public int getId() {
		return this.id;
	}
	
	public Tablero3x3[][] getSubTableros() {
		return subTableros;
	}
}