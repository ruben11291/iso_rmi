package server.exportable.exceptions;

public class NoEstaJugandoException extends Exception {
	public NoEstaJugandoException(String email) {
		super(email + " no est?? jugando");
	}
}