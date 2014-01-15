package server.communications;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import server.domain.FTERD;
import server.domain.Jugador;
import server.domain.Tablero9x9;
import server.exportable.communications.IServer;
import server.exportable.exceptions.CoordenadasNoValidasException;
import server.exportable.exceptions.JugadorNoExisteException;
import server.exportable.exceptions.NoEstaJugandoException;
import server.exportable.exceptions.NoTienesElTurnoException;
import esi.uclm.iso.ultimate_tttoe.comunicaciones.exportable.*;

public class Server extends UnicastRemoteObject implements IServer {
	private String ip;
	private int puerto;
	private FTERD fachada;
	private static Server servo;
	
	protected Server() throws RemoteException {
		super();
		this.ip="localhost";
		this.puerto=3001;
		this.fachada=FTERD.get();
	}
	
	public static Server get() throws RemoteException {
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
	public void add(String email, ICliente cliente) throws RemoteException {
		Jugador jugador=new Jugador(cliente, email);
		this.fachada.add(jugador);
	}

	public void delete(String email) throws RemoteException {
		Jugador jugador=this.fachada.getJugador(email);
		this.fachada.delete(jugador);
	}
	
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
	
	public void solicitudDeJuego(String email) throws RemoteException {
		Jugador jugador=this.fachada.getJugador(email);
		this.fachada.solicitudDeJuego(jugador);
	}

	public void unirAPartida(String emailOponente, String emailCreador) throws RemoteException {
		Jugador oponente=this.fachada.getJugador(emailOponente);
		Jugador creador=this.fachada.getJugador(emailCreador);
		this.fachada.unirAPartida(oponente, creador);
	}

	public Hashtable<String, Jugador> getJugadores() throws RemoteException {
		return this.fachada.getJugadores();
	}

	public Hashtable<Integer, Tablero9x9> getTableros() throws RemoteException {
		return this.fachada.getTableros();
	}

	public void poner(String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, JugadorNoExisteException, NoEstaJugandoException, CoordenadasNoValidasException, RemoteException {
		this.fachada.poner(email, cT, fT, cC, fC);
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException {
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
}
