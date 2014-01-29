package client.presentation;

import java.util.Vector;

public interface IListaJugadores {
	public void recibirRespuestaReto(String retado, boolean respuesta, int id_partida);
	public void cerrarSesion();
	public void recibirRespuestaLista(Vector<String> jugadores);
	public void recibirReto(String retador);
}
