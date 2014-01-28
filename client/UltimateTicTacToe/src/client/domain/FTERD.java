package client.domain;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import client.communications.Cliente;
import client.communications.Proxy;
import client.exceptions.*;
import client.persistence.DAOAutenticar;
import client.exceptions.*;

public class FTERD {
	
	private Hashtable<String, Jugador> jugadores;
	private Hashtable<String, Tablero9x9> tableros;
	private Proxy proxy;
	private Cliente cliente;
	
	public FTERD() throws Exception{
		this.jugadores = new Hashtable<String, Jugador>();
		this.tableros  = new Hashtable<String, Tablero9x9>();
		this.proxy = Proxy.get();
		this.cliente = new Cliente();
	}

	public void registrarJugador(String email, String passwd) throws RemoteException {
		proxy.register(email, passwd);
	}
	
	public void autenticar(String email, String passwd) {
		try {
			proxy.add(email, passwd, cliente);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void poner(int id_partida, String email, int cT, int fT, int cC, int fC) throws RemoteException {
		proxy.poner(id_partida, email, cT, fT, cC, fC);
	}

	public void solicitudDeJuego(String creador, String oponente) throws RemoteException {
		proxy.retar(creador, oponente);
	}
	
	public void actualizarListaJugadores(Vector<String> jugadores2) {
		// TODO LISTA DE JUGADORES
		
		
	}
	
	public void cerrarSesion(String email) {
		try {
			proxy.delete(email);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
