package PruebaComunicacion;


import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.communications.*;
import client.domain.Tablero9x9;

public class main {
	public static void main(String []args) throws Exception{
		System.out.println("Conectando");
		conectar2();
		Tablero9x9 prueba = new Tablero9x9();
	}
	
	protected static void conectar() {
		Cliente cliente=null;
		System.out.println("En Conectar");
			try {
				cliente=new Cliente("pepe6prueba@pepe.com");
			} catch (RemoteException | UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				cliente.setServer("rmi://192.168.0.100:3001/servidor");
				;
			} catch (MalformedURLException | RemoteException
					| NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				cliente.conectar();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			@SuppressWarnings("unused")
//			Proxy p=Proxy.get();
//			System.out.println("Conectado");

	}
	
	protected static void conectar2() throws Exception {
		Cliente c = new Cliente("pepe");
		Proxy proxy = Proxy.get();
		System.out.println("Conectar2 (con proxy)");
		proxy.register(c.getEmail(), "pepe");
//		proxy.add(c.getEmail(), c);
	}
}
