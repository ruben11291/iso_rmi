package esi.uclm.iso.ultimate_tttoe.comunicaciones.exportable;

import java.rmi.Remote;
import java.rmi.RemoteException;

import esi.uclm.iso.ultimate_tttoe.excepciones.CoordenadasNovalidasException;
import esi.uclm.iso.ultimate_tttoe.excepciones.JugadorNoExisteException;
import esi.uclm.iso.ultimate_tttoe.excepciones.NoEstaJugandoException;
import esi.uclm.iso.ultimate_tttoe.excepciones.NoTienesElTurnoException;

public interface ICliente extends Remote{
	
	String getEmail() throws RemoteException;
	void mostrarUnMensaje(String msg) throws RemoteException;
	void poner(int cT, int fT, int cC, int fC) throws RemoteException, NoTienesElTurnoException, JugadorNoExisteException, NoEstaJugandoException, CoordenadasNovalidasException;

}
