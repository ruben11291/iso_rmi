package client.exportable.communications;

import java.rmi.Remote;
import java.rmi.RemoteException;
import client.exceptions.*;

public interface ICliente extends Remote{
	
	String getEmail() throws RemoteException;
	void mostrarUnMensaje(String msg) throws RemoteException;
	void poner(int cT, int fT, int cC, int fC) throws RemoteException, NoTienesElTurnoException, JugadorNoExisteException, NoEstaJugandoException, CoordenadasNoValidasException;

}
