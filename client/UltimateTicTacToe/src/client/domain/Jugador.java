package client.domain;

import java.sql.SQLException;

import client.persistence.DAOJugador;
import client.exceptions.*;

public class Jugador {

	String email;
	String passwd;
	private Tablero9x9 tablero;
	
	public Jugador(String email, String passwd){
		this.email = email;
		this.passwd = passwd;
	}
	
	public void empezarPartida(){
		//comunicacion con ventana ifaz. y con rmi.
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return this.email;
	}

	public void poner(int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, CoordenadasNoValidasException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException {
		System.out.println("Jugador ha puesto "+this.email);
		if(this.tablero == null)
			throw new NoEstaJugandoException(email);
		
		if(this.tablero.getJugadorConElTurno() != this)
			throw new NoTienesElTurnoException();
		
		this.tablero.comprobarMovimiento(cT, fT, cC, fC);
		this.tablero.colocar(cT, fT, cC, fC);
		
	}

	public void setTablero(Tablero9x9 tablero) {
		this.tablero = tablero;
	}

	public boolean retar(Jugador creador) {
		// TODO Auto-generated method stub
		return false;
	}



	public void acabarPartida() {
		//comunicacion con ventana ifaz. y delete de rmi.
		
	}


	
}
