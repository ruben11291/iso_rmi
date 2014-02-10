package server.communications;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import client.exceptions.JugadorNoExisteException;
import client.exceptions.JugadorYaExisteException;
import client.exceptions.JugadorYaRegistradoException;
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
	private Registry r;
	
	protected Server() throws RemoteException, UnknownHostException {
		super();
//		this.ip="localhost";
		this.ip = InetAddress.getLocalHost().getHostAddress();
		//System.out.println(ip);
		this.puerto=3002;
		this.fachada=FTERD.get();
		this.stubs = new Hashtable <String, ICliente>();
	}
	
	public static Server get() throws RemoteException, UnknownHostException {
		if (servo==null)
			servo=new Server();
		return servo;
	}

	public void conectar() throws RemoteException, MalformedURLException {
		r = LocateRegistry.createRegistry(this.puerto);
		//Registry r = LocateRegistry.getRegistry(this.puerto);
		try {
			Naming.bind("rmi://" + this.ip + ":" + this.puerto + "/servidor", this);
//			r.bind("rmi://" + this.ip + ":" + this.puerto + "/servidor", this);
		}
		catch (AlreadyBoundException eABE) {
			;
			//Naming.rebind("rmi://" + this.ip + ":" + this.puerto + "/servidor", this);
//			r.rebind("rmi://" + this.ip + ":" + this.puerto + "/servidor", this);
		}
		System.out.println("Servidor escuchando" + this.puerto);
	}

	/**** Métodos remotos ****/
	
	/////ADD Player///
	public void add(String email, String passwd, ICliente cliente) throws RemoteException,JugadorNoExisteException , JugadorYaExisteException{
		if(this.stubs.containsKey(email)) throw new JugadorYaExisteException(email);
		Jugador jugador=new Jugador(email);
		this.fachada.add(email, passwd);
		this.stubs.put(email, cliente);
		System.out.println("Jugador añadido "+email+" en servidor ");
		this.actualizarListaDeJugadores();
		

	}
	
	public void add(String email, String passwd) throws JugadorNoExisteException {
//		if(this.stubs.containsKey(email)) throw new JugadorYaExisteException(email);
//		Jugador jugador=new Jugador(email);
		this.fachada.add(email, passwd);
//		this.stubs.put(email, cliente);
		System.out.println("Jugador añadido "+email+" en servidor ");
		this.actualizarListaDeJugadores();
	}
	
	private void actualizarListaDeJugadores(){
		Vector <String> jugadores = this.fachada.getEmailsJugadores();
		Hashtable <String, Integer> listaJugadores = new Hashtable<String, Integer>();
		for (String jugador : jugadores) {
			listaJugadores.put(jugador, 0);
		}
		Enumeration <Tablero9x9> tableros = this.fachada.getTableros().elements();
		System.out.println(jugadores.toString());
		while (tableros.hasMoreElements()){
			Tablero9x9 tablero = tableros.nextElement();
			if (listaJugadores.containsKey(tablero.getJugadorA().getEmail()))
				listaJugadores.put(tablero.getJugadorA().getEmail(), 1);
			if (listaJugadores.containsKey(tablero.getJugadorB().getEmail()))
				listaJugadores.put(tablero.getJugadorB().getEmail(), 1);			
		}
		
//		Set entrySet = listaJugadores.entrySet();
//		// Obtain an Iterator for the entries Set
//		Iterator it = entrySet.iterator();
//		// Iterate through Hashtable entries
//		System.out.println("Lista de jugadores: ");
//		while(it.hasNext())
//			System.out.println(it.next());
		
		Enumeration<ICliente> lista=this.stubs.elements();
		while (lista.hasMoreElements()) {
			ICliente t=lista.nextElement();
			try {
				t.recibirListaDeJugadores(listaJugadores);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

//	private Vector<String> getEmailJugadores(Hashtable<String, ICliente> stubs2) {
//		Vector <String> emailJugadores = new Vector <String>();
//		Enumeration<String> lista=stubs2.keys();
//		while (lista.hasMoreElements()) {
//			String t=lista.nextElement();
//			emailJugadores.add(t);
//		}
//		return emailJugadores;
//	}

	// Para cerrar sesion
	public void delete(String email) throws RemoteException {
		System.out.println("Abandona "+ email);
		this.fachada.delete(email);
		this.stubs.remove(email);
		this.abandonoPartida(email);
		this.actualizarListaDeJugadores();
	}
	
	// El jugador con email 'email' ha abandonado la partida. Se busca a su oponente y 
	// se comunica que 'email' ha abandonado la partida
	@Override
	public void abandonoPartida(String email) {
		Hashtable<String, Integer> avisarA = this.fachada.eliminarPartidaDelJugador(email);
		//Si cliente1 abandona se elimina la partida y se avisa a cliente2, si también abandona cliente2 entonces
		//al llegar aquí avisarA será vacio
		if(!avisarA.isEmpty()){
			//avisar al jugador que su oponente ha cerrado sesion
			Enumeration<String> emailes=avisarA.keys();
			while (emailes.hasMoreElements()) {
				String e = emailes.nextElement();
				ICliente c = this.stubs.get(e);
				try {
					c.OponenteHaAbandonadoPartida();
				} catch (RemoteException e1) {
					System.out.println("Error en comunicacion con cliente :" +e);
					this.stubs.remove(e);//eliminamos de stubs
				}
	
			}
		}
		this.actualizarListaDeJugadores();
	}	
	
	///Registar jugador///
	public void register(String email, String passwd) throws RemoteException, JugadorYaRegistradoException {
		Jugador jugador = new Jugador(email, passwd);
		try {
			this.fachada.register(jugador);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new JugadorYaRegistradoException(email);
		}
		catch (SQLException e) {
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

	@Override
	public void poner(int idPartida, String email, int cT, int fT, int cC, int fC) throws RemoteException {
		System.out.println("Llega movimiento");
		this.fachada = FTERD.get();
		Jugador oponente =  this.fachada.poner(idPartida, email, cT, fT, cC, fC);
		this.enviarMovimientoAOponente(idPartida, oponente.getEmail(), cT, fT, cC, fC);
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, UnknownHostException {
		Server s=Server.get();
		s.conectar();
	}

	@Override
	public Vector<String> getClientesEnEspera() throws RemoteException {
		Vector<String> result=new Vector<String>();
//		for (Tablero9x9 tablero : this.fachada.getTablerosLibres()) {
//			result.add(tablero.getJugadorA().getEmail());
//		}
		return result;
	}

	@Override
	public void retar(String retador, String retado) throws RemoteException {
		this.fachada= FTERD.get();
		this.fachada.retar(retador, retado);
		ICliente IRetado = this.stubs.get(retado);
		IRetado.notificarSolicitudReto(retador);
	}

	@Override
	public void respuestaAPeticionDeReto(String retador, String retado,
			boolean respuesta) throws RemoteException {
		this.fachada = FTERD.get();
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
		this.actualizarListaDeJugadores();
	}

	/* Este metodo se ha creado para las pruebas de la comunicacion*/
	public FTERD getFachada() {
		return this.fachada;
		
	}

//	/* Este metodo se ha creado para las pruebas de la comunicacion*/
//	public void desconectar() throws RemoteException, MalformedURLException, NotBoundException {
////		Naming.unbind();
////		UnicastRemoteObject.unexportObject(r, true);
//		Registry r = LocateRegistry.getRegistry(this.ip, this.puerto);
////		r.unbind("rmi://" + this.ip + ":" + this.puerto + "/servidor");
//		Naming.unbind("rmi://" + this.ip + ":" + this.puerto + "/servidor");
////		UnicastRemoteObject.unexportObject();
//	}

	@Override
	public void enviarMovimientoAOponente(int idPartida, String oponente, int cT, int fT, int cC, int fC) throws RemoteException {
		System.out.println("Enviar movimiento a oponente: " + oponente);
		ICliente c = this.stubs.get(oponente);
		String realizadoPor = this.fachada.getTableros().get(idPartida).getOpenenteDE(oponente).getEmail();
		c.poner(idPartida, realizadoPor, cT, fT, cC, fC);
		
	}

	@Override
	public void partidaFinalizada(int idPartida) throws RemoteException {
		this.fachada.eliminarPartidaFinalizada(idPartida);
	}


	
}
