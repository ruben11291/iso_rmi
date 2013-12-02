package server.exportable.exceptions;

public class JugadorNoExisteException extends Exception {
	public JugadorNoExisteException(String email) {
		super("El jugador " + email + " no existe");
	}
}
