package PruebaComunicacion;


import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.communications.*;
import client.domain.Tablero9x9;

public class main {
	public static void main(String []args){
		System.out.println("Conectando");
		conectar();
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
				cliente.setServer("rmi://localhost:3001/servidor");
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
}
