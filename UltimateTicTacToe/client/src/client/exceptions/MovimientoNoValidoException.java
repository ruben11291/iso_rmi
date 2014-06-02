package client.exceptions;

public class MovimientoNoValidoException extends Exception {
	public MovimientoNoValidoException(int cT, int fT, int cC, int fC) {
		super("El movimiento " + cT + ", " + fT + ", " + cC + ", " + fC + " no es válido.");
	}
}
