package client.controller;

import client.exceptions.*;
import client.presentation.*;

public interface IController {
	public void comprobarMovimientoValido(int id_partida, String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, JugadorNoExisteException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException, CoordenadasNoValidasException;
	public void setRegistro(IRegistro registro);
	public void setLista(IListaJugadores lista);
	public void setJuego(IJuego juego);
	public void setLogin(ILogin login);
}
