package client.exceptions;

public class JugadorNoExisteException extends Exception {
	public JugadorNoExisteException(String email) {
		super("El jugador " + email + " no existe");
	}
}
