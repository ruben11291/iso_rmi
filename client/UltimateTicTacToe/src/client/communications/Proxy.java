package client.communications;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

import client.exportable.communications.ICliente;
import server.exportable.communications.IServer;
import server.exportable.exceptions.*;
public class Proxy {
	private static Proxy yo;
	private IServer server;
	  
	private Proxy() throws MalformedURLException, RemoteException, NotBoundException {
		this.server=(IServer) Naming.lookup("rmi://localhost:3001/servidor");
	}
	
	public static Proxy get() throws Exception {
		if (yo==null)
			yo=new Proxy();
		return yo;
	}
	
	
	//////////// COMUNICACION CON EL SERVIDOR//////////////////
	//registrar en el sistema
	public void registry(String email, String passwd) throws RemoteException, JugadorYaExisteException {
		// TODO Auto-generated method stub
		server.register(email, passwd);
	}
	
	//darse de alta en el sistema
	public void add (String email, ICliente cliente) throws RemoteException, JugadorYaExisteException{
		server.add(email, cliente);
	}
	
	//darse de baja del sistema
	public void delete(String email) throws RemoteException {
		// TODO Auto-generated method stub
		server.delete(email);
	}
	
	//retar a un jugador
	public void retar(String emailRetador, String emailRetado)  throws RemoteException{
		server.solicitudDeJuego(emailRetador, emailRetado);
	}
	
	//realizar movimiento
	public void poner(String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, JugadorNoExisteException, NoEstaJugandoException, CoordenadasNoValidasException, RemoteException {
		server.poner(email, cT, fT, cC, fC);
	}
//////////////////////////////////////////////

	
	
	///////////////FUNCIONES USADAS POR EL CLIENTE///////////////
	
	//Conecta al cliente rmi 
	public int conectaCliente(String ip, int puerto, String email,ICliente cliente) {
		boolean conectado= false;
		while (!conectado) {
			try {
				
				Registry l = LocateRegistry.createRegistry(puerto); 
				Naming.bind("rmi://" + ip + ":" +puerto + "/"+email, cliente);
			//	ICliente a =(ICliente) Naming.lookup("rmi://localhost:"+puerto+"/"+email);
			//	System.out.println(a.toString());
				conectado=true;
			}
			catch (AlreadyBoundException eABE) {
				puerto+=1;
			}
			catch (MalformedURLException e) {}
			catch (ExportException e) {
				puerto+=1;
			}
			catch (RemoteException e) {
				System.err.println(e.toString());
				puerto+=1;
			}
			catch (Exception e) {
				System.out.println("ERROR EX");
			}
		}
		return puerto;
	}
	
	//Desconecta al cliente de rmi
	public boolean desconectaCliente(String host, int port, String name) {
		try{
			//System.out.println("rmi://" + host + ":" +port + "/"+name);
			Naming.unbind("rmi://" + host + ":" +port + "/"+name);
		}catch(NotBoundException e){
			return false;
		}catch(AccessException e){
			return false;
		}catch(RemoteException e ){
			return false;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	
	
	
	
	///////////////////////////////////////////////////////////
	
}
