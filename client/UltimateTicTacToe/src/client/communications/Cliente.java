package client.communications;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import client.domain.FTERD;
import client.exportable.communications.ICliente;

public class Cliente extends UnicastRemoteObject implements ICliente{
	
	private String ip;
	private int puerto;
	private String email;
	private FTERD fachada;

	
	public Cliente(FTERD f) throws RemoteException {
		super();
		
		puerto = 4000;              // Puerto por defecto para rmi
		this.fachada = f;
		boolean conectado= false;
		
		try {
			this.ip = InetAddress.getLocalHost().getHostAddress();
			
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		while (!conectado) {
			try {
				LocateRegistry.createRegistry(this.puerto);
				Naming.bind("rmi://" + this.ip + ":" + this.puerto + "/cliente", this);
				conectado=true;
			}
			catch (AlreadyBoundException eABE) {
				this.puerto+=1;
			}
			catch (MalformedURLException e) {
				e.printStackTrace();
			}
			catch (ExportException e){ 
				this.puerto+=1;
			}catch (RemoteException e) {
				System.err.println(e.toString());
				this.puerto+=1;
			}
		}
	}

	public String getEmail() {
		return this.email;
	}
	
	@Override
	public void poner(int idPartida, String realizaMov, int cT, int fT, int cC, int fC) throws RemoteException {
		this.fachada.recibirMovimientoOponente(realizaMov, cT, fT, cC, fC);
	}

	@Override
	public void OponenteHaAbandonadoPartida() throws RemoteException {
		this.fachada.oponenteHaAbandonado();
	}

	@Override
	public void recibirListaDeJugadores(Hashtable<String, Integer> jugadores) throws RemoteException {
		try {
			this.fachada.updateJugadoresConectados(jugadores);
		} catch(NullPointerException e) {
			System.out.println(e);
		}
	}

	//El cliente ha sido retado
	@Override
	public void notificarSolicitudReto(String retador) throws RemoteException {
		this.fachada.hasSidoRetado(retador);
		
	}
	
	@Override
	public void iniciarPartida(int idPartida,String retador, String retado)
			throws RemoteException {
		this.fachada.iniciarPartida(retador, retado,idPartida);
		
	}

	@Override
	public void respuestaAPeticionDeReto(String retador, String retado,
			boolean respuesta, int idPartida) throws RemoteException {
		this.fachada.respuestaAPeticionDeReto(retador, retado, respuesta,  idPartida);
		
	}
}
