package client.controller;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Hashtable;
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
	
	public FTERD getModelo() {
		return modelo;
	}

	private Controller(){
		try{
			this.modelo = new FTERD();
		}
		catch (RemoteException | NotBoundException e) {
			this.excepcionRemota();
		}
	}
	
	public static Controller get() {
		if (self == null)
			self = new Controller();
		return self;
	}
		
	
	public void ponerMovimiento(int cT, int fT, int cC, int fC) {
		System.out.println("Comprobar movimiento válido");
		try {
			this.modelo.poner(this.modelo.getEmailJugador(), cT, fT, cC, fC);
			this.juego.ponerFicha(this.modelo.getEmailJugador(),cT, fT, cC, fC);
		}catch (NoEstaJugandoException e) {
			System.out.println("El oponente no está jugando.");
			e.printStackTrace();
		} catch (CoordenadasNoValidasException e) {
			System.out.println("Coordenadas no válidas.");
			e.printStackTrace();
		}catch (MovimientoNoValidoException e1){
			this.juego.movimientoInvalido(cT, fT, cC, fC);
			System.out.println("Movimiento no válido");
		}catch (NoTienesElTurnoException e2) {
			// TODO: INFORMAR A LA INTERFAZ
			System.out.println("No tienes el turno.");
		}catch (CasillaOcupadaException e3){
			System.out.println("Casilla ocupada.");
		}catch (TableroGanadoException e4) {
			System.out.println("Excepción: " + e4.getEmail() + " " + e4.getcT() + " " + e4.getfT());
			this.juego.tableroGanado(e4.getEmail(), e4.getcT(), e4.getfT());
			this.juego.ponerFicha(this.modelo.getEmailJugador(),cT, fT, cC, fC);
		}catch (PartidaFinalizadaException  e5) {
			this.juego.ponerFicha(this.modelo.getEmailJugador(), cT, fT, cC, fC);
			this.juego.partidaFinalizada(e5.getEmail());
			if(!e5.getEmpate())
				this.juego.tableroGanado(e5.getEmail(), e5.getCol(), e5.getFila());
			else
				this.juego.tableroEmpatado(e5.getCol(),e5.getFila());
		}catch (TableroEmpateException  e6) {
				this.juego.ponerFicha(this.modelo.getEmailJugador(), cT, fT, cC, fC);
				this.juego.tableroEmpatado(e6.getcT(),e6.getfT());
		}catch (RemoteException e) {
			e.printStackTrace();
		} 
	}
	
	// TODO: Capturar las excepciones de partida finalizada  y tablero lleno
	@Override
	public void ponerMovimientoEnemigo(String realizaMov, int cT, int fT, int cC, int fC) {
		this.juego.ponerFicha(realizaMov, cT, fT, cC, fC);
	}
	
	@Override
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
	@Override
	public void enviarDatosRegistro(String email, String passwd) {
		try{
			modelo.registrarJugador(email, passwd);
			registro.respuestaRegistro(false);
		}catch(JugadorYaRegistradoException e){
			registro.respuestaRegistro(true);
		}
	}

	@Override
	public void enviarListaJugadores(Hashtable <String, Integer> jugadores) {
		lista.recibirRespuestaLista(jugadores);
	}
	@Override
	public void retarJugador(String oponente) {
		try {
			modelo.retar(oponente);
		} catch (RemoteException e) {
			this.excepcionRemota();
		}
	}
	@Override
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

	public IJuego getJuego() {
		return juego;
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
		if(this.juego != null) 
			this.juego.cerrar();
		
		this.modelo.cerrarPartida();
		this.juego = null;
		
	}

	@Override 
	public void oponenteHaAbandonado(){
		if(this.juego != null){
			this.juego.cerrarPorAbandonoOponente();
			this.juego = null;
		}
	}
	

	@Override
	public void respuestaReto(String retador, String retado, boolean respuesta) {
		this.lista.recibirRespuestaReto(this.modelo.getEmailJugador(), retador, retado, respuesta);
	}

	@Override
	public void enviarRespuestaReto(boolean respuesta, String retador) {
		try {
			System.out.println("Respuesta enviada a la fachada: " + respuesta + ", " + retador);
			this.modelo.enviarRespuestaReto(respuesta, retador);
		} catch (RemoteException e) {
			this.excepcionRemota();
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
		this.login.avisoCerrarSesion();
	}


	@Override
	public void tableroGanadoPorOponente(String email, int getcT, int getfT) {
		this.juego.tableroGanado(email, getcT, getfT);
	}
	
	@Override
	public void partidaGanadaPorOponente(String email) {
		this.juego.partidaFinalizada(email);

	}

	@Override
	public void partidaFinalizada(String email) {	
		this.modelo.partidaFinalizada();
		if(this.juego != null) {
			this.juego = null;
			this.juego.partidaFinalizada(email);
		}
	}
	@Override
	public void tableroEmpatado(int col, int fila) {
		this.juego.tableroEmpatado(col,fila);
		
	}
	@Override
	public void excepcionRemota() {
		if(this.juego != null){
			juego.excepcionRemota();
			login.mostrar();
	}
		else if(this.lista != null){
			lista.excepcionRemota();
			login.mostrar();
		}
		else if (this.login != null)
			login.excepcionRemota();
		
	}





	
	
}
