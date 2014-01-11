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

import client.exportable.communications.ICliente;
import server.domain.FTERD;
import server.domain.Jugador;
import server.domain.Tablero9x9;
import server.exportable.communications.IServer;
import server.exportable.exceptions.CoordenadasNoValidasException;
import server.exportable.exceptions.JugadorNoExisteException;
import server.exportable.exceptions.JugadorYaExisteException;
import server.exportable.exceptions.NoEstaJugandoException;
import server.exportable.exceptions.NoTienesElTurnoException;

public class Server extends UnicastRemoteObject implements IServer {
	private String ip;
	private int puerto;
	private FTERD fachada;
	private Hashtable<String, ICliente> stubs;
	
	public Server() throws RemoteException {
		super();
		this.ip="localhost";
		this.puerto=3001;
		this.fachada=FTERD.get();
		this.stubs = new Hashtable<String, ICliente>();
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

	
	
	
	/**** Métodos remotos 
	 * @throws JugadorYaExisteException ****/
	public void add(String email, ICliente cliente) throws RemoteException, JugadorYaExisteException {
		if(!this.stubs.containsKey(email)){
			this.stubs.put(email, cliente);
			Jugador jugador=new Jugador(cliente, email);
			this.fachada.add(jugador);
			System.out.println("Jugador añadido "+email);
			System.out.println(cliente.getEmail());
			System.out.println("Size "+this.stubs.size());
		}
		else throw new JugadorYaExisteException(email);

	}
	@Override
	public void delete(String email) throws RemoteException {
		if( this.stubs.containsKey(email)){
		Jugador jugador=this.fachada.getJugador(email);
		this.fachada.delete(jugador);
		this.stubs.remove(email);
		}
	}
	
	@Override
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
	
	@Override
	public void solicitudDeJuego(String emailOrigen,String emailDestino) throws RemoteException {
	//	Jugador jugador=this.fachada.getJugador(email);
	//	System.out.println("Solicitud de juego por parte de "+email);
//		this.fachada.solicitudDeJuego(jugador);
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
		Server s= new Server();
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
