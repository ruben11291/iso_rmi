package server.tests.communications;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import server.exportable.communications.IServer;
import client.exportable.communications.ICliente;


/**
 * Esta clase no es un test ejecutable
 * Es una clase auxiliar que emula a un cliente*/
public class ClienteParaTest extends UnicastRemoteObject implements ICliente {

	private IServer server;
	private String ipDelServidor;
	private String ipDelCliente;
	private int puertoDelServidor=3001;
	private int puertoDelCliente = 400;;

	public ClienteParaTest() throws RemoteException{
		try {
			ipDelServidor = InetAddress.getLocalHost().getHostAddress();
			ipDelCliente = InetAddress.getLocalHost().getHostAddress();
			server=(IServer) Naming.lookup("rmi://"+ipDelServidor+":"+this.puertoDelServidor+"/servidor");
		} catch (MalformedURLException  | NotBoundException | UnknownHostException e) {
			e.printStackTrace();
		}
		
		boolean conectado= false;
		while (!conectado) {
			try {
				LocateRegistry.createRegistry(this.puertoDelCliente);
				Naming.bind("rmi://" + this.ipDelCliente + ":" + this.puertoDelCliente + "/cliente", this);
				conectado=true;
			}
			catch (AlreadyBoundException eABE) {
				this.puertoDelCliente+=1;
			}
			catch (MalformedURLException e) {}
			catch (ExportException e){ 
				this.puertoDelCliente+=1;}
			catch (RemoteException e) {
				System.err.println(e.toString());
				this.puertoDelCliente+=1;
			}
		}
		
		
	}

	public void desconectar(){
		try {
			Naming.unbind("rmi://" + this.ipDelCliente + ":" + this.puertoDelCliente + "/cliente");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void OponenteHaAbandonadoPartida() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void iniciarPartida(int arg0, String arg1, String arg2)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarSolicitudReto(String arg0) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void poner(int arg0, int arg1, int arg2, int arg3, int arg4)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recibirListaDeJugadores(Vector<String> arg0)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void respuestaAPeticionDeReto(String arg0, String arg1,
			boolean arg2, int arg3) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
