package server.domain;

import java.rmi.RemoteException;
import java.sql.SQLException;

import client.exportable.communications.ICliente;
import client.exceptions.*;
import server.persistence.DAOJugador;

public class Jugador {
	private String email;
	private String passwd;
	private Tablero9x9 tablero;
	private ICliente cliente;
	
	public Jugador(ICliente cliente, String email) {
		this.cliente=cliente;
		this.email=email;
	}
	
	public Jugador(String email, String passwd) {
		this.cliente=null;
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

	public void poner(int cT, int fT, int cC, int fC) throws NoEstaJugandoException, NoTienesElTurnoException, CoordenadasNoValidasException {
		if (this.tablero==null)
			throw new NoEstaJugandoException(email);
//		if (this.tablero.getJugadorConElTurno()!=this)
//			throw new NoTienesElTurnoException();
//		this.tablero.colocar(cT, fT, cC, fC);
	}

	public void setTablero(Tablero9x9 tablero) {
		this.tablero=tablero;
	}
	
	@Override
	public String toString() {
		return this.email;
	}

	public void setMensaje(String msg) throws RemoteException {
		if (cliente!=null)
			this.cliente.mostrarUnMensaje(msg);
	}

	public Tablero9x9 getTablero() {
		return tablero;
	}
}
