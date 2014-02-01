package client.exceptions;

public class TableroEmpateException extends Exception {
	private int cT;
	public int getcT() {
		return cT;
	}
	public int getfT() {
		return fT;
	}
	private int fT;
	public TableroEmpateException(int cT, int fT){
		 super("Empate en tablero " + cT + ", " + fT);
		 this.cT = cT;
		 this.fT = fT;
	}
}
