package server.exportable.communications;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import client.exportable.communications.ICliente;
import server.domain.Jugador;
import server.domain.Tablero9x9;

public interface IServer extends Remote {
	public void add(String email, String passwd, ICliente cliente) throws RemoteException;
	public void delete(String email) throws RemoteException;
	public void register(String email, String passwd) throws RemoteException;
	
	public void retar(String retador, String retado) throws RemoteException;
	public void respuestaAPeticionDeReto(String retador, String retado, boolean respuesta) throws RemoteException;
	public void poner(int idPartida, String email, int cT, int fT, int cC, int fC)throws RemoteException;
	public Vector<String> getClientesEnEspera() throws RemoteException;
}
