package server.domain;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import client.exceptions.JugadorNoExisteException;
import server.communications.Server;
import server.persistence.DAOAutenticar;
import server.persistence.DAOTablero;

public class FTERD {
	private static FTERD yo;
	private Hashtable<String, Jugador> jugadores;
	private Hashtable<Integer, Tablero9x9> tableros;
	private Hashtable<String, String> retosEnEspera;
	
	
	private FTERD() {
		this.jugadores =     new Hashtable<String, Jugador>();
		this.tableros  =     new Hashtable<Integer, Tablero9x9>();
		this.retosEnEspera = new Hashtable<String, String>(); 
	}
	
	public static FTERD get() {
		if (yo==null)
			yo=new FTERD();
		return yo;
	}
	
	public void add(String email, String passwd) throws JugadorNoExisteException{
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
	
	public void add(Jugador j) {
		
	}
	
	public void delete(String email) {
		if (jugadores != null) {
			this.jugadores.remove(email);
		}
	}
	
	public void register(Jugador j) throws ClassNotFoundException, SQLException {
		j.insert();
	}

	public void poner(int idPartida, String email, int cT, int fT, int cC, int fC) {
		if(!this.tableros.containsKey(idPartida)){
			System.out.println("NO CONTIENE PARTIDA");
			return;
		}
		Tablero9x9 partida = this.tableros.get(idPartida);
		
		if (partida!=null){
			try {
				partida.colocar(email, cT, fT, cC, fC);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}			

			Jugador oponente = partida.getOpenenteDE(email);
			Server s;
			System.out.println(email);
			try {
				s = Server.get();
				s.enviarMovimientoAOponente(idPartida, oponente.getEmail(), cT, fT, cC, fC);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		

		}
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
	
//	public Vector<Tablero9x9> getTablerosLibres() {
//		Vector<Tablero9x9> result=new Vector<Tablero9x9>();
//		Enumeration<Tablero9x9> lista=this.tableros.elements();
//		while (lista.hasMoreElements()) {
//			Tablero9x9 t=lista.nextElement();
//			if (t.getJugadorB()==null)
//				result.add(t);
//		}
//		return result;
//		
//	}

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
