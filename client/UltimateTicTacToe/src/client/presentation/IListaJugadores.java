package client.presentation;

import java.util.Vector;

public interface IListaJugadores {
	public void recibirRespuestaReto(String self, String retador, String retado, boolean respuesta);
	public void recibirRespuestaLista(Vector<String> jugadores);
	public void recibirReto(String retador);
	public void mostrarMensaje(String mensaje);
	public void iniciarPartida(String self, String retador, String retado);
	public void excepcionRemota();
}
