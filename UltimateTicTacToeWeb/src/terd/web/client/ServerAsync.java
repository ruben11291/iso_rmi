package terd.web.client;

import java.util.Hashtable;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServerAsync {
	void conectar(String jugador, String passwd, AsyncCallback<Vector<String>> callback)
			throws IllegalArgumentException;

	void retarJugador(String retador, String retado,
			AsyncCallback<Vector<String>> asyncCallback);
	
	void respuestaAPeticionDeReto(String retador, String retado,
			boolean respuesta, int idPartida, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	void getRetos(String login_name, AsyncCallback<Vector<String>> asyncCallback);

	void getListaJugadores(AsyncCallback<Vector<String>> asyncCallback);

	void recibirRespuestaReto(String login_name,
			AsyncCallback<Boolean> asyncCallback);

	void poner(int idPartida, String email, int cT, int fT, int cC, int fC,
			AsyncCallback<Void> callback);

	void getMovimiento(String oponente, AsyncCallback<Vector<Integer>> asyncCallback);

	void abandonarPartida(String login_name, AsyncCallback asyncCallback);

	void cerrarSesion(String login_name, AsyncCallback asyncCallback);
}
