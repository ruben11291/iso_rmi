package terd.web.client;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet")
public interface Server extends RemoteService {
	Vector<String> conectar(String name, String passwd) throws IllegalArgumentException;

	Vector<String> retarJugador(String retador, String retado) throws IllegalArgumentException;

	boolean respuestaAPeticionDeReto(String retador, String retado,
			boolean respuesta, int idPartida);

	Vector<String> getRetos(String login_name) throws Exception;

	Vector<String> getListaJugadores() throws Exception;

	Boolean recibirRespuestaReto(String login_name) throws Exception;

	void poner(int idPartida, String email, int cT, int fT, int cC, int fC) throws Exception;

	int getMovimiento(String oponente) throws Exception;

	void abandonarPartida(String login_name) throws Exception;
}
