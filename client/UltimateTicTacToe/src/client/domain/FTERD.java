package client.domain;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import client.exceptions.*;
import client.persistence.DAOAutenticar;
import client.exceptions.*;

public class FTERD {
	
	private Hashtable<String, Jugador> jugadores;
	private Hashtable<String, Tablero9x9> tableros;
	
	public FTERD(){
		this.jugadores = new Hashtable<String, Jugador>();
		this.tableros  = new Hashtable<String, Tablero9x9>();
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
		
	public void add(Jugador j){
		if (jugadores == null) {
			this.jugadores = new Hashtable<String, Jugador>();
			
		}
		this.jugadores.put(j.getEmail(), j);
	}
	
	public void remove(Jugador j){
		if (jugadores.contains(j))
			jugadores.remove(j);
	}
	
	public void registrarJugador(String email) throws ClassNotFoundException, SQLException{
		Jugador j = new Jugador(email);
		j.insert();
		
	}
	
	public Jugador autenticar(String email) throws ClassNotFoundException, SQLException{
		Jugador j = null;
		
		if (DAOAutenticar.autenticar(email)){
			j = new Jugador(email);
			this.add(j);
		}
		
		return j;
				
	}
	
	public void CerrarSesion(Jugador j){
		Enumeration<Tablero9x9> elements = tableros.elements();
		boolean logic = true;
		while(elements.hasMoreElements() && logic){
			Tablero9x9 table = elements.nextElement();
			if(table.getJugadorA().getEmail().equals(j.getEmail())) {
				NotificarFinPartida(table.getJugadorB());
			}
			
			else if(table.getJugadorB().getEmail().equals(j.getEmail())){
				NotificarFinPartida(table.getJugadorA());
				
			}
			tableros.remove(table);
			logic = false;
		}
		
		this.remove(j);
	}
	
	private void NotificarFinPartida(Jugador jugador) {
		jugador.acabarPartida();		
	}


	public void poner(String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, JugadorNoExisteException, CoordenadasNoValidasException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException{
		Jugador j = this.jugadores.get(email);
		if (j==null)
			throw new JugadorNoExisteException(email);
		
		j.poner(cT,fT,cC,fC);
	}

	public void solicitudDeJuego(Jugador creador, Jugador oponente) throws RetoNoAceptadoException {
		boolean respuesta = oponente.retar(creador);
		if (respuesta) {
			unirAPartida(oponente, creador);
		} else {
			throw new RetoNoAceptadoException();
		}
	}

	public void unirAPartida(Jugador oponente, Jugador creador) {
		Tablero9x9 tablero = new Tablero9x9();
		tablero.setJugadorConelTurno(creador);
		tablero.setJugadorA(creador);
		tablero.setJugadorB(oponente);
		this.tableros.put(creador.getEmail(), tablero);		
		creador.setTablero(tablero);
		oponente.setTablero(tablero);
		
	}

	public Hashtable<String, Jugador> getJugadores() {
		return jugadores;
	}
	
	public void updateJugadoresConectados(Vector <String> jugadores){
		//pasarselo a ventana
		System.out.println(jugadores);
	}

	
}
