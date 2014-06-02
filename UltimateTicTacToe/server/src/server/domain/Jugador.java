package server.domain;

import java.sql.SQLException;
import server.persistence.DAOJugador;

public class Jugador {
	private String email;
	private String passwd;
	private Tablero9x9 tablero;
	
	
	public Jugador(String email, String passwd) {
		this.email=email;
		this.passwd=passwd;
	}
	
	public Jugador(String email){
		this.email = email;
	}

	public void insert() throws ClassNotFoundException, SQLException {
		DAOJugador.insert(this);
	}
	
	public void delete() throws ClassNotFoundException, SQLException{
		DAOJugador.delete(this);
	}
	
	public void empezarPartida() {
		
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTablero(Tablero9x9 tablero) {
		this.tablero=tablero;
	}
	
	@Override
	public String toString() {
		return this.email;
	}

	public Tablero9x9 getTablero() {
		return tablero;
	}
}
