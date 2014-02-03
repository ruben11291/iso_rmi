package client.domain;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import client.communications.Cliente;
import client.communications.Proxy;
import client.controller.Controller;
import client.exceptions.*;

public class FTERD {
	private Jugador jugador;
	private Tablero9x9 tablero;
	private Vector<String> retosSolicitados;
	private Proxy proxy;
	private Cliente cliente;
	
	public FTERD() throws RemoteException, NotBoundException {

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
	
	public void registrarJugador(String email, String passwd) throws  JugadorYaRegistradoException {
		Controller cntrl;
		try{
			proxy.register(email, passwd);
		}catch(RemoteException e){
			cntrl = Controller.get();
			cntrl.excepcionRemota();
		}
	}
	
	public void autenticar(String email, String passwd) throws JugadorNoExisteException, JugadorYaExisteException {
		Controller cntrl;
		try {
			proxy.add(email, passwd, cliente);
		} catch (RemoteException e) {
			cntrl = Controller.get();
			cntrl.excepcionRemota();
		}
		this.jugador = new Jugador(email,passwd);
		
	}

	public void poner(String email,int cT, int fT, int cC, int fC) throws RemoteException, NoTienesElTurnoException, NoEstaJugandoException, CoordenadasNoValidasException, MovimientoNoValidoException, PartidaFinalizadaException, CasillaOcupadaException, TableroGanadoException, TableroEmpateException {
//		System.out.println("Poner de la fachada");
//		System.out.println(email);
		Controller cntrl=null;
		try{
			if (email.equals(this.tablero.getJugadorA().getEmail())) 
				this.tablero.getJugadorA().poner(cT, fT, cC, fC);
			else 
				this.tablero.getJugadorB().poner(cT, fT, cC, fC);	
			//Si no se produce exception se trata normal
			try {
				proxy.poner(email, cT, fT, cC, fC,this.tablero.getId());
			} catch (RemoteException e) {
				cntrl = Controller.get();
				cntrl.excepcionRemota();
			}
		//Si al realizar el movimiento se produce una de las siguientes excepciones se le envia al oponente
			//el movimiento  y se vuelve a lanzar la excepcion
		} catch(TableroGanadoException | TableroEmpateException e1){
			try {
				proxy.poner(email, cT, fT, cC, fC,this.tablero.getId());
			} catch (RemoteException e) {
				cntrl = Controller.get();
				cntrl.excepcionRemota();
			}
			throw e1;
		} catch(PartidaFinalizadaException e2) {
			try {
				System.out.println("PARTIDA FINALIZADA");
				proxy.poner(email, cT, fT, cC, fC,this.tablero.getId());
				proxy.partidaFinalizada(this.tablero.getId());
			} catch (RemoteException e) {
				cntrl = Controller.get();
				cntrl.excepcionRemota();
			}
			throw e2;
		}
	}

	public void recibirMovimientoOponente(String realizaMov, int cT, int fT, int cC, int fC) {
			Controller cntrl=null;
			cntrl = Controller.get();
			try{
				cntrl.ponerMovimientoEnemigo(realizaMov, cT, fT, cC, fC);
				this.tablero.colocar(cT, fT, cC, fC);
			}catch ( TableroGanadoException e1) {
				cntrl.tableroGanadoPorOponente(e1.getEmail(),e1.getcT(),e1.getfT());
			}catch(PartidaFinalizadaException e2){
				if(!e2.getEmpate()){
					cntrl.tableroGanadoPorOponente(e2.getEmail(),e2.getCol(),e2.getFila());
					cntrl.partidaGanadaPorOponente(realizaMov);
				}
				else
					cntrl.tableroEmpatado(e2.getCol(),e2.getFila());
			}catch (TableroEmpateException e3) {
					cntrl.tableroEmpatado(e3.getcT(), e3.getfT());
			}
	}
	
	public void retar(String oponente) throws RemoteException {
		Controller cntrl = null;
		
		this.retosSolicitados.add(oponente);
		
		try{
			proxy.retar(this.jugador.getEmail(), oponente);
		}catch(RemoteException e){
			this.retosSolicitados.remove(oponente);
			cntrl = Controller.get();
			cntrl.excepcionRemota();
		
		}
	}
	
	
	
	public void cerrarSesion() {
		Controller cntrl = null;
		try {
			proxy.delete(this.jugador.getEmail());
			this.jugador = null;
		} catch (RemoteException e) {
			cntrl = Controller.get();
			cntrl.excepcionRemota();
		}
	}
	public void updateJugadoresConectados(Hashtable <String, Integer> jugadores){
//		System.out.println(jugadores);
		Controller c = Controller.get();
		c.enviarListaJugadores(jugadores);
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
			Controller cntrl = Controller.get();
			cntrl.respuestaReto(retador, retado, respuesta);
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
		Controller cntrl = Controller.get();
		cntrl.iniciarPartida(retador, retado);
	}

	//Esta operacion hace que el cliente limpie la partida que mantenia con el otro jugador
	//Es recibida por el openente al jugador que abandona
	public void oponenteHaAbandonado() {
		Controller c;
		c = Controller.get();
		c.oponenteHaAbandonado();
		this.tablero = null;
	}

	public void hasSidoRetado(String retador) {
		Controller cntrl = Controller.get();
		cntrl.hasSidoRetado(retador);
	}

	public void enviarRespuestaReto(boolean respuesta, String retador) throws RemoteException {
//		System.out.println("Respuesta enviada al proxy");
		Controller cntrl = null;
		try{
			this.proxy.envioRespuestaPeticionDeReto(retador, this.jugador.getEmail(), respuesta);
		}catch(RemoteException e){
			cntrl=Controller.get();
			cntrl.excepcionRemota();
		}
	}

	public void cerrarPartida()  {
		try {
			this.proxy.abandonoPartida(this.jugador.getEmail());
		} catch (RemoteException e) {
			//No se hace nada
			e.printStackTrace();
		}
		this.tablero = null;
	}

	public void partidaFinalizada() {
		this.tablero = null;
	}
	
}
