package PruebaComunicacion;


import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.communications.*;
import client.domain.Tablero9x9;
import client.presentation.StartupWindow;

import java.net.InetAddress;

import javax.swing.JOptionPane;

public class main {
	private static StartupWindow startupWindow = null;
	
	public static void main(String []args) throws Exception{
		startupWindow = new StartupWindow();
		startupWindow.setLocationRelativeTo(null);
		startupWindow.setVisible(true);		
		System.out.println("Conectando");
		Tablero9x9 prueba = new Tablero9x9();
	}
	
	protected static void conectar() {
		Cliente cliente=null;
		System.out.println("En Conectar");
			try {
				cliente=new Cliente("pepeprueba@pepe.com");
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
}
