package client.communications;

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
	
	public Cliente(FTERD f) throws RemoteException, UnknownHostException{
		super();
		//ip = "localhost";
		this.ip = InetAddress.getLocalHost().getHostAddress();
		puerto = 4000;              // Puerto por defecto para rmi
		this.fachada = f;
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
			catch (ExportException e){ 
				this.puerto+=1;}
			catch (RemoteException e) {
				System.err.println(e.toString());
				this.puerto+=1;
			}
		}
		
	}
	
	
	@Override
	public void poner(int idPartida, int cT, int fT, int cC, int fC) throws RemoteException {
		
		
		
	}



	@Override
	public void OponenteHaAbandonadoPartida(int idPartida) throws RemoteException {
		this.fachada.oponenteHaAbandonado(idPartida);
		
	}

	@Override

	public void recibirListaDeJugadores(Vector<String> jugadores)
			throws RemoteException {
		try {
			System.out.println("asss");
			this.fachada.updateJugadoresConectados(jugadores);
		} catch(NullPointerException e) {
			System.out.println(e);
		}
	}

	public String getEmail() {
		return this.email;
	}


	@Override
	public void notificarSolicitudReto(String retador) throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void iniciarPartida(int idPartida, String retador, String retado)
			throws RemoteException {
		this.fachada.iniciarPartida(retador, retado,idPartida);
		
	}


	@Override
	public void respuestaAPeticionDeReto(String retador, String retado,
			boolean respuesta, int idPartida) throws RemoteException {
		
		this.fachada.respuestaAPeticionDeReto(retador, retado, respuesta, idPartida);
		
	}
	
}
