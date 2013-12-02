package PruebaComunicacion;


import esi.uclm.iso.ultimate_tttoe.comunicaciones.Cliente;


public class main {
	public static void main(String []args){
		System.out.println("Conectando");
		conectar();
	}
	
	protected static void conectar() {
		Cliente cliente;
		System.out.println("En Conectar");
		try {
			cliente=new Cliente("pepeprueba");
			cliente.setServer("rmi://172.19.212.87:3000/terd");
			cliente.conectar();
			System.out.println("Conectado");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
