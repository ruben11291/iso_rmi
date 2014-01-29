package server.domain;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import client.exceptions.JugadorNoExisteException;
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
			// TODO Auto-generated catch block
			throw new JugadorNoExisteException(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	
	public Jugador autenticar(String email, String passwd) throws ClassNotFoundException, SQLException, JugadorNoExisteException{
		Jugador j = null;
		
		if (DAOAutenticar.autenticar(email, passwd)){
			j = new Jugador(email);
				this.add(email, passwd);
			
		}
		
		return j;
				
	}
	
	/*AÑADIR A LA INTERFAZ*/
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			
//			mandar movimiento a oponente
			
//			Jugador oponente = partida.getOpenenteDE(email);
//			oponente.poner(idPartida,..,..,);
			
		}
	}
	
	
	
	/*AÑADIR A LA INTERFAZ*/
	public void retar(String retador, String retado){
		retosEnEspera.put(retador, retado);
		//Enviar peticion de reto a jugador 'retado'
		
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	
				
				
			}
			if (!respuesta){

			}
		
		}
		
		return idPartida;
	}

	public Hashtable<String, Jugador> getJugadores() {
		return jugadores;
	}

	public Hashtable<Integer, Tablero9x9> getTableros() {
		return tableros;
	}
	
	public Hashtable<String, String> getRetosEnEspera(){
		return retosEnEspera;
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

	public Hashtable<String, Integer> eliminarPartidaDelJugador(String email) {
		Hashtable<String, Integer> avisarA = new Hashtable<String, Integer>();
		
		Enumeration<Tablero9x9> tableros = this.tableros.elements();
		while(tableros.hasMoreElements()){
			Tablero9x9 tablero = tableros.nextElement();
			if (tablero.getJugadorA().getEmail().equals(email)){
				avisarA.put(tablero.getJugadorB().getEmail(), tablero.getId());			
			}
			if (tablero.getJugadorB().getEmail().equals(email)){
				avisarA.put(tablero.getJugadorA().getEmail(), tablero.getId());
			}
				
		}
		
		return avisarA;
	}
}
