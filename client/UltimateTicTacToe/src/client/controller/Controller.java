package client.controller;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Hashtable;

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
		
	
	public void comprobarMovimientoValido(String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, JugadorNoExisteException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException, CoordenadasNoValidasException {
		modelo.poner(email, cT, fT, cC, fC);
		juego.ponerFicha(email, cT, fT, cC, fC);
	}
	
	public void enviarDatosLogin(String email, String passwd) {
		modelo.autenticar(email, passwd);
	}
	
	public void enviarDatosRegistro(String email, String passwd) {
		modelo.registrarJugador(email, passwd);
	}
	
	public void retarJugador() {
		
	}
	
}
