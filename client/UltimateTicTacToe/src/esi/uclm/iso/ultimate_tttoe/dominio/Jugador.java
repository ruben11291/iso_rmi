package esi.uclm.iso.ultimate_tttoe.dominio;

import java.sql.SQLException;

import esi.uclm.iso.ultimate_tttoe.excepciones.NoTienesElTurnoException;
import esi.uclm.iso.ultimate_tttoe.excepciones.NoEstaJugandoException;
import esi.uclm.iso.ultimate_tttoe.persistencia.DAOJugador;

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

	public void poner(int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException {
		
		if(this.tablero == null)
			throw new NoEstaJugandoException(email);
		
		if(this.tablero.getJugadorConElTurno() != this)
			throw new NoTienesElTurnoException();
		
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
