package client.domain;

public class Tablero3x3 {
	
	public Casilla[][] getCasillas() {
		return casillas;
	}

	private Casilla[][] casillas;
	private Jugador vencedor;	
	
	
	public Tablero3x3(){
		this.casillas = new Casilla[3][3];
		for(int fila=0; fila<3; fila++){
			for(int col=0; col<3; col++){
				this.casillas[col][fila] = new Casilla();
			}
		}
		this.vencedor = null;		
	}

	public void colocar(int cC, int fC, int valor){
		this.casillas[cC][fC].setValor(valor);
	}

	public boolean isFull() {
		boolean isFull = true;
		for(int fila=0; fila<3; fila++){
			for(int col=0; col<3; col++){
				if (this.casillas[col][fila].getValor() == 0) {
					isFull = false;
					col = fila = 3;
				}
			}
		}
		return isFull;
	}	

	public boolean hayVencedor() {
		boolean hay = false;
		if (this.vencedor != null)
			hay = true;
		return hay;
	}
	
	public void comprobarVencedor(Jugador a, Jugador b) {
		Jugador vencedor = null;
		if (!hayVencedor()) {
			for (int i = 0; i < 3; i++) {
				// Comprueba si hay alguna combinación ganadora vertical
				if (this.casillas[i][0].getValor() == this.casillas[i][1].getValor() 
					&& this.casillas[i][1].getValor() == this.casillas[i][2].getValor()
					&& this.casillas[i][2].getValor() != 0) {
					if (this.casillas[i][0].getValor() == 1)
						vencedor = a;
					else
						vencedor = b;
					i = 4;
				// Comprueba si hay alguna combinación ganadora horizontal
				} else if (this.casillas[0][i].getValor() == this.casillas[1][i].getValor() 
					&& this.casillas[1][i].getValor() == this.casillas[2][i].getValor()
					&& this.casillas[2][i].getValor() != 0) {
					if (this.casillas[0][i].getValor() == 1)
						vencedor = a;
					else
						vencedor = b;
					i = 4;
				}
			}
			// Comprueba si hay alguna combinación ganadora diagonal
			if (vencedor == null) {
				if (this.casillas[0][0].getValor() == this.casillas[1][1].getValor()
					&& this.casillas[1][1].getValor() == this.casillas[2][2].getValor()
					&& this.casillas[2][2].getValor() != 0) {
					if (this.casillas[2][2].getValor() == 1)
						vencedor = a;
					else
						vencedor = b;
				} else if (this.casillas[2][0].getValor() == this.casillas[1][1].getValor()
					&& this.casillas[1][1].getValor() == this.casillas[0][2].getValor()
					&& this.casillas[0][2].getValor() != 0) {
					if (this.casillas[0][2].getValor() == 1)
						vencedor = a;
					else
						vencedor = b;
				}
			}
		}
		setVencedor(vencedor);
	}
	
	public Jugador getVencedor() {
		return this.vencedor;
	}
	
	private void setVencedor(Jugador w) {
		this.vencedor = w;
	}
	
	@Override
	public String toString(){
		String r = "";
		for (int fila=0; fila<3; fila++){
			for(int col=0; col<3; col++){
				r+=this.casillas[col][fila].getValor();
			}
		}
		return r;
	}

}
