package client.domain;

import client.exceptions.CasillaOcupadaException;
import client.exceptions.CoordenadasNoValidasException;
import client.exceptions.MovimientoNoValidoException;
import client.exceptions.PartidaFinalizadaException;
import client.exceptions.TableroEmpateException;
import client.exceptions.TableroGanadoException;

public class Tablero9x9 {

	private Jugador jugadorConElTurno;
	private int ultimoValor;
	private Tablero3x3[][] tablerillos;
	private int id;
	private Jugador jugadorA, jugadorB;
	private String vencedor;
	private int last_cT, last_fT, last_cC, last_fC;
	
	public Tablero9x9(){
		this.ultimoValor = 1;
		this.tablerillos = new Tablero3x3[3][3];
		for(int fila=0; fila<3; fila++){
			for(int col=0; col<3; col++){
				this.tablerillos[col][fila] = new Tablero3x3();
			}
		}
		this.jugadorA= null;
		this.jugadorB = null;
		this.last_cT = this.last_fT = this.last_cC = this.last_fC = -1;
		this.vencedor = "";
	}

	public Tablero9x9(int idPartida) {
		this.id = idPartida;
		this.ultimoValor = 1;
		this.tablerillos = new Tablero3x3[3][3];
		for(int fila=0; fila<3; fila++){
			for(int col=0; col<3; col++){
				this.tablerillos[col][fila] = new Tablero3x3();
			}
		}
		this.jugadorA= null;
		this.jugadorB = null;
		this.last_cT = this.last_fT = this.last_cC = this.last_fC = -1;
		this.vencedor = "";
		
	}

	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public Jugador getJugadorConElTurno() {
		return this.jugadorConElTurno;
	}

	public boolean hayVencedor() {
		boolean hay = false;
		if (!this.vencedor.equals(""))
			hay = true;
		return hay;
	}

	public void comprobarVencedor(String a, String b) {
		String vencedor = "";
		
		if (!hayVencedor()) {
			for (int i = 0; i < 3; i++) {
				// Comprueba si hay alguna combinación ganadora vertical
				if ((this.tablerillos[i][0].getVencedor().equals(a) || tablerillos[i][0].getEmpate()) && 
						(this.tablerillos[i][1].getVencedor().equals(a) || tablerillos[i][1].getEmpate()) &&
						(this.tablerillos[i][2].getVencedor().equals(a) || tablerillos[i][2].getEmpate())) {
						vencedor = a;
				} else if ((this.tablerillos[i][0].getVencedor().equals(b) || tablerillos[i][0].getEmpate()) && 
						(this.tablerillos[i][1].getVencedor().equals(b) || tablerillos[i][1].getEmpate()) &&
						(this.tablerillos[i][2].getVencedor().equals(b) || tablerillos[i][2].getEmpate())) {
					vencedor = b;
				// Comprueba si hay alguna combinación ganadora horizontal
				} else if (this.tablerillos[i][0].getEmpate() && this.tablerillos[i][1].getEmpate() && this.tablerillos[i][2].getEmpate()) {
					vencedor = this.getJugadorConElTurno().getEmail();
				} else if ((this.tablerillos[0][i].getVencedor().equals(a) || tablerillos[0][i].getEmpate()) && 
						(this.tablerillos[1][i].getVencedor().equals(a) || tablerillos[1][i].getEmpate()) &&
						(this.tablerillos[2][i].getVencedor().equals(a) || tablerillos[2][i].getEmpate())) {
					vencedor = a;
				} else if ((this.tablerillos[0][i].getVencedor().equals(b) || tablerillos[0][i].getEmpate()) && 
						(this.tablerillos[1][i].getVencedor().equals(b) || tablerillos[1][i].getEmpate()) &&
						(this.tablerillos[2][i].getVencedor().equals(b) || tablerillos[2][i].getEmpate())) {
					vencedor = b;
				} else if (this.tablerillos[0][i].getEmpate() && this.tablerillos[1][i].getEmpate() && this.tablerillos[2][i].getEmpate()) {
					vencedor = this.getJugadorConElTurno().getEmail();
				} 
				i = 4;
//				// Comprueba si hay alguna combinación ganadora vertical
//				if (this.tablerillos[i][0].getVencedor().equals(this.tablerillos[i][1].getVencedor())
//					&& this.tablerillos[i][1].getVencedor().equals(this.tablerillos[i][2].getVencedor())
//					&& !this.tablerillos[i][2].getVencedor().equals("")) {
//					if (this.tablerillos[i][0].getVencedor().equals(this.jugadorA.getEmail()))
//						vencedor = a;
//					else
//						vencedor = b;
//					i = 4;
//				// Comprueba si hay alguna combinación ganadora horizontal
//				} else if (this.tablerillos[0][i].getVencedor().equals(this.tablerillos[1][i].getVencedor())
//					&& this.tablerillos[1][i].getVencedor().equals(this.tablerillos[2][i].getVencedor())
//					&& !this.tablerillos[2][i].getVencedor().equals("")) {
//					if (this.tablerillos[0][i].getVencedor().equals(this.jugadorA.getEmail()))
//						vencedor = a;
//					else
//						vencedor = b;
//					i = 4;
				}
			}
			// Comprueba si hay alguna combinación ganadora diagonal
			if (vencedor.equals("")) {
				if ((this.tablerillos[0][0].getVencedor().equals(a) || tablerillos[0][0].getEmpate()) && 
						(this.tablerillos[1][1].getVencedor().equals(a) || tablerillos[1][1].getEmpate()) &&
						(this.tablerillos[2][2].getVencedor().equals(a) || tablerillos[2][2].getEmpate())) {
					vencedor = a;
				} else if ((this.tablerillos[0][0].getVencedor().equals(b) || tablerillos[0][0].getEmpate()) && 
						(this.tablerillos[1][1].getVencedor().equals(b) || tablerillos[1][1].getEmpate()) &&
						(this.tablerillos[2][2].getVencedor().equals(b) || tablerillos[2][2].getEmpate())) {
					vencedor = b;
				} else if (this.tablerillos[0][0].getEmpate() && this.tablerillos[1][1].getEmpate() && this.tablerillos[2][2].getEmpate()) {
					vencedor = this.getJugadorConElTurno().getEmail();
				} else if ((this.tablerillos[2][0].getVencedor().equals(a) || tablerillos[2][0].getEmpate()) && 
						(this.tablerillos[1][1].getVencedor().equals(a) || tablerillos[1][1].getEmpate()) &&
						(this.tablerillos[0][2].getVencedor().equals(a) || tablerillos[0][2].getEmpate())) {
					vencedor = a;
				} else if ((this.tablerillos[2][0].getVencedor().equals(b) || tablerillos[2][0].getEmpate()) && 
						(this.tablerillos[1][1].getVencedor().equals(b) || tablerillos[1][1].getEmpate()) &&
						(this.tablerillos[0][2].getVencedor().equals(b) || tablerillos[0][2].getEmpate())) {
					vencedor = b;
				} else if (this.tablerillos[2][0].getEmpate() && this.tablerillos[1][1].getEmpate() && this.tablerillos[0][2].getEmpate()) {
					vencedor = this.getJugadorConElTurno().getEmail();
//				if (this.tablerillos[0][0].getVencedor().equals(this.tablerillos[1][1].getVencedor())
//					&& this.tablerillos[1][1].getVencedor().equals(this.tablerillos[2][2].getVencedor())
//					&& !this.tablerillos[2][2].getVencedor().equals("")) {
//					if (this.tablerillos[2][2].getVencedor().equals(this.jugadorA.getEmail()))
//						vencedor = a;
//					else
//						vencedor = b;
//				} else if (this.tablerillos[2][0].getVencedor().equals(this.tablerillos[1][1].getVencedor())
//					&& this.tablerillos[1][1].getVencedor().equals(this.tablerillos[0][2].getVencedor())
//					&& !this.tablerillos[0][2].getVencedor().equals("")) {
//					if (this.tablerillos[0][2].getVencedor().equals(this.jugadorA.getEmail()))
//						vencedor = a;
//					else
//						vencedor = b;
//				}
			}
		}
		setVencedor(vencedor);
	}
	
