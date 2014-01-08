package server.exportable.exceptions;

public class JugadorYaExisteException extends Exception {
	public JugadorYaExisteException (String email) {
		super(email + " ya esta anyadido");
	}
}
