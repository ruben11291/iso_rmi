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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import server.domain.FTERD;
import server.domain.Jugador;
import server.domain.Tablero9x9;
import server.exportable.communications.IServer;
import client.exceptions.JugadorNoExisteException;
import client.exceptions.JugadorYaExisteException;
import client.exceptions.JugadorYaRegistradoException;
import client.exportable.communications.ICliente;


public class Server extends UnicastRemoteObject implements IServer {
	private String ip;
	private int puerto;
	private FTERD fachada;
	private static Server servo;
	private Hashtable <String, ICliente>stubs;
	private Hashtable<String, Vector<Integer>> movimientosHechos;
	
	protected Server() throws RemoteException, UnknownHostException {
		super();

		this.ip = InetAddress.getLocalHost().getHostAddress();

		this.puerto=3002;
		this.fachada=FTERD.get();
		this.stubs = new Hashtable <String, ICliente>();
		this.movimientosHechos = new Hashtable <String, Vector<Integer>>();
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
			;
		}
		System.out.println("Servidor escuchando" + this.puerto);
	}

	/**** M������todos remotos ****/
	
	/////ADD Player///
	public void add(String email, String passwd, ICliente cliente) throws RemoteException,JugadorNoExisteException , JugadorYaExisteException{
		if(this.stubs.containsKey(email)) throw new JugadorYaExisteException(email);
		Jugador jugador=new Jugador(email);
		this.fachada.add(email, passwd);
		if (cliente != null)
			this.stubs.put(email, cliente);
		System.out.println("Jugador a������adido "+email+" en servidor ");
		this.actualizarListaDeJugadores();
		

	}
	
	public void add(String email, String passwd) throws JugadorNoExisteException {
		this.fachada.add(email, passwd);
		System.out.println("Jugador a������adido "+email+" en servidor ");
		this.actualizarListaDeJugadores();
	}
	
	public Hashtable<String, Integer> getListaJugadores() throws RemoteException {
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
		return listaJugadores;
	}
	
	private void actualizarListaDeJugadores(){
		Enumeration<ICliente> lista=this.stubs.elements();
		while (lista.hasMoreElements()) {
			ICliente t=lista.nextElement();
			try {
				t.recibirListaDeJugadores(getListaJugadores());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

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
		
		//Si cliente1 abandona se elimina la partida y se avisa a cliente2, si tambi������n abandona cliente2 entonces
		//al llegar aqu������ avisarA ser������ vacio
		
		if(!avisarA.isEmpty()){
			//avisar al jugador que su oponente ha cerrado sesion
			Enumeration<String> emailes=avisarA.keys();
			while (emailes.hasMoreElements()) {
				String e = emailes.nextElement();
				ICliente c = this.stubs.get(e);
				if (c != null) {
					try {
						c.OponenteHaAbandonadoPartida();
					} catch (RemoteException e1) {
						System.out.println("Error en comunicacion con cliente :" +e);
						this.stubs.remove(e);//eliminamos de stubs
					}
				} else {
					Vector <Integer> mov = new Vector<Integer>();
					mov.add(-2);
					mov.add(0);
					mov.add(0);
					mov.add(0);
					movimientosHechos.put(email, mov);
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
			e.printStackTrace();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new JugadorYaRegistradoException(email);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Hashtable<String, Jugador> getJugadores() throws RemoteException {
		return this.fachada.getJugadores();
	}

	public Hashtable<Integer, Tablero9x9> getTableros() throws RemoteException {
		return this.fachada.getTableros();
	}

	@Override
	public void poner(int idPartida, String email, int cT, int fT, int cC, int fC) throws RemoteException {
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
		return result;
	}

	@Override
	public void retar(String retador, String retado) throws RemoteException {
		this.fachada= FTERD.get();
		this.fachada.retar(retador, retado);
		ICliente IRetado = this.stubs.get(retado);
		if (IRetado != null)
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
		
		if (IRetador != null) {
			IRetador.respuestaAPeticionDeReto(retador, retado, respuesta, idPartida);
		} else {
			this.fachada.getRetosContestados().put(retador, respuesta);
		}
		
		//Solo si el jugador retado ha aceptado el reto, el servidor le manda el idPartida con el
		//que van a jugar.
		//Si el jugador retado NO ha aceptado el reto no espera ningun mensaje der servidor
		if(respuesta){
			ICliente IRetado = this.stubs.get(retado);
			if (IRetado != null)
				IRetado.iniciarPartida(idPartida, retador, retado);
		}
		this.actualizarListaDeJugadores();
	}

	/* Este metodo se ha creado para las pruebas de la comunicacion*/
	public FTERD getFachada() {
		return this.fachada;
		
	}
	
	@Override
	public void enviarMovimientoAOponente(int idPartida, String oponente, int cT, int fT, int cC, int fC) throws RemoteException {
		System.out.println("Enviar movimiento a oponente: " + oponente);
		ICliente c = this.stubs.get(oponente);
		String realizadoPor = this.fachada.getTableros().get(idPartida).getOpenenteDE(oponente).getEmail();
		if (c != null) {
			c.poner(idPartida, realizadoPor, cT, fT, cC, fC);
		} else {
			Vector<Integer> mov = new Vector<Integer>();
			mov.add(fT);
			mov.add(cT);
			mov.add(fC);
			mov.add(cC);
			movimientosHechos.put(realizadoPor, mov);
		}
		
	}
	
	@Override
	public Vector<Integer> getMovimientosHechos(String oponente) {
		Vector<Integer> a = new Vector<Integer>();
		if (movimientosHechos.get(oponente) == null){
			a.add(-1);
			a.add(0);
			a.add(0);
			a.add(0);
		}
		else
			a = movimientosHechos.remove(oponente);
		return a;
//		int movimiento;
//		if (movimientosHechos.get(oponente) == null)
//			movimiento = -1;
//		else
//			movimiento = movimientosHechos.remove(oponente);
//		return movimiento;
	}

	@Override
	public void partidaFinalizada(int idPartida) throws RemoteException {
		this.fachada.eliminarPartidaFinalizada(idPartida);
	}
	
	@Override
	public Hashtable<String, String> getRetosEnEspera() throws RemoteException {
		return this.fachada.getRetosEnEspera();
	}
	
	@Override
	public Boolean getRespuestaReto(String retador) throws RemoteException {
		Boolean respuesta;
		respuesta = this.fachada.getRetosContestados().get(retador);
		this.fachada.getRetosContestados().remove(retador);
		return respuesta;
	}
}
