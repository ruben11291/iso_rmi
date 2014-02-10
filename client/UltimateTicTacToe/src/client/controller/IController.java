package client.controller;
import java.rmi.RemoteException;
import java.util.Hashtable;
import client.exceptions.*;
import client.presentation.*;

public interface IController {
	public void ponerMovimiento(int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, JugadorNoExisteException, TableroLlenoException, MovimientoNoValidoException, PartidaFinalizadaException, CoordenadasNoValidasException;
	public void enviarDatosLogin(String email, String passwd);
	public void enviarDatosRegistro(String email, String passwd) throws JugadorYaRegistradoException, RemoteException;
	public void enviarListaJugadores(Hashtable <String, Integer> jugadores);
	public void enviarRespuestaReto(boolean respuesta, String retador);
	public void retarJugador(String oponente);
	public void setRegistro(IRegistro registro);
	public void setLista(IListaJugadores lista);
	public void setLogin(ILogin login);
	public void setJuego(IJuego juego);
	public void hasSidoRetado(String retador);
	public void cerrarPartida();
	public void oponenteHaAbandonado();
	public void avisoCerrarSesion();
	public void respuestaReto(String retador, String retado, boolean respuesta);
	public void iniciarPartida(String retador, String retado);
	public void ponerMovimientoEnemigo(String realizaMov, int cT, int fT, int cC,int fC);
	public void tableroGanadoPorOponente(String email, int getcT, int getfT);
	public void partidaGanadaPorOponente(String email);
	public void partidaFinalizada(String email);
	public void tableroEmpatado(int col, int fila);
	public void excepcionRemota();
}
