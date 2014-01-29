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

import client.exceptions.JugadorNoExisteException;
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
	public void add(String email, String passwd, ICliente cliente) throws RemoteException,JugadorNoExisteException {
		Jugador jugador=new Jugador((ICliente)cliente, email);
		this.fachada.add(email, passwd);
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

	public void delete(String email) throws RemoteException {
		this.fachada.delete(email);
		Hashtable<String, Integer> avisarA = this.fachada.eliminarPartidasDelJugador(email);
		//avisar a todos los clientes que esten jugando que su oponente ha cerrado sesion
		Enumeration<String> emailes=avisarA.keys();
		while (emailes.hasMoreElements()) {
			String e = emailes.nextElement();
			ICliente c = this.stubs.get(e);
			c.OponenteHaAbandonadoPartida(avisarA.get(e));
		}		
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
		ICliente IRetado = this.stubs.get(retado);
		IRetado.notificarSolicitudReto(retador);
	}

	@Override
	public void respuestaAPeticionDeReto(String retador, String retado,
			boolean respuesta) throws RemoteException {
		
		int idPartida = this.fachada.respuestaAPeticionDeReto(retador, retado, respuesta);
		
		//Mandar a respuesta a retador. Si la respuestas ha sido positiva le manda un idPartida valido
		//Si ha sido negativa le mando un idPartida invalido (= -1)
		ICliente IRetador = this.stubs.get(retador);
		IRetador.respuestaAPeticionDeReto(retador, retado, respuesta, idPartida);
		
		//Solo si el jugador retado ha aceptado el reto, el servidor le manda el idPartida con el
		//que van a jugar.
		//Si el jugador retado NO ha aceptado el reto no espera ningun mensaje der servidor
		if(respuesta){
			ICliente IRetado = this.stubs.get(retado);
			IRetado.iniciarPartida(idPartida, retador, retado);
		}

		
	}
	
	
}
