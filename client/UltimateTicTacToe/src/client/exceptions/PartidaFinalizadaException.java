package client.exceptions;

import client.domain.Jugador;

public class PartidaFinalizadaException extends Exception {
	public PartidaFinalizadaException(Jugador j){
		 super("La partida ya ha finalizado, el ganador es: " + j.getEmail());
	}
}
