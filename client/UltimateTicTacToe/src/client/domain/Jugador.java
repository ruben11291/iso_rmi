package client.domain;

import java.sql.SQLException;

import client.persistence.DAOJugador;
import client.exceptions.*;

public class Jugador {

	String email;
	private Tablero9x9 tablero;
	
	public Jugador(String a){
		this.email = a;
	}
	
	public void insert() throws ClassNotFoundException, SQLException{
		DAOJugador.insert(this);
	}
	
	public void delete() throws ClassNotFoundException, SQLException{
		DAOJugador.delete(this);
	}
	public void empezarPartida(){
		//comunicacion con ventana ifaz. y con rmi.
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return this.email;
	}

	public void poner(int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, CoordenadasNovalidasException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException {
		
		if(this.tablero == null)
			throw new NoEstaJugandoException(email);
		
		if(this.tablero.getJugadorConElTurno() != this)
			throw new NoTienesElTurnoException();
		
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
