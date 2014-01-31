package client.domain;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import client.communications.Cliente;
import client.communications.Proxy;
import client.controller.Controller;
import client.exceptions.*;
import client.persistence.DAOAutenticar;
import client.exceptions.*;

public class FTERD {
	
	private Hashtable<String, Jugador> jugadores;
	private Jugador jugador;
	private Tablero9x9 tablero;
	private Vector<String> retosSolicitados;
	private Proxy proxy;
	private Cliente cliente;
	
	public FTERD() throws Exception{
		this.jugadores = new Hashtable<String, Jugador>();
		this.proxy = Proxy.get();
		this.cliente = new Cliente(this);
		this.retosSolicitados = new Vector<String>();
	}

	public String getEmailJugador(){
		if(jugador != null) 
			return this.jugador.getEmail();
		else return "";
	}
	
	public void autenticarTest(String email, String passwd){
		this.jugador = new Jugador(email,passwd);
	}
	
	public void registrarJugador(String email, String passwd) throws RemoteException {
		proxy.register(email, passwd);
	}
	
	public void autenticar(String email, String passwd) throws JugadorNoExisteException, JugadorYaExisteException {
		try {
			proxy.add(email, passwd, cliente);
			this.jugador = new Jugador(email,passwd);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void poner(String email,int cT, int fT, int cC, int fC) throws RemoteException, NoTienesElTurnoException, NoEstaJugandoException, CoordenadasNoValidasException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException {
		System.out.println("Poner de la fachada");
		if (email.equals(this.tablero.getJugadorA().getEmail())) 
			this.tablero.getJugadorA().poner(cT, fT, cC, fC);
		else 
			this.tablero.getJugadorB().poner(cT, fT, cC, fC);
		proxy.poner(email, cT, fT, cC, fC,this.tablero.getId());
	}

	public void retar(String oponente) throws RemoteException {
		this.retosSolicitados.add(oponente);
		proxy.retar(this.jugador.getEmail(), oponente);
	}
	
	
	
	public void cerrarSesion() {
		
		try {
			proxy.delete(this.jugador.getEmail());
			this.jugador = null;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateJugadoresConectados(Vector <String> jugadores){
		//pasarselo a ventana
		System.out.println(jugadores);
		try {
			Controller c = Controller.get();
			c.enviarListaJugadores(jugadores);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void recibirMovimientoOponente(int cT, int fT, int cC, int fC) {
		try {
			Controller cntrl = Controller.get();
			cntrl.ponerMovimientoEnemigo(cT, fT, cC, fC);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Metodo que se llama cuando un cliente recibe la respuesta a su solicitud de reto
	public void respuestaAPeticionDeReto(String retador, String retado,
			boolean respuesta, int idPartida) {
		System.out.println("Procede de: proxy servidor. Retador: " + retador + " Retado: " + retado + " Respuesta:" + respuesta);
		System.out.println(this.retosSolicitados.toString());
		if(this.retosSolicitados.contains(retado)){
			if(respuesta){
				this.retosSolicitados.remove(retado);
				creaPartida(retador,retado,idPartida);
			}
			try {
				Controller cntrl = Controller.get();
				cntrl.respuestaReto(retador, retado, respuesta);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//Metodo en el que se crea la partida y se inicializan los jugadores
	private void creaPartida(String retador, String retado,int idPartida){
		Tablero9x9 tablero = new Tablero9x9(idPartida);
		Jugador j1 = new Jugador(retador,"");
		Jugador j2 = new Jugador(retado,"");
		tablero.setJugadorA(j1);
		tablero.setJugadorB(j2);
		j1.setTablero(tablero);
		j2.setTablero(tablero);
		tablero.setJugadorConelTurno(j1);
		this.tablero = tablero;
		j1.setTablero(tablero);
		j2.setTablero(tablero);

	}
	
	//Metodo que se llama cuando se recibe por parte del servidor un inicio de partida.
	//Este cliente es el que recibe la petición de reto
	public void iniciarPartida(String retador, String retado, int idPartida){
		creaPartida(retador, retado, idPartida);
		try {
			Controller cntrl = Controller.get();
			cntrl.iniciarPartida(retador, retado);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Esta operacion hace que el cliente limpie la partida que mantenia con el otro jugador
	//Es recibida por el openente al jugador que abandona
	public void oponenteHaAbandonado() {
		Controller c;
		try {
			c = Controller.get();
			//c.cerrarPartida();
			c.oponenteHaAbandonado();
			this.tablero = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void hasSidoRetado(String retador) {
		// TODO Auto-generated method stub
		try {
			Controller cntrl = Controller.get();
			cntrl.hasSidoRetado(retador);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void enviarRespuestaReto(boolean respuesta, String retador) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Respuesta enviada al proxy");
		this.proxy.envioRespuestaPeticionDeReto(retador, this.jugador.getEmail(), respuesta);
	}

	public void cerrarPartida()  {
		try {
			this.proxy.abandonoPartida(this.jugador.getEmail());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.tablero = null;
	}
	
}
