package client.controller;

import client.presentation.*;

public class Controller implements IController {
	IJuego juego;
	IListaJugadores lista;
	ILogin login;
	IRegistro registro;
	FTERD fachadaModelo;
	
	public boolean comprobarMovimientoValido(int jugador, int X, int Y, int x, int y) {
		boolean valido = false;
		
		return valido;
	}
}
