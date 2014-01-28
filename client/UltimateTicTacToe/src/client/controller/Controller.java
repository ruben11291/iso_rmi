package client.controller;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import client.domain.FTERD;
import client.exceptions.*;
import client.presentation.*;

public class Controller implements IController {
	private static Controller self;
	IJuego juego;
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
		}
		catch(JugadorNoExisteException e){
			//crear ventana de error
			System.out.println("JUGADOR "+e.toString()+" NO EXISTE EXCEPTION");
		}
		login.recibirRespuestaLogin(error);
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
	
	public void retarJugador(String creador, String oponente) {
		try {
			modelo.retar(creador, oponente);
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
	public void setJuego(IJuego juego) {
		this.juego = juego;
	}

	@Override
	public void setLogin(ILogin login) {
		this.login = login;
	}
	
}
