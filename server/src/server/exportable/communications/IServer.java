package server.exportable.communications;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import client.exceptions.JugadorNoExisteException;
import client.exceptions.JugadorYaExisteException;
import client.exceptions.JugadorYaRegistradoException;
import client.exportable.communications.ICliente;

public interface IServer extends Remote {
	public void add(String email, String passwd, ICliente cliente) throws RemoteException, JugadorNoExisteException, JugadorYaExisteException;
	public void delete(String email) throws RemoteException;
	public void register(String email, String passwd) throws RemoteException, JugadorYaRegistradoException;
	public void retar(String retador, String retado) throws RemoteException;
	public void respuestaAPeticionDeReto(String retador, String retado, boolean respuesta) throws RemoteException;
	public void poner(int idPartida, String email, int cT, int fT, int cC, int fC)throws RemoteException;
	public void abandonoPartida(String email) throws RemoteException;
	public Vector<String> getClientesEnEspera() throws RemoteException;
	public void enviarMovimientoAOponente(int idPartida, String oponente, int cT, int fT, int cC, int fC) throws RemoteException;
	public void partidaFinalizada(int idPartida) throws RemoteException;
	public Hashtable<String, Integer> getListaJugadores() throws RemoteException;
	public Hashtable<String, String> getRetosEnEspera() throws RemoteException;
}

