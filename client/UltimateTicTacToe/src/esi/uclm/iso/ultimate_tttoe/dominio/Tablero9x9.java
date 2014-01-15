package esi.uclm.iso.ultimate_tttoe.dominio;

import esi.uclm.iso.ultimate_tttoe.excepciones.CoordenadasNovalidasException;

public class Tablero9x9 {

	private Jugador jugadorConElTurno;
	private int ultimoValor;
	private Tablero3x3[][] tablerillos;
	private Jugador jugadorA, jugadorB ;
	
	public Tablero9x9(){
		this.ultimoValor =+ 1;
		this.tablerillos = new Tablero3x3[3][3];
		for(int fila=0; fila<3; fila++){
			for(int col=0; col<3; col++){
				this.tablerillos[col][fila] = new Tablero3x3();
			}
		}
		this.jugadorA= null;
		this.jugadorB = null;
	}

	public Jugador getJugadorConElTurno() {
		return this.jugadorConElTurno;
	}
	
	public void colocar(int cT, int fT, int cC, int fC) throws CoordenadasNovalidasException{
		if (cT<0 || cT>2 || fT<0 || fT>2 || cC<0 || cC>2 || fC<0 || fC>2)
			throw new CoordenadasNovalidasException(cT, fT, cC, fC);
		
		Tablero3x3 tablerillo = this.tablerillos[cT][fT];
		tablerillo.colocar(cC, fC, this.ultimoValor*-1);
		this.ultimoValor =- this.ultimoValor;
	}

	public void setJugadorConelTurno(Jugador jugador) {
		this.jugadorConElTurno = jugador;
		
	}

	public void setJugadorA(Jugador a) {
		this.jugadorA = a;
		
	}
	
	public void setJugadorB(Jugador b) {
		this.jugadorB = b;
		
	}

	public Jugador getJugadorA() {
		return jugadorA;
	}

	public Jugador getJugadorB() {
		return jugadorB;
	}
/*
	public String toString(){
		String r = "";
		for (int fila=0; fila<3; fila++){
			for(int col=0; col<3; col++){
				r+=this.casillas[col][fila].getValor();
			}
		}
	}
	*/
	public String toString(){
		String r ="";
		for (int fila=0; fila<3; fila++){
			for(int col=0; col<3; col++){
				r+=this.tablerillos[col][fila].toString();
			}
		}
		
		return r;
	}
}
