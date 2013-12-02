package server.domain;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import server.exportable.exceptions.CoordenadasNoValidasException;
import server.exportable.exceptions.JugadorNoExisteException;
import server.exportable.exceptions.NoEstaJugandoException;
import server.exportable.exceptions.NoTienesElTurnoException;

public class FTERD {
	private static FTERD yo;
	private Hashtable<String, Jugador> jugadores;
	private Hashtable<String, Tablero9x9> tableros;
	
	private FTERD() {
		this.jugadores=new Hashtable<String, Jugador>();
		this.tableros=new Hashtable<String, Tablero9x9>();
	}
	
	public static FTERD get() {
		if (yo==null)
			yo=new FTERD();
		return yo;
	}
	
	public void add(Jugador jugador) {
		if (jugadores==null) {
			this.jugadores=new Hashtable<String, Jugador>();
		}
		this.jugadores.put(jugador.getEmail(), jugador);
	}
	
	public void delete(Jugador jugador) {
		if (jugadores != null) {
			this.jugadores.remove(jugador.getEmail());
		}
	}
	
	public void register(Jugador j) throws ClassNotFoundException, SQLException {
		j.insert();
	}
	
	public void poner(String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, JugadorNoExisteException, NoEstaJugandoException, CoordenadasNoValidasException {
		Jugador j=this.jugadores.get(email);
		if (j==null)
			throw new JugadorNoExisteException(email);
		j.poner(cT, fT, cC, fC);
	}

	public void solicitudDeJuego(Jugador jugador) throws RemoteException {
		Tablero9x9 tablero=new Tablero9x9();
		tablero.setJugadorA(jugador);
		tablero.setJugadorConElTurno(jugador);
		this.tableros.put(jugador.getEmail(), tablero);
		jugador.setMensaje("Has creado el tablero con id: " + tablero.getId());
	}

	public void unirAPartida(Jugador oponente, Jugador creador) throws RemoteException {
		Tablero9x9 tablero=this.tableros.get(creador.getEmail());
		tablero.setJugadorA(creador);
		tablero.setJugadorB(oponente);
		creador.setTablero(tablero);
		oponente.setTablero(tablero);
		creador.setMensaje("Se te ha unido " + oponente.getEmail());
		oponente.setMensaje("Estás en la partida de " + creador.getEmail());
	}

	public Hashtable<String, Jugador> getJugadores() {
		return jugadores;
	}

	public Hashtable<String, Tablero9x9> getTableros() {
		return tableros;
	}
	
	public Vector<Tablero9x9> getTablerosLibres() {
		Vector<Tablero9x9> result=new Vector<Tablero9x9>();
		Enumeration<Tablero9x9> lista=this.tableros.elements();
		while (lista.hasMoreElements()) {
			Tablero9x9 t=lista.nextElement();
			if (t.getJugadorB()==null)
				result.add(t);
		}
		return result;
		
	}

	public Jugador getJugador(String email) {
		return this.jugadores.get(email);
	}
}
