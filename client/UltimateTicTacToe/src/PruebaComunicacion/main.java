package PruebaComunicacion;


import client.communications.*;
import client.domain.Tablero9x9;
import client.exceptions.ClienteNoDesconectadoException;

public class main {
	public static void main(String []args){
		System.out.println("Conectando");
		conectar();
		Tablero9x9 prueba = new Tablero9x9();
	}
	
	protected static void conectar() {
		Cliente cliente,cliente2,cliente3;
		System.out.println("En Conectar");
		try {
			cliente=new Cliente("pepeprueba@pepe.com");
			cliente2 = new Cliente("ruben@g.cmo");
			cliente3 = new Cliente("pepeprueba@pepe.com");
			cliente.conectar();
			cliente2.conectar();
			cliente3.conectar();
			System.out.println("Conectado");
			try{
				cliente.desconectar();
				cliente2.desconectar();
				cliente3.desconectar();System.out.println("DESCONECADOS");
			}catch(ClienteNoDesconectadoException e){
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.out.println("error");
		}
	}
}
