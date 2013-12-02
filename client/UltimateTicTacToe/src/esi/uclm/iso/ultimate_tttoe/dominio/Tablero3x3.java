package esi.uclm.iso.ultimate_tttoe.dominio;

public class Tablero3x3 {
	
	private Casilla[][] casillas;
	
	
	public Tablero3x3(){
		this.casillas = new Casilla[3][3];
		for(int fila=0; fila<3; fila++){
			for(int col=0; col<3; col++){
				this.casillas[col][fila] = new Casilla();
			}
		}
	}

	public void colocar(int cC, int fC, int valor){
		this.casillas[cC][fC].setValor(valor);
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
