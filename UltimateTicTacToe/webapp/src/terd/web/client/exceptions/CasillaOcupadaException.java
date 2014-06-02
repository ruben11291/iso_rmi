package terd.web.client.exceptions;

public class CasillaOcupadaException extends Exception {
	public CasillaOcupadaException(int cT, int fT, int cC, int fC) {
		super("La casilla está ocupada");
	}
}
