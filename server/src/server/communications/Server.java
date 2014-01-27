package server.communications;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import client.exportable.communications.ICliente;
import server.domain.FTERD;
import server.domain.Jugador;
import server.domain.Tablero9x9;
import server.exportable.communications.IServer;


public class Server extends UnicastRemoteObject implements IServer {
	private String ip;
	private int puerto;
	private FTERD fachada;
	private static Server servo;
	private Hashtable <String, ICliente>stubs;
	
	protected Server() throws RemoteException, UnknownHostException {
		super();
		//this.ip="localhost";
		this.ip = InetAddress.getLocalHost().getHostAddress();
		//System.out.println(ip);
		this.puerto=3001;
		this.fachada=FTERD.get();
		this.stubs = new Hashtable <String, ICliente>();
	}
	
	public static Server get() throws RemoteException, UnknownHostException {
		if (servo==null)
			servo=new Server();
		return servo;
	}

	public void conectar() throws RemoteException, MalformedURLException {
		LocateRegistry.createRegistry(this.puerto);
		try {
			Naming.bind("rmi://" + this.ip + ":" + this.puerto + "/servidor", this);
		}
		catch (AlreadyBoundException eABE) {
			Naming.rebind("rmi://" + this.ip + ":" + this.puerto + "/servidor", this);
		}
		System.out.println("Servidor escuchando" + this.puerto);
	}

	/**** Métodos remotos ****/
	
	/////ADD Player///
	public void add(String email, ICliente cliente) throws RemoteException {
		Jugador jugador=new Jugador((ICliente)cliente, email);
		this.fachada.add(jugador);
		this.stubs.put(email, cliente);
		System.out.println("Jugador añadido "+email+" en servidor ");
		
		Vector <String> emailJugadores = getEmailJugadores(stubs);
		
		Enumeration<ICliente> lista=this.stubs.elements();
		while (lista.hasMoreElements()) {
			ICliente t=lista.nextElement();
			t.recibirListaDeJugadores(emailJugadores);
		}
	}

	private Vector<String> getEmailJugadores(Hashtable<String, ICliente> stubs2) {
		Vector <String> emailJugadores = new Vector <String>();
		Enumeration<String> lista=stubs2.keys();
		while (lista.hasMoreElements()) {
			String t=lista.nextElement();
			emailJugadores.add(t);
		}
		return emailJugadores;
	}

	
	///Delete player////
	public void delete(String email) throws RemoteException {
		Jugador jugador=this.fachada.getJugador(email);
		this.fachada.delete(jugador);
	}
	
	
	
	///Registar jugador///
	public void register(String email, String passwd) throws RemoteException {
		Jugador jugador = new Jugador(email, passwd);
		try {
			this.fachada.register(jugador);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	////////???????????????///////////////
	public Hashtable<String, Jugador> getJugadores() throws RemoteException {
		return this.fachada.getJugadores();
	}

	//////////??????????/////////////////
	public Hashtable<Integer, Tablero9x9> getTableros() throws RemoteException {
		return this.fachada.getTableros();
	}

	
	public void poner(int idPartida, String email, int cT, int fT, int cC, int fC) throws RemoteException {
		this.fachada.poner(idPartida, email, cT, fT, cC, fC);
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, UnknownHostException {
		Server s=Server.get();
		s.conectar();
	}

	@Override
	public Vector<String> getClientesEnEspera() throws RemoteException {
		Vector<String> result=new Vector<String>();
		for (Tablero9x9 tablero : this.fachada.getTablerosLibres()) {
			result.add(tablero.getJugadorA().getEmail());
		}
		return result;
	}

	@Override
	public void retar(String retador, String retado) throws RemoteException {
		this.fachada.retar(retador, retado);
		
	}

	@Override
	public void respuestaAPeticionDeReto(String retador, String retado,
			boolean respuesta) throws RemoteException {
		this.fachada.respuestaAPeticionDeReto(retador, retado, respuesta);
		
	}


}
