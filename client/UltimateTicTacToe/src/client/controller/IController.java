package client.controller;

import java.rmi.RemoteException;
import java.util.Vector;

import client.exceptions.*;
import client.presentation.*;

public interface IController {
	public void comprobarMovimientoValido(int id_partida, String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, JugadorNoExisteException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException, CoordenadasNoValidasException;
	public void enviarDatosLogin(String email, String passwd);
	public void enviarDatosRegistro(String email, String passwd);
	public void enviarListaJugadores(Vector<String> jugadores);
	public void retarJugador(String creador, String oponente);
	public void respuestaReto(String retado, boolean respuesta, int id_partida);
	public void setRegistro(IRegistro registro);
	public void setLista(IListaJugadores lista);
	public void setJuego(IJuego juego);
	public void setLogin(ILogin login);
}
