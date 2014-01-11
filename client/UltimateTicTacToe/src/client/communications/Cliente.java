package client.communications;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Vector;

import client.domain.FTERD;
import client.domain.Jugador;
import client.exceptions.*;
import client.exportable.communications.ICliente;
import server.exportable.communications.IServer;
import server.exportable.exceptions.*;
import server.exportable.exceptions.JugadorNoExisteException;
import server.exportable.exceptions.NoEstaJugandoException;
import server.exportable.exceptions.NoTienesElTurnoException;
//import esi.uclm.iso.ultimate_tttoe.dominio.Tablero9x9;
public class Cliente extends UnicastRemoteObject implements ICliente{
	
	
	/**
	 * 
	 */
	private String ip;
	private int puerto;
	private String email;
	private IServer servidor;
	private FTERD fachada;
	
	public Cliente(String email) throws Exception {
		super();
		ip = "localhost";
		puerto = 4000;              // Puerto por defecto para rmi
		this.email = email;
		this.servidor = null;
		this.fachada = new FTERD(this);
	}
	

	public void conectar() throws RemoteException, JugadorYaExisteException {
	
		System.out.println("INIT CONECTAR");
		Proxy prx;
		try {
			prx = Proxy.get();
			this.puerto = prx.conectaCliente(ip, puerto, email, this);
			System.out.println("Escuchando en el puerto "+this.puerto );


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(this.servidor != null)
//			this.servidor.add(this.email, this);
//		else System.out.println("Error en servidor NULL");
	}


	public void desconectar() throws ClienteNoDesconectadoException{
		// TODO Auto-generated method stub
		System.out.println("INIT DESCONECTAR");
		Proxy prx;
		try{
			prx = Proxy.get();
			boolean logic = prx.desconectaCliente(ip, puerto, email);
			if(!logic ) throw new ClienteNoDesconectadoException();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//METODOS REMOTOS
	
	@Override
	public Vector<String> getListaDeJugadores() throws RemoteException {
		Enumeration<Jugador> elements = this.fachada.getJugadores().elements();
		Vector <String> emails = new Vector <String> ();
		while(elements.hasMoreElements()){
			Jugador j = elements.nextElement();
			emails.add(j.getEmail());
		}
		return emails;
	}

	@Override
	public String getEmail() throws RemoteException {
		return email;
	}

	@Override
	public void mostrarUnMensaje(String email,String msg) throws RemoteException {
		System.out.println("CONSOLA DE " + email);
		System.out.println("\t" + msg);
		
	}

	@Override
	public void poner(String email,int cT, int fT, int cC, int fC) throws RemoteException,
			NoTienesElTurnoException,
			NoEstaJugandoException, CoordenadasNoValidasException,CoordenadasNovalidasException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException, client.exceptions.NoTienesElTurnoException, client.exceptions.NoEstaJugandoException, client.exceptions.JugadorNoExisteException {
		
		fachada.poner(email, cT, fT, cC, fC);
		//juego.colocar(cT, fT, cC, fC);
		
	}

	@Override
	public boolean solicitudReto(String retador) throws RemoteException {
		
		if(fachada.aceptarReto(retador, this.email))
			return true;
		else return false;
	}

	@Override
	public void salirPartida(String emailJugador) throws RemoteException {
		// TODO Auto-generated method stub
		fachada.cerrarSesion(emailJugador);
		
	}

	@Override
	public void ListaDeJugadores(Vector<String> list) throws RemoteException {
		fachada.mostrarJugadoresActivos(list);
		
	}



}
