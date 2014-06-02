package client.exceptions;

public class CoordenadasNoValidasException extends Exception {

	public CoordenadasNoValidasException(int cT, int fT, int cC, int fC) {
		super("Las coordenadas (" + cT + ", " + fT + ", " + cC + ", " + fC + ") no son " +
				"v??lidas. Deben estar entre 0 y 2");
	}
	
}
