package client.communications;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import client.domain.FTERD;
import client.exceptions.CoordenadasNoValidasException;
import client.exceptions.JugadorNoExisteException;
import client.exceptions.NoEstaJugandoException;
import client.exceptions.NoTienesElTurnoException;
import client.exportable.communications.ICliente;
import server.exportable.communications.IServer;

public class Cliente extends UnicastRemoteObject implements ICliente{
	
	private String ip;
	private int puerto;
	private String email;
	private IServer servidor;
	private FTERD fachada;
	//private Tablero9x9 juego;
	
	public Cliente(String email) throws RemoteException, UnknownHostException{
		super();
		//ip = "localhost";
		this.ip = InetAddress.getLocalHost().getHostAddress();
		puerto = 4000;              // Puerto por defecto para rmi
		this.email = email;
		
		boolean conectado= false;
		while (!conectado) {
			try {
				LocateRegistry.createRegistry(this.puerto);
				//Naming.bind("rmi://" + this.ip + ":" + this.puerto + "/ServicioAjedrez", this);
				Naming.bind("rmi://" + this.ip + ":" + this.puerto + "/cliente", this);
				conectado=true;
			}
			catch (AlreadyBoundException eABE) {
				this.puerto+=1;
			}
			catch (MalformedURLException e) {}
			catch (RemoteException e) {
				System.err.println(e.toString());
				this.puerto+=1;
			}
		}
		
	}
	
	public void setServer(String url) throws MalformedURLException, RemoteException, NotBoundException {
		System.out.println("Recuperando servicio");
		this.servidor=(IServer) Naming.lookup(url);
		System.out.print("Servicio recuperado");
	}
		
	public void conectar() throws RemoteException {
		//this.servidor.add(this.email, (IServer)this);
		this.servidor.add(this.email, this);
		
	}

	@Override
	public void poner(int idPartida, int cT, int fT, int cC, int fC)
			throws RemoteException {
		
		
		
	}

	@Override
	public void respuestaAPeticionDeReto(String retador, String retado,
			boolean respuesta) throws RemoteException {
		
		;//this.fachada.
	}

	@Override
	public void OponenteHaAbandonadoPartida(int idPartida)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recibirListaDeJugadores(Vector<String> jugadores)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
