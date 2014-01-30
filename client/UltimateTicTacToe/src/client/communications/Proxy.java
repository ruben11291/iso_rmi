package client.communications;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

import client.exceptions.JugadorNoExisteException;
import client.exceptions.JugadorYaExisteException;
import client.exportable.communications.ICliente;
import server.exportable.communications.IServer;

public class Proxy {
	private static Proxy yo;
	private IServer server;
	  
	private Proxy() throws MalformedURLException, RemoteException, NotBoundException {
		this.server=(IServer) Naming.lookup("rmi://172.19.243.93:3001/servidor");//172.19.177.184

	}
	
	public static Proxy get() throws Exception {
		if (yo==null)
			yo=new Proxy();
		return yo;
	}
	
	//////////// COMUNICACION CON EL SERVIDOR//////////////////
	//registrar en el sistema
	public void register(String email, String passwd) throws RemoteException {
		// TODO Auto-generated method stub
		server.register(email, passwd);
	}
	
	//darse de alta en el sistema
	public void add(String email, String passwd, ICliente cliente) throws RemoteException, JugadorNoExisteException, JugadorYaExisteException{
		server.add(email, passwd, cliente);
	}
	
	//darse de baja del sistema
	public void delete(String email) throws RemoteException {
		server.delete(email);
	}
	
	//retar a un jugador
	public void retar(String emailRetador, String emailRetado) throws RemoteException {
		server.retar(emailRetador, emailRetado);
	}
	
	//realizar movimiento
	public void poner(String email, int cT, int fT, int cC, int fC) throws RemoteException {
		server.poner(email, cT, fT, cC, fC);
	}	
	
	public void envioRespuestaPeticionDeReto(String retador, String retado, boolean respuesta) throws RemoteException{
		System.out.println("Respuesta a server");
		server.respuestaAPeticionDeReto(retador, retado, respuesta);
	}

	public void abandonoPartida(String email) throws RemoteException {
		server.abandonoPartida(email);
	}
}
