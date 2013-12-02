package PruebaComunicacion;


import esi.uclm.iso.ultimate_tttoe.comunicaciones.Cliente;
import esi.uclm.iso.ultimate_tttoe.comunicaciones.Proxy;


public class main {
	public static void main(String []args){
		System.out.println("Conectando");
		conectar();
	}
	
	protected static void conectar() {
		Cliente cliente;
		System.out.println("En Conectar");
		try {
			cliente=new Cliente("pepeprueba@pepe.com");
//			cliente.setServer("rmi://localhost:3001/servidor");
//			//cliente.conectar();
			@SuppressWarnings("unused")
			Proxy p=Proxy.get();
			System.out.println("Conectado");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.out.println("error");
		}
	}
}
