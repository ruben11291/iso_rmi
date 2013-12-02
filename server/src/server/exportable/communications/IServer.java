package server.exportable.communications;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import server.exportable.exceptions.CoordenadasNoValidasException;
import server.exportable.exceptions.JugadorNoExisteException;
import server.exportable.exceptions.NoEstaJugandoException;
import server.exportable.exceptions.NoTienesElTurnoException;
import server.domain.Jugador;
import server.domain.Tablero9x9;
import esi.uclm.iso.ultimate_tttoe.comunicaciones.exportable.*;

public interface IServer extends Remote {
	public void add(String email, ICliente cliente) throws RemoteException;
	public void delete(Jugador jugador) throws RemoteException;
	public void register(String email, String passwd) throws RemoteException;
	public void solicitudDeJuego(String email) throws RemoteException;
	public void unirAPartida(String emailOponente, String emailCreador) throws RemoteException;
	public Hashtable<String, Jugador> getJugadores() throws RemoteException;
	public Hashtable<String, Tablero9x9> getTableros() throws RemoteException;
	public void poner(String email, int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, JugadorNoExisteException, NoEstaJugandoException, CoordenadasNoValidasException, RemoteException;
	public Vector<String> getClientesEnEspera() throws RemoteException;
}
