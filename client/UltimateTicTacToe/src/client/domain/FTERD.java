package client.domain;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import server.exportable.exceptions.JugadorYaExisteException;
import client.communications.Cliente;
import client.communications.Proxy;
import client.exceptions.*;
import client.persistence.DAOAutenticar;


public class FTERD {
	
	private Hashtable<String, Jugador> jugadores;
	private Hashtable<String, Tablero9x9> tableros;
	private Cliente cliente;
	private Jugador jCliente;
	//private Hashtable< Tablero9x9 , IntefazJuego>; para la asignacion de partidas y ventanas de juego
	
	public FTERD(Cliente cliente){
		this.jugadores = new Hashtable<String, Jugador>(); //almacenara los jugadores con los que se mantiene una partida
		this.tableros  = new Hashtable<String, Tablero9x9>(); //almacena las partidas mantenidas
		this.cliente = cliente;
		this.jCliente = null; //se usara para cuando se reciba la lista de clientes conectados, este no se tenga en cuenta
	}
	
	
	public Hashtable<String, Tablero9x9> getTableros() {
		return this.tableros;
	}

	public void setTableros(Hashtable<String, Tablero9x9> tableros) {
		this.tableros = tableros;
	}

	public void setJugadores(Hashtable<String, Jugador> jugadores) {
		this.jugadores = jugadores;
	}
		
	//Anyade un jugador a la hash de jugadores, con los cual mantiene una partida
	private void add(Jugador j){
		if (jugadores == null) {
			this.jugadores = new Hashtable<String, Jugador>();
			
		}
		this.jugadores.put(j.getEmail(), j);
	}
	
	//Elimina un jugador a la hash de jugadores, con los cual mantiene una partida	
	private void remove(Jugador j){
		if (jugadores.contains(j))
			jugadores.remove(j);
		
	}
	
	
	//Registra al usuario en la base de datos del server
	public void registrarJugador(String email,String passwd) throws Exception{
		Proxy prx = Proxy.get();
		try{
			prx.registry(email,passwd);
		}catch(JugadorYaExisteException e){
			System.out.println("Jugador ya registrado");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//Autentica al jugador cliente 
	public Jugador autenticar(String email,String passwd) throws ClassNotFoundException, SQLException{
	
		if (DAOAutenticar.autenticar(email,passwd)){
			jCliente = new Jugador(email);
			
		}
		
		return this.jCliente;
				
	}
	
	
	//Cierra la sesion de un  jugador con el que mantenia una partida
	public void cerrarSesion(String email){
		Enumeration<Tablero9x9> elements = tableros.elements();
		boolean logic = true;
		while(elements.hasMoreElements() && logic){
			Tablero9x9 table = elements.nextElement();
//			if(table.getJugadorA().getEmail().equals(j)) {
//				NotificarFinPartida(table.getJugadorB());
//			}
//			
//			else if(table.getJugadorB().getEmail().equals(j)){
//				NotificarFinPartida(table.getJugadorA());
//			}
//			tableros.remove(table);
			if(table.getJugadorA().getEmail().equals(email) || table.getJugadorB().equals(email)){
				logic = false;
				//controlInterfaz.cierra(tableros.get(email)); //si el control de las interfaces lo hace la propia fachada y no cada uno de los tableros
				tableros.get(email).delete();
				tableros.remove(jugadores.get(email));
				remove(jugadores.get(email));
				
			}
			}
}


	
	//Funcion que pone el movimiento del jugador en el tablero correspondiente
	public void poner(String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, JugadorNoExisteException, CoordenadasNovalidasException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException{
		//Jugador j = this.jugadores.get(email);
		//if (j==null)
		if (jCliente.getEmail().equals(email))
			this.tableros.get(email).colocar(jCliente, cT, fT, cC, fC);
		
		if ( !this.jugadores.containsKey(email))	
			throw new JugadorNoExisteException();
		else{
			this.tableros.get(email).colocar(this.jugadores.get(email),cT, fT, cC, fC);
		//	j.poner(cT,fT,cC,fC);
		}
	}

//	public void solicitudDeJuego(Jugador creador, Jugador oponente) throws RetoNoAceptadoException {
//		boolean respuesta = oponente.retar(creador);
////		boolean respuesta = 
//		if (respuesta) {
//			unirAPartida(oponente, creador);
//		} else {
//			throw new RetoNoAceptadoException();
//		}
//	}
//
//	public void unirAPartida(Jugador oponente, Jugador creador) {
//		Tablero9x9 tablero = new Tablero9x9(/*Habria que meterle la ifaz grafica del tablero*/);
//		tablero.setJugadorConelTurno(creador);
//		tablero.setJugadorA(creador);
//		tablero.setJugadorB(oponente);
//		this.tableros.put(creador.getEmail(), tablero);		
//		creador.setTablero(tablero);
//		oponente.setTablero(tablero);
//		
//	}
	
	//Funcion usada por el cliente para enviar una peticion de reto a un jugador
	public void solicitudReto(String emailCreador, String emailRetado) throws Exception{
		Proxy prx = Proxy.get();
		prx.retar(emailCreador, emailRetado);
	}
	
	//Funcion llamada por el cliente cuando se recibe una peticion de reto
	public boolean aceptarReto(String emailCreador,String emailRetado){
		//Creo nueva ventana de interfaz de Reto y Juego
		//return NuevaVentanaReto.aceptado();
		
		
		return false;
		
	}

	public Hashtable<String, Jugador> getJugadores() {
		return jugadores;
	}

	//Notifica al servidor que abandona y desconecta
	public void salir() throws Exception {
		Proxy prx = Proxy.get();
		prx.delete(this.jCliente.getEmail());
		this.cliente.desconectar();
	}

	public void mostrarJugadoresActivos(Vector<String> list) {
		//ventanaDondeEstanlosJugadores.update(list);
		
	}


	

	
}
