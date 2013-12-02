package server.domain;

import server.exportable.exceptions.CoordenadasNoValidasException;

public class Tablero9x9 {
	private Jugador jugadorConElTurno;
	private Jugador jugadorA, jugadorB;
	private int ultimoValor;
	private Tablero3x3[][] tablerillos;
	private int id;
	
	public Tablero9x9() {
		this.id=Math.abs((new java.util.Random()).nextInt());
		this.ultimoValor=+1;
		this.tablerillos=new Tablero3x3[3][3];
		for (int fila=0; fila<3; fila++) {
			for (int col=0; col<3; col++) {
				this.tablerillos[col][fila]=new Tablero3x3();
			}
		}
		this.jugadorA=null;
		this.jugadorB=null;
	}

	public Jugador getJugadorConElTurno() {
		return this.jugadorConElTurno;
	}

	public void colocar(int cT, int fT, int cC, int fC) throws CoordenadasNoValidasException {
		if (cT<0 || cT>2 || fT<0 || fT>2 || cC<0 || cC>2 || fC<0 || fC>2)
			throw new CoordenadasNoValidasException(cT, fT, cC, fC);
		Tablero3x3 tablerillo=this.tablerillos[cT][fT];
		tablerillo.colocar(cC, fC, this.ultimoValor*-1);
		cambiarTurno();
	}

	private void cambiarTurno() {
		this.ultimoValor=-this.ultimoValor;
		this.jugadorConElTurno = this.jugadorConElTurno==this.jugadorA ? this.jugadorB : this.jugadorA; 
	}

	public void setJugadorConElTurno(Jugador jugador) {
		this.jugadorConElTurno=jugador;
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
	
	public String getCadena() {
		String r="";
		for (int fila=0; fila<3; fila++) {
			for (int col=0; col<3; col++) {
				r+=this.tablerillos[col][fila].toString();
			}
		}
		return r;
	}
	
	@Override
	public String toString() {
		String r="Jugadores: " + this.jugadorA + " VS " + this.jugadorB + " (turno de: " + this.jugadorConElTurno + ")\n"; 
		r+=this.getCadena();
		return r;
	}

	public int getId() {
		return this.id;
	}
}
