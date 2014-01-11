package client.exportable.communications;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import client.exceptions.*;
import client.exportable.communications.ICliente;
import server.exportable.exceptions.*;
import server.exportable.exceptions.NoEstaJugandoException;
import server.exportable.exceptions.NoTienesElTurnoException;


public interface ICliente extends Remote{
	
	String getEmail() throws RemoteException;
	//idPartida para que el jugador pueda tener varias partidas asignadas a distintas ventanas del juego
	void mostrarUnMensaje(String emailJugador,String msg) throws RemoteException; //??????
	//recibe el movimiento 
	void poner(String email,int cT, int fT, int cC, int fC) throws RemoteException, CoordenadasNoValidasException,  CoordenadasNovalidasException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException, client.exceptions.NoTienesElTurnoException, client.exceptions.NoEstaJugandoException, client.exceptions.JugadorNoExisteException, NoTienesElTurnoException, NoEstaJugandoException;
	boolean solicitudReto(String retador) throws RemoteException;//solicitud de reto
	void salirPartida(String emailJugador)throws RemoteException ; //recibe que el jugador contrario ha salido de la partida 
	Vector<String> getListaDeJugadores() throws RemoteException;//solicitud de lista de jugadores con los que el jugador tiene partidas
	
	//recibe la lista de emails serializadas de los jugadores activos al momento
	void ListaDeJugadores(Vector< String > list) throws RemoteException;
	
}