	public String getVencedor() {
		return this.vencedor;
	}
	
	private void setVencedor(String vencedor) {
		this.vencedor = vencedor;
	}
	
	public void comprobarMovimiento(int cT, int fT, int cC, int fC) throws CoordenadasNoValidasException, MovimientoNoValidoException, CasillaOcupadaException {
		System.out.println("Tablero colocar");
		if (cT<0 || cT>2 || fT<0 || fT>2 || cC<0 || cC>2 || fC<0 || fC>2)
			throw new CoordenadasNoValidasException(cT, fT, cC, fC);
		if (this.tablerillos[cT][fT].getCasillas()[cC][fC].getValor() != 0)
			throw new CasillaOcupadaException(cT, fT, cC, fC);
		System.out.println("Columna tablero peq. anterior: " + last_cC + ". Fila tablero peq. anterior: " + last_fC + ". Columna tablero grande actual: " + cT + ". Fila tabler ogrande actual: " + fT);
		if (this.last_fC != -1)
			if (this.last_cC != cT || this.last_fC != fT)
				if (!this.tablerillos[this.last_cC][this.last_fC].isFull())
					throw new MovimientoNoValidoException(cT, fT, cC, fC);
	}
	
	public void colocar(int cT, int fT, int cC, int fC) throws PartidaFinalizadaException, TableroGanadoException, TableroEmpateException {
		Tablero3x3 tablerillo = this.tablerillos[cT][fT];
		tablerillo.colocar(cC, fC, this.ultimoValor);
		tablerillo.comprobarVencedor(this.jugadorA.getEmail(), this.jugadorB.getEmail());
		this.ultimoValor *= -1;
			
		this.last_cT = cT; this.last_fT = fT; this.last_cC = cC; this.last_fC = fC;
		this.cambiarTurno();
		
		if (!tablerillo.getVencedor().equals("")) {
			this.comprobarVencedor(this.jugadorA.getEmail(), this.jugadorB.getEmail());
			if (!this.vencedor.equals(""))
				throw new PartidaFinalizadaException(this.vencedor, cT, fT);
			throw new TableroGanadoException(tablerillo.getVencedor(), cT, fT);
		}
		if(tablerillo.getVencedor().equals("") && tablerillo.isFull()){
			tablerillo.setEmpate(true);
			this.comprobarVencedor(this.jugadorA.getEmail(), this.jugadorB.getEmail());
			if (!this.vencedor.equals(""))
				throw new PartidaFinalizadaException(this.vencedor, cT, fT, true);
			throw new TableroEmpateException(cT,fT);
		}
	}

	public void setJugadorConelTurno(Jugador jugador) {
		this.jugadorConElTurno = jugador;
	}
	
	public void cambiarTurno(){
		if (this.jugadorConElTurno.equals(this.jugadorA))
			this.jugadorConElTurno = this.jugadorB;
		else
			this.jugadorConElTurno = this.jugadorA;
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
		String r = "";
		for (int fila = 0; fila < 3; fila++){
			for(int col = 0; col < 3; col++){
				r += this.tablerillos[col][fila].toString();
			}
		}
		return r;
	}
}
