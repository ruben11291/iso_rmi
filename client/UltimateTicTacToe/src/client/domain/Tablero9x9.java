package client.domain;

import client.exceptions.CoordenadasNoValidasException;
import client.exceptions.MovimientoNoValidoException;
import client.exceptions.PartidaFinalizadaException;
import client.exceptions.TableroLlenoException;

public class Tablero9x9 {

	private Jugador jugadorConElTurno;
	private int ultimoValor;
	private Tablero3x3[][] tablerillos;
	private Jugador jugadorA, jugadorB, vencedor;
	private int last_cT, last_fT, last_cC, last_fC;
	
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

	public boolean hayVencedor() {
		boolean hay = false;
		if (this.vencedor != null)
			hay = true;
		return hay;
	}

	public void comprobarVencedor(Jugador a, Jugador b) {
		Jugador vencedor = null;
		if (!hayVencedor()) {
			for (int i = 0; i < 3; i++) {
				// Comprueba si hay alguna combinación ganadora vertical
				if (this.tablerillos[i][0].getVencedor().equals(this.tablerillos[i][1].getVencedor())
					&& this.tablerillos[i][1].getVencedor().equals(this.tablerillos[i][2].getVencedor())
					&& this.tablerillos[i][2].getVencedor() != null) {
					if (this.tablerillos[i][0].getVencedor().equals(this.jugadorA))
						vencedor = a;
					else
						vencedor = b;
					i = 4;
				// Comprueba si hay alguna combinación ganadora horizontal
				} else if (this.tablerillos[0][i].getVencedor().equals(this.tablerillos[1][i].getVencedor())
					&& this.tablerillos[1][i].getVencedor().equals(this.tablerillos[2][i].getVencedor())
					&& this.tablerillos[2][i].getVencedor() != null) {
					if (this.tablerillos[0][i].getVencedor().equals(this.jugadorA))
						vencedor = a;
					else
						vencedor = b;
					i = 4;
				}
			}
			// Comprueba si hay alguna combinación ganadora diagonal
			if (vencedor == null) {
				if (this.tablerillos[0][0].getVencedor().equals(this.tablerillos[1][1].getVencedor())
					&& this.tablerillos[1][1].getVencedor().equals(this.tablerillos[2][2].getVencedor())
					&& this.tablerillos[2][2].getVencedor() != null) {
					if (this.tablerillos[2][2].getVencedor().equals(this.jugadorA))
						vencedor = a;
					else
						vencedor = b;
				} else if (this.tablerillos[2][0].getVencedor().equals(this.tablerillos[1][1].getVencedor())
					&& this.tablerillos[1][1].getVencedor().equals(this.tablerillos[0][2].getVencedor())
					&& this.tablerillos[0][2].getVencedor() != null) {
					if (this.tablerillos[0][2].getVencedor().equals(this.jugadorA))
						vencedor = a;
					else
						vencedor = b;
				}
			}
		}
		setVencedor(vencedor);
	}
	
	public Jugador getVencedor() {
		return this.vencedor;
	}
	
	private void setVencedor(Jugador w) {
		this.vencedor = w;
	}
	
	
	public void colocar(int cT, int fT, int cC, int fC) throws CoordenadasNoValidasException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException {
		if (cT<0 || cT>2 || fT<0 || fT>2 || cC<0 || cC>2 || fC<0 || fC>2)
			throw new CoordenadasNoValidasException(cT, fT, cC, fC);
		if (this.last_cC != cT && this.last_fC != fT && this.last_cT != -1)
			if (!this.tablerillos[this.last_cC][this.last_fC].isFull())
				throw new MovimientoNoValidoException(cT, fT, cC, fC);
		if (this.tablerillos[cT][fT].isFull())
			throw new TableroLlenoException(cT, fT, cC, fC);
		if (this.vencedor != null)
			throw new PartidaFinalizadaException(this.vencedor);
		
		Tablero3x3 tablerillo = this.tablerillos[cT][fT];
		tablerillo.colocar(cC, fC, this.ultimoValor*-1);
		tablerillo.comprobarVencedor(this.jugadorA, this.jugadorB);
		
		this.last_cT = cT; this.last_fT = fT; this.last_cC = cC; this.last_fC = fC;
		this.ultimoValor =- this.ultimoValor;
		
		if (this.jugadorConElTurno.equals(this.jugadorA))
			this.jugadorConElTurno = this.jugadorB;
		else
			this.jugadorConElTurno = this.jugadorA;
	}

	public void setJugadorConelTurno(Jugador jugador) {
		this.jugadorConElTurno = jugador;
		
	}

	public void setJugadorA(Jugador a) {
		this.jugadorA = a;
		setJugadorConelTurno(this.jugadorA);
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
