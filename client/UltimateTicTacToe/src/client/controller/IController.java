package client.controller;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.rmi.RemoteException;
import java.util.Vector;

import client.exceptions.*;
import client.presentation.*;

public interface IController {
	public void comprobarMovimientoValido(String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, JugadorNoExisteException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException, CoordenadasNoValidasException;
	public void enviarDatosLogin(String email, String passwd);
	public void enviarDatosRegistro(String email, String passwd);
	public void enviarListaJugadores(Vector<String> jugadores);
	public void enviarRespuestaReto(boolean respuesta, String retador);
	public void retarJugador(String oponente);
	public void respuestaReto(String retado, boolean respuesta);
	public void setRegistro(IRegistro registro);
	public void setLista(IListaJugadores lista);
	public void setLogin(ILogin login);
	public void setJuego(IJuego juego);
	public void hasSidoRetado(String retador);
	public void cerrarPartida();
	public void cerrarPartida(Window window);
	public void oponenteHaAbandonado();
	public void avisoCerrarSesion();
}
