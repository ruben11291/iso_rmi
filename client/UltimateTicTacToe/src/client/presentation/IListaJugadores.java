package client.presentation;

import java.util.Vector;

public interface IListaJugadores {
	public void recibirRespuestaReto();
	public void cerrarSesion();
	public void recibirRespuestaLista(Vector<String> jugadores);
}
