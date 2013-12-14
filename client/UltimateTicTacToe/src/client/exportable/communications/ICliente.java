package client.exportable.communications;

import java.rmi.Remote;
import java.rmi.RemoteException;

import server.exportable.exceptions.CoordenadasNoValidasException;
import server.exportable.exceptions.JugadorNoExisteException;
import server.exportable.exceptions.NoEstaJugandoException;
import server.exportable.exceptions.NoTienesElTurnoException;


public interface ICliente extends Remote{
	
	String getEmail() throws RemoteException;
	void mostrarUnMensaje(String msg) throws RemoteException;
	void poner(int cT, int fT, int cC, int fC) throws RemoteException, NoTienesElTurnoException, JugadorNoExisteException, NoEstaJugandoException, CoordenadasNoValidasException;

}
