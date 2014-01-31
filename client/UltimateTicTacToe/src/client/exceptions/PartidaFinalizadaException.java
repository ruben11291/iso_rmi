package client.exceptions;

import client.domain.Jugador;

public class PartidaFinalizadaException extends Exception {
	public PartidaFinalizadaException(String email){
		 super("La partida ya ha finalizado, el ganador es: " + email);
	}
}
