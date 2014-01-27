package server.domain;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

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
	
	public Jugador autenticar(String email) throws ClassNotFoundException, SQLException{
		Jugador j = null;
		
		if (DAOAutenticar.autenticar(email)){
			j = new Jugador(email);
			this.add(j);
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
	
	/*AÑADIR A LA INTERFAZ*/
	public void respuestaAPeticionDeReto(String retador, String retado, boolean respuesta) {
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
				try {
					DAOTablero.nuevaPartida(tableroNuevo);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//IMPLEMENTAR:
				//mandar a retador: reto aceptado con tableroNuevo.getId()
				//mandar a retado:  tableroNuevo.getId()
				//...
				
			}
			if (!respuesta){
				//IMPLEMENTAR
				//mandar a retador: reto rechazado
				
			}
		
		}
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
}
