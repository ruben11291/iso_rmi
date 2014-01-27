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
}
