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
	
	
	
	protected static void conectar2() throws Exception {
		Cliente c = new Cliente("pepe");
		Proxy proxy = Proxy.get();
		System.out.println("Conectar2 (con proxy)");
		proxy.register(c.getEmail(), "pepe");
//		proxy.add(c.getEmail(), c);
	}
}
