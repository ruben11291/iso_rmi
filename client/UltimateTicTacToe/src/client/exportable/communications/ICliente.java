package client.exportable.communications;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface ICliente extends Remote{
	
	void poner(int idPartida, String realizaMov, int cT, int fT, int cC, int fC) throws RemoteException;
	void respuestaAPeticionDeReto(String retador, String retado, boolean respuesta,int idPartida) throws RemoteException;
	void recibirListaDeJugadores(Vector <String> jugadores) throws RemoteException;
	void notificarSolicitudReto(String retador) throws RemoteException;
	void iniciarPartida(int idPartida,String retador, String retado) throws RemoteException;
	void OponenteHaAbandonadoPartida() throws RemoteException;
	
}
