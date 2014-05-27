package client.communications;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import server.exportable.communications.IServer;
import client.exceptions.JugadorNoExisteException;
import client.exceptions.JugadorYaExisteException;
import client.exceptions.JugadorYaRegistradoException;
import client.exportable.communications.ICliente;

public class Proxy {
	private static Proxy yo;
	private IServer server;
	  
	private Proxy() throws RemoteException, NotBoundException {
		try{
			this.server=(IServer) Naming.lookup("rmi://172.19.213.178:3002/servidor");
		}catch(MalformedURLException e){
			e.printStackTrace();  //No se va a producir
		}

	}
	
	public static Proxy get() throws RemoteException , NotBoundException {
		if (yo==null)
			yo=new Proxy();
		return yo;
	}
	
	//////////// COMUNICACION CON EL SERVIDOR//////////////////
	
	// Registrar en el sistema
	public void register(String email, String passwd) throws RemoteException,JugadorYaRegistradoException {
		server.register(email, passwd);
	}
	
	// Darse de alta en el sistema
	public void add(String email, String passwd, ICliente cliente) throws RemoteException, JugadorNoExisteException, JugadorYaExisteException{
		server.add(email, passwd, cliente);
	}
	
	// Darse de baja del sistema
	public void delete(String email) throws RemoteException {
		server.delete(email);
	}
	
	// Retar a un jugador
	public void retar(String emailRetador, String emailRetado) throws RemoteException {
		server.retar(emailRetador, emailRetado);
	}
	
	// Realizar movimiento
	public void poner(String email, int cT, int fT, int cC, int fC, int idPartida) throws RemoteException {
		server.poner(idPartida,email, cT, fT, cC, fC);
	}	
	
	// Enviar respuesta a una peticion de reto
	public void envioRespuestaPeticionDeReto(String retador, String retado, boolean respuesta) throws RemoteException{
		server.respuestaAPeticionDeReto(retador, retado, respuesta);
	}
	
	// Abandonar una partida
	public void abandonoPartida(String email) throws RemoteException {
		server.abandonoPartida(email);
	}
	
	// Fin de partida
	public void partidaFinalizada(int id) throws RemoteException {
		server.partidaFinalizada(id);
	}
}
