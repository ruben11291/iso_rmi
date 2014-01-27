package client.controller;

import client.exceptions.CoordenadasNoValidasException;
import client.exceptions.JugadorNoExisteException;
import client.exceptions.MovimientoNoValidoException;
import client.exceptions.NoEstaJugandoException;
import client.exceptions.NoTienesElTurnoException;
import client.exceptions.PartidaFinalizadaException;
import client.exceptions.TableroLlenoException;

public interface IController {
	public void comprobarMovimientoValido(String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, JugadorNoExisteException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException, CoordenadasNoValidasException;
}
