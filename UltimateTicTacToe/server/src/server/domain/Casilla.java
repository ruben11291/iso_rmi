package server.domain;

public class Casilla {
	private int valor;
	private int id;
	
	public Casilla() {
		this.id=Math.abs((new java.util.Random()).nextInt());
		this.valor=0;
	}

	public void setValor(int valor) {
		this.valor=valor;
		
	}
	
	public int getValor() {
		return this.valor;
	}

	public int getId() {
		return id;		
	}
}
