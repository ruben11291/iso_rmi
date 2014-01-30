package client.presentation;

import java.util.Vector;

public interface IListaJugadores {
	public void recibirRespuestaReto(String retado, boolean respuesta);
	public void recibirRespuestaLista(Vector<String> jugadores);
	public void recibirReto(String retador);
	public void iniciarPartida();
	public void mostrarMensaje(String mensaje);
}
