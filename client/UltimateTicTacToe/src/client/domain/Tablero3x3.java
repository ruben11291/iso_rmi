package client.domain;

public class Tablero3x3 {
	
	private Casilla[][] casillas;
	private String vencedor;	
	private boolean empate;
	
	public Tablero3x3(){
		this.casillas = new Casilla[3][3];
		for(int fila=0; fila<3; fila++){
			for(int col=0; col<3; col++){
				this.casillas[col][fila] = new Casilla();
			}
		}
		this.vencedor = "";	
		this.empate = false;
	}
	
	public Casilla[][] getCasillas() {
		return casillas;
	}
	
	public String getVencedor() {
		return this.vencedor;
	}
	
	private void setVencedor(String vencedor) {
		this.vencedor = vencedor;
	}

	public void colocar(int cC, int fC, int valor){
		this.casillas[cC][fC].setValor(valor);
	}
	

	public void setEmpate(boolean empate) {
		this.empate = empate;
		
	}
	public boolean getEmpate() {
		return this.empate;
		
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
		if (!this.vencedor.equals(""))
			hay = true;
		return hay;
	}
	
	public void comprobarVencedor(String a, String b) {
		String vencedor = "";
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
			if (vencedor.equals("")) {
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
