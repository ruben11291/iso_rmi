package esi.uclm.iso.ultimate_tttoe.comunicaciones;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import server.exportable.communications.IServer;
import esi.uclm.iso.ultimate_tttoe.comunicaciones.exportable.ICliente;
import esi.uclm.iso.ultimate_tttoe.dominio.Tablero9x9;
import esi.uclm.iso.ultimate_tttoe.excepciones.CoordenadasNovalidasException;
import esi.uclm.iso.ultimate_tttoe.excepciones.JugadorNoExisteException;
import esi.uclm.iso.ultimate_tttoe.excepciones.NoEstaJugandoException;
import esi.uclm.iso.ultimate_tttoe.excepciones.NoTienesElTurnoException;

public class Cliente implements ICliente{
	
	private String ip;
	private int puerto;
	private String email;
	private IServer servidor;
	private Tablero9x9 juego;
	
	public Cliente(String email) throws RemoteException{
		ip = "localhost";
		puerto = 1099;              // Puerto por defecto para rmi
		this.email = email;
		
		boolean conectado= false;
		while (!conectado) {
			try {
				LocateRegistry.createRegistry(this.puerto);
				//Naming.bind("rmi://" + this.ip + ":" + this.puerto + "/ServicioAjedrez", this);
				Naming.bind("rmi://" + this.ip + ":" + this.puerto + "/terd", this);
				conectado=true;
			}
			catch (AlreadyBoundException eABE) {
				this.puerto+=1;
			}
			catch (MalformedURLException e) {}
			catch (RemoteException e) {
				this.puerto+=1;
			}
		}
		mostrarUnMensaje("Escuchando en el puerto " + this.puerto);
		
	}
	
	public void setServer(String url) throws MalformedURLException, RemoteException, NotBoundException {
		System.out.println("Recuperando servicio");
		this.servidor=(IServer) Naming.lookup(url);
		System.out.print("Servicio recuperado");
	}
		
	public void conectar() throws RemoteException {
		//this.servidor.add(this.email, (IServer)this);
		this.servidor.add(this.email, this);
		
	}

//	public void solicitarJuego() throws RemoteException {
//		this.servidor.solicitudDeJuego(this.email);
//	}

//	public void unirAPartida(String emailCreadorDePartida) throws RemoteException {
//		this.servidor.unirAPartida(this.email, emailCreadorDePartida);
//	}

//	public void ponerARival(int cT, int fT, int cC, int fC) throws RemoteException, NoTienesElTurnoException, JugadorNoExisteException, NoEstaJugandoException, CoordenadasNoValidasException {
//		this.servidor.poner(this.email, cT,  fT,  cC,  fC);
//	}

//	public Vector<String> getListaDeJugadores() throws RemoteException {
//		return this.servidor.geJugadores();
//	}

	@Override
	public String getEmail() throws RemoteException {
		return email;
	}

	@Override
	public void mostrarUnMensaje(String msg) throws RemoteException {
		System.out.println("CONSOLA DE " + email);
		System.out.println("\t" + msg);
		
	}

	@Override
	public void poner(int cT, int fT, int cC, int fC) throws RemoteException,
			NoTienesElTurnoException, JugadorNoExisteException,
			NoEstaJugandoException, CoordenadasNovalidasException {
		
		juego.colocar(cT, fT, cC, fC);
		
	}


}
