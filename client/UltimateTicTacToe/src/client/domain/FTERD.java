package client.domain;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import client.communications.Cliente;
import client.communications.Proxy;
import client.controller.Controller;
import client.exceptions.*;
import client.persistence.DAOAutenticar;
import client.exceptions.*;

public class FTERD {
	
	private Hashtable<String, Jugador> jugadores;
	private Jugador jugador;
	private Hashtable<Integer, Tablero9x9> tableros;
	private Vector<String> retosSolicitados;
	private Proxy proxy;
	private Cliente cliente;
	
	public FTERD() throws Exception{
		this.jugadores = new Hashtable<String, Jugador>();
		this.tableros  = new Hashtable<Integer, Tablero9x9>();
		this.proxy = Proxy.get();
		this.cliente = new Cliente(this);
		this.retosSolicitados = new Vector<String>();
	}

	public String getEmailJugador(){
		if(jugador != null) 
			return this.jugador.getEmail();
		else return "";
	}
	
	public void autenticarTest(String email, String passwd){
		this.jugador = new Jugador(email,passwd);
	}
	
	public void registrarJugador(String email, String passwd) throws RemoteException {
		proxy.register(email, passwd);
	}
	
	public void autenticar(String email, String passwd) throws JugadorNoExisteException {
		try {
			proxy.add(email, passwd, cliente);
			this.jugador = new Jugador(email,passwd);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void poner(int id_partida, String email, int cT, int fT, int cC, int fC) throws RemoteException {
		proxy.poner(id_partida, email, cT, fT, cC, fC);
	}

	public void retar(String oponente) throws RemoteException {
		proxy.retar(this.jugador.getEmail(), oponente);
		this.retosSolicitados.add(oponente);
	}
	
	
	
	public void cerrarSesion() {
		
		try {
			proxy.delete(this.jugador.getEmail());
			this.jugador = null;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateJugadoresConectados(Vector <String> jugadores){
		//pasarselo a ventana
		System.out.println(jugadores);
		try {
			Controller c = Controller.get();
			c.enviarListaJugadores(jugadores);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Metodo que se llama cuando un cliente recibe la respuesta a su solicitud de reto
	public void respuestaAPeticionDeReto(String retador, String retado,
			boolean respuesta, int idPartida) {
		
		if(this.retosSolicitados.contains(retador)){
			if(respuesta){
				this.retosSolicitados.remove(retador);
				creaPartida(retador,retado, idPartida);
				try {
					Controller cntrl = Controller.get();
					cntrl.respuestaReto(retado, respuesta, idPartida);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//Metodo en el que se crea la partida y se inicializan los jugadores
	private void creaPartida(String retador, String retado, int idPartida){
		Tablero9x9 tablero = new Tablero9x9(idPartida);
		Jugador j1 = new Jugador(retador,"");
		Jugador j2 = new Jugador(retado,"");
		tablero.setJugadorA(j1);
		tablero.setJugadorB(j2);
		tablero.setJugadorConelTurno(j1);
		this.tableros.put(idPartida, tablero);
	}
	
	//Metodo que se llama cuando se recibe por parte del servidor un inicio de partida.
	//Este cliente es el que recibe la petición de reto
	public void iniciarPartida(String retador, String retado, int idPartida){
		creaPartida(retador, retado, idPartida);
	}

	//Esta operacion hace que el cliente limpie la partida que mantenia con el otro jugador
	//Es recibida por el openente al jugador que abandona
	public void oponenteHaAbandonado(int idPartida) {
		Controller c;
		try {
			c = Controller.get();
			c.cerrarPartida(idPartida);
			this.tableros.remove(idPartida);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void hasSidoRetado(String retador) {
		// TODO Auto-generated method stub
		
	}
	
}
