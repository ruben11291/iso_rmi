package client.exceptions;


public class PartidaFinalizadaException extends Exception {
	public String getEmail() {
		return email;
	}

	public int getCol() {
		return col;
	}

	public int getFila() {
		return fila;
	}

	private String email;
	private int col;
	private int fila;
	
	public PartidaFinalizadaException(String email, int cT, int fT){
		 super("La partida ya ha finalizado, el ganador es: " + email);
		 this.email = email;
		 this.col = cT;
		 this.fila = fT;
	}
}
