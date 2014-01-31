package client.controller;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import client.domain.FTERD;
import client.exceptions.*;
import client.presentation.*;

public class Controller implements IController {
	private static Controller self;
	private IJuego juego;
	IListaJugadores lista;
	ILogin login;
	IRegistro registro;
	FTERD modelo;
	
	private Controller() throws Exception {
		this.modelo = new FTERD();
	}
	
	public static Controller get() throws Exception {
		if (self == null)
			self = new Controller();
		return self;
	}
		
	
	public void comprobarMovimientoValido(int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, JugadorNoExisteException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException, CoordenadasNoValidasException {
		System.out.println("Comprobar movimiento válido");
		try {
			this.modelo.poner(this.modelo.getEmailJugador(), cT, fT, cC, fC);
			this.juego.ponerFicha(this.modelo.getEmailJugador(),cT, fT, cC, fC);
		}catch (MovimientoNoValidoException e1){
			System.out.println("MOVIMIENTO NO VÁLIDO");
		}catch (NoTienesElTurnoException e2) {
			// TODO: handle exception
			System.out.println("NO TIENEES EL TULNO");
		}catch (CasillaOcupadaException e3){
			//finalizamos la partida hacia el otro clietne: implementar operacion en icliente, la partida se ha acabado
			System.out.println("CASILLA OCUPADA ILLO");

		}catch (PartidaFinalizadaException  e4) {
			//avisamos al otro cliente que hemoss ganado la partida
			System.out.println("END OF GAME");

		}catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ponerMovimientoEnemigo(String realizaMov, int cT, int fT, int cC, int fC) {
		System.out.println("Esto está mal: " + this.modelo.getEmailJugador());
		juego.ponerFicha(realizaMov, cT, fT, cC, fC);
	}
	
	public void enviarDatosLogin(String email, String passwd) {
		boolean error = true;
		try{
			modelo.autenticar(email, passwd);
			error = false;
			login.recibirRespuestaLogin(error);
		}
		catch(JugadorNoExisteException e){
			//crear ventana de error
			System.out.println("JUGADOR "+e.toString()+" NO EXISTE EXCEPTION");
			this.login.jugadorNoExiste();
		}
		catch (JugadorYaExisteException e2) {
			System.out.println("JUGADOR "+e2.toString()+" YA EXISTE EN EL SERVER");
			this.login.jugadorYaExiste();
		}
		
	}
	
	public void enviarDatosRegistro(String email, String passwd) {
		boolean error = true;
		try {
			modelo.registrarJugador(email, passwd);
			error = false;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		registro.respuestaRegistro(error);
	}

	@Override
	public void enviarListaJugadores(Vector<String> jugadores) {
		lista.recibirRespuestaLista(jugadores);
	}
	
	public void retarJugador(String oponente) {
		try {
			modelo.retar(oponente);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setRegistro(IRegistro registro) {
		this.registro = registro;
	}

	@Override
	public void setLista(IListaJugadores lista) {
		this.lista = lista;
		
	}

	@Override
	public void setJuego(IJuego juego) {
		this.juego =juego;
	}

	@Override
	public void setLogin(ILogin login) {
		this.login = login;
	}

	public void cerrarSesion() {
		if (this.juego !=null) {
			this.cerrarPartida();
		}
		this.modelo.cerrarSesion();
		
	}

	//Cierra ventana de juego
	@Override
	public void cerrarPartida() {
		if(juego != null) {
			this.juego.cerrar();
			this.modelo.cerrarPartida();
		}
		this.juego = null;
		
	}
	////////////////////////////////////////////////
	@Override 
	public void oponenteHaAbandonado(){
		if(juego !=null){
			this.juego.cerrarPorAbandonoOponente();
			this.juego = null;
		}
	}
	
	@Override
	public void cerrarPartida(Window window) {
		this.modelo.cerrarPartida();
	}

	@Override
	public void respuestaReto(String retador, String retado, boolean respuesta) {
		// TODO Auto-generated method stub
		this.lista.recibirRespuestaReto(this.modelo.getEmailJugador(), retador, retado, respuesta);
	}

	@Override
	public void enviarRespuestaReto(boolean respuesta, String retador) {
		try {
			System.out.println("Respuesta enviada a la fachada: " + respuesta + ", " + retador);
			this.modelo.enviarRespuestaReto(respuesta, retador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void hasSidoRetado(String retador) {
		this.lista.recibirReto(retador);
	}
	
	@Override
	public void iniciarPartida(String retador, String retado) {
		this.lista.iniciarPartida(this.modelo.getEmailJugador(), retador, retado);
	}

	@Override
	public void avisoCerrarSesion() {
		// TODO Auto-generated method stub
		this.login.avisoCerrarSesion();
	}





	
	
}
