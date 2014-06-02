package client.presentation;

import java.util.Hashtable;

public interface IListaJugadores {
	public void recibirRespuestaReto(String self, String retador, String retado, boolean respuesta);
	public void recibirRespuestaLista(Hashtable <String, Integer> jugadores);
	public void recibirReto(String retador);
	public void mostrarMensaje(String mensaje);
	public void iniciarPartida(String self, String retador, String retado);
	public void excepcionRemota();
}
