package client.exceptions;

public class CoordenadasNovalidasException extends Exception {
	public CoordenadasNovalidasException(int cT, int fT, int cC, int fC){
		 super("Las coordenadas no son validas. Deben estar entre 0 y 2");
	}

}
