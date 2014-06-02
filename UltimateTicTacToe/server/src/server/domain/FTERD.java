package server.domain;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import server.persistence.DAOAutenticar;
import server.persistence.DAOTablero;
import client.exceptions.JugadorNoExisteException;

public class FTERD {
	private static FTERD yo;
	private Hashtable<String, Jugador> jugadores;
	private Hashtable<Integer, Tablero9x9> tableros;
	private Hashtable<String, String> retosEnEspera;
	private Hashtable <String, Integer> retosContestados;
	
	private FTERD() {
		this.jugadores =     new Hashtable<String, Jugador>();
		this.tableros  =     new Hashtable<Integer, Tablero9x9>();
		this.retosEnEspera = new Hashtable<String, String>();
		this.retosContestados = new Hashtable<String, Integer>();
	}
	
	public static FTERD get() {
		if (yo==null)
			yo=new FTERD();
		return yo;
	}
	
	public void add(String email, String passwd) throws JugadorNoExisteException{
		if (jugadores.containsKey(email)) throw new JugadorNoExisteException(email);
		// Habria que crear una nueva excepcion y volverla a importar
		
		try {
			if (DAOAutenticar.autenticar(email, passwd)) {
				if (jugadores==null) {
					this.jugadores=new Hashtable<String, Jugador>();
				}
				Jugador jugador = new Jugador(email, passwd);
				this.jugadores.put(jugador.getEmail(), jugador);
			}
			else throw new JugadorNoExisteException(email);
		} catch (ClassNotFoundException e) {
			throw new JugadorNoExisteException(email);
		} catch (SQLException e) {
			throw new JugadorNoExisteException(email);
		}
	}
		
	public void delete(String email) {
		if (jugadores != null) {
			this.jugadores.remove(email);
		}
	}
	
	public void register(Jugador j) throws ClassNotFoundException, SQLException {
		j.insert();
	}

	public Jugador poner(int idPartida, String email, int cT, int fT, int cC, int fC) {
		Jugador oponente = null;
		if(this.tableros.containsKey(idPartida)){
			
		
			Tablero9x9 partida = this.tableros.get(idPartida);
			
			if (partida!=null){
				try {
					partida.colocar(email, cT, fT, cC, fC);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}			
	
				oponente = partida.getOpenenteDE(email);	

			}
		}
		return oponente;
	}
	
	public void retar(String retador, String retado){
		retosEnEspera.put(retador, retado);		
	}
	
	public int respuestaAPeticionDeReto(String retador, String retado, boolean respuesta) {
		
		int idPartida = -1;
		if (retosEnEspera.containsKey(retador) &&
				(retosEnEspera.get(retador)).equals(retado) ){
			
			retosEnEspera.remove(retador);

			if (respuesta){
				Jugador jugadorA = this.getJugador(retador);
				Jugador jugadorB = this.getJugador(retado);
				
				Tablero9x9 tableroNuevo = new Tablero9x9();
				tableroNuevo.setJugadorA(jugadorA);
				tableroNuevo.setJugadorB(jugadorB);
				this.tableros.put(tableroNuevo.getId(), tableroNuevo);
				idPartida = tableroNuevo.getId();
				try {
					DAOTablero.nuevaPartida(tableroNuevo);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}				
			}
		
		}
		
		return idPartida;
	}

	public Hashtable<String, Jugador> getJugadores() {
		return jugadores;
	}
	
	public Vector<String> getEmailsJugadores(){
		Vector<String> emailJugadores = new Vector<String>();
		
		for (String email: this.jugadores.keySet()){
			emailJugadores.add(email);
			
		}
		return emailJugadores;
	}

	public Hashtable<Integer, Tablero9x9> getTableros() {
		return tableros;
	}
	
	public Hashtable<String, String> getRetosEnEspera(){
		return retosEnEspera;
	}

	public Hashtable<String, Integer> getRetosContestados() {
		return retosContestados;
	}

	public Jugador getJugador(String email) {
		return this.jugadores.get(email);
	}

	public Hashtable<String, Integer> eliminarPartidaDelJugador(String email) {
		Hashtable<String, Integer> avisarA = new Hashtable<String, Integer>();
		
		Enumeration<Tablero9x9> tableros = this.tableros.elements();
		while(tableros.hasMoreElements()){
			Tablero9x9 tablero = tableros.nextElement();
			if (tablero.getJugadorA().getEmail().equals(email)){
				avisarA.put(tablero.getJugadorB().getEmail(), tablero.getId());
				this.tableros.remove(tablero.getId());
			}
			if (tablero.getJugadorB().getEmail().equals(email)){
				avisarA.put(tablero.getJugadorA().getEmail(), tablero.getId());
				this.tableros.remove(tablero.getId());
			}
		}	
		
		return avisarA;
	}

	public void eliminarPartidaFinalizada(int idPartida) {
		this.tableros.remove(idPartida);
	}
}
