package server.exportable.communications;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import server.exportable.exceptions.CoordenadasNoValidasException;
import server.exportable.exceptions.JugadorNoExisteException;
import server.exportable.exceptions.JugadorYaExisteException;
import server.exportable.exceptions.NoEstaJugandoException;
import server.exportable.exceptions.NoTienesElTurnoException;
import server.domain.Jugador;
import server.domain.Tablero9x9;
import client.exportable.communications.*;

public interface IServer extends Remote {
	public void add(String email, ICliente cliente) throws RemoteException, JugadorYaExisteException;
	public void delete(String email) throws RemoteException;
	public void register(String email, String passwd) throws RemoteException;
	public void solicitudDeJuego(String email) throws RemoteException;
	public void unirAPartida(String emailOponente, String emailCreador) throws RemoteException;
//	public Hashtable<String, Jugador> getJugadores() throws RemoteException;
//	public Hashtable<String, Tablero9x9> getTableros() throws RemoteException;
	public void poner(String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, JugadorNoExisteException, NoEstaJugandoException, CoordenadasNoValidasException, RemoteException;
	public Vector<String> getClientesEnEspera() throws RemoteException;
}
