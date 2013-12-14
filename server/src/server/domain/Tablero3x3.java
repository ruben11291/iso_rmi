package server.domain;

public class Tablero3x3 {
	private Casilla[][] casillas;
	private int id;
	
	public Tablero3x3() {
		this.id=Math.abs((new java.util.Random()).nextInt());
		this.casillas=new Casilla[3][3];
		for (int fila=0; fila<3; fila++) {
			for (int col=0; col<3; col++) {
				this.casillas[col][fila]=new Casilla();
			}
		}
	}

	/**
	 * Coloca una ficha en una casilla del tablero.
	 * @param cC numero de columna de la casilla
	 * @param fC numero de fila de la casilla
	 * @param valor valor de la ficha que se coloca
	 * @return El identificador de la casilla en la que se ha colocado la ficha
	 */
	public int colocar(int cC, int fC, int valor) {
		this.casillas[cC][fC].setValor(valor);
		return casillas[cC][fC].getId();
		
	}

	@Override
	public String toString() {
		String r="";
		for (int fila=0; fila<3; fila++) {
			for (int col=0; col<3; col++) {
				r+=this.casillas[col][fila].getValor();
			}
			r+=" ";
		}
		return r;
	}

	public int getId() {
		return id;
	}

	public Casilla[][] getCasillas() {
		return casillas;
	}

	
}
