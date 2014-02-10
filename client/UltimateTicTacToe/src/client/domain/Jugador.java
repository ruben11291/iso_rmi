package client.domain;

import client.exceptions.*;

public class Jugador {

	String email;
	String passwd;
	private Tablero9x9 tablero;
	
	public Jugador(String email, String passwd){
		this.email = email;
		this.passwd = passwd;
	}

	public void setTablero(Tablero9x9 tablero) {
		this.tablero = tablero;
	}

	public String getEmail() {
		return this.email;
	}

	public void poner(int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, CoordenadasNoValidasException, MovimientoNoValidoException, PartidaFinalizadaException, CasillaOcupadaException, TableroGanadoException, TableroEmpateException {
		if(this.tablero == null)
			throw new NoEstaJugandoException(email);
		
		if(this.tablero.getJugadorConElTurno() != this)
			throw new NoTienesElTurnoException();
		
		this.tablero.comprobarMovimiento(cT, fT, cC, fC);
		this.tablero.colocar(cT, fT, cC, fC);
	}
}
