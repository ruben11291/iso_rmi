package client.exceptions;

public class TableroGanadoException extends Exception {
	private int cT;
	public int getcT() {
		return cT;
	}
	public int getfT() {
		return fT;
	}
	private int fT;
	private String email;
	public String getEmail() {
		return email;
	}
	public TableroGanadoException(String email, int cT, int fT){
		 super("El jugador " + email + " ha ganado el tablero " + cT + ", " + fT);
		 this.email = email;
		 this.cT = cT;
		 this.fT = fT;
	}
	
}
