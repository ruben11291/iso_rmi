package server.domain;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import client.exceptions.*;
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
	public void poner(int idPartida, String email, int cT, int fT, int cC, int fC) throws NoEstaJugandoException, NoTienesElTurnoException, CoordenadasNoValidasException, ClassNotFoundException, SQLException  {
		Tablero9x9 partida = this.tableros.get(idPartida);
		
		if (partida!=null){
			partida.colocar(email, cT, fT, cC, fC);
			
//			mandar movimiento a oponente
//			Jugador oponente = partida.getOpenenteDE(email);
			
		}
	}
	
	
	
	/*AÑADIR A LA INTERFAZ*/
	public void retar(String retador, String retado){
		retosEnEspera.put(retador, retado);
		//Enviar peticion de reto a jugador 'retado'
	}
	
	/*AÑADIR A LA INTERFAZ*/
	public void respuestaAPeticionDeReto(String retador, String retado, boolean respuesta) throws ClassNotFoundException, SQLException{
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
				DAOTablero.nuevaPartida(tableroNuevo);
				
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

	/********************************************************************************/
	/* ESTE SOBRE. MODIFICAR LA INTERFAZ*/
	public void poner(String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, JugadorNoExisteException, NoEstaJugandoException, CoordenadasNoValidasException {
		Jugador j=this.jugadores.get(email);
		if (j==null)
			throw new JugadorNoExisteException(email);
		j.poner(cT, fT, cC, fC);
	}
	
	
	/* ESTE SOBRA. MODIFICAR INTERFAZ*/
	public void solicitudDeJuego(Jugador jugador) throws RemoteException {
		Tablero9x9 tablero=new Tablero9x9();
		tablero.setJugadorA(jugador);
//		tablero.setJugadorConElTurno(jugador);
//		this.tableros.put(jugador.getEmail(), tablero);
		jugador.setMensaje("Has creado el tablero con id: " + tablero.getId());
	}
	/* ESTE SOBRA. MODIFICAR INTERFAZ*/
	public void unirAPartida(Jugador oponente, Jugador creador) throws RemoteException {
		Tablero9x9 tablero=this.tableros.get(creador.getEmail());
		tablero.setJugadorA(creador);
		tablero.setJugadorB(oponente);
		creador.setTablero(tablero);
		oponente.setTablero(tablero);
		creador.setMensaje("Se te ha unido " + oponente.getEmail());
		oponente.setMensaje("Estás en la partida de " + creador.getEmail());
	}
	/***********************************************************************************/
	
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
