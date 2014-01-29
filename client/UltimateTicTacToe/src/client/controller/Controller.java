package client.controller;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import client.domain.FTERD;
import client.exceptions.*;
import client.presentation.*;

public class Controller implements IController {
	private static Controller self;
	private IJuego juego;
	IListaJugadores lista;
	ILogin login;
	IRegistro registro;
	FTERD modelo;
	
	private Controller() throws Exception {
		this.modelo = new FTERD();
	}
	
	public static Controller get() throws Exception {
		if (self == null)
			self = new Controller();
		return self;
	}
		
	
	public void comprobarMovimientoValido(int id_partida, String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, JugadorNoExisteException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException, CoordenadasNoValidasException {
		try {
			modelo.poner(id_partida, email, cT, fT, cC, fC);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		juego.ponerFicha(email, cT, fT, cC, fC);
	}
	
	public void enviarDatosLogin(String email, String passwd) {
		boolean error = true;
		try{
			modelo.autenticar(email, passwd);
			error = false;
			login.recibirRespuestaLogin(error);
		}
		catch(JugadorNoExisteException e){
			//crear ventana de error
			System.out.println("JUGADOR "+e.toString()+" NO EXISTE EXCEPTION");
		}
		catch (JugadorYaExisteException e2) {
			System.out.println("JUGADOR "+e2.toString()+" YA EXISTE EN EL SERVER");
		}
		
	}
	
	public void enviarDatosRegistro(String email, String passwd) {
		boolean error = true;
		try {
			modelo.registrarJugador(email, passwd);
			error = false;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		registro.respuestaRegistro(error);
	}

	@Override
	public void enviarListaJugadores(Vector<String> jugadores) {
		lista.recibirRespuestaLista(jugadores);
	}
	
	public void retarJugador(String oponente) {
		try {
			modelo.retar(oponente);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setRegistro(IRegistro registro) {
		this.registro = registro;
	}

	@Override
	public void setLista(IListaJugadores lista) {
		this.lista = lista;
		
	}

	@Override
	public void setJuego(int id_partida, IJuego juego) {
		if (this.juego == null) this.juego =juego;
//		this.juegos.put(id_partida, juego);
	}

	@Override
	public void setLogin(ILogin login) {
		this.login = login;
	}

	public void cerrarSesion() {
		if (this.juego !=null) {
			this.cerrarPartida();
		}
		this.modelo.cerrarSesion();
		
	}

	//Cierra ventana de juego
	@Override
	public void cerrarPartida() {
		if(juego != null){
		this.juego.cerrar();
		}
		this.juego = null;
		
	}
	
	@Override 
	public void oponenteHaAbandonado(){
		this.juego.cerrarPorAbandonoOponente();
		this.juego = null;
	}
	
	@Override
	public void cerrarPartida(Window window) {
		// TODO Auto-generated method stub
		System.out.println("Cerrar Partida window");
//		boolean encontrado = false;
//		while(it.hasNext()&& !encontrado){
//			IJuego juego = it.next();
//			if(juego == window){
//				encontrado = true;
		this.modelo.cerrarPartida();
		System.out.println("CERRADA");
			
//		}

	}

	@Override
	public void respuestaReto(String retado, boolean respuesta, int id_partida) {
		// TODO Auto-generated method stub
		this.lista.recibirRespuestaReto(retado, respuesta, id_partida);		
	}

	@Override
	public void enviarRespuestaReto(boolean respuesta, String retador) {
		try {
			System.out.println("Respuesta enviada a la fachada: " + respuesta + ", " + retador);
			this.modelo.enviarRespuestaReto(respuesta, retador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void hasSidoRetado(String retador) {
		this.lista.recibirReto(retador);
	}
	
	public void iniciarPartida(int id_partida) {
		this.lista.iniciarPartida(id_partida);
	}

	@Override
	public void avisoCerrarSesion() {
		// TODO Auto-generated method stub
		this.login.avisoCerrarSesion();
	}



	
	
}
