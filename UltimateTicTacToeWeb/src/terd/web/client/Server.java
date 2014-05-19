package terd.web.client;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface Server extends RemoteService {
	Vector<String> conectar(String name, String passwd) throws IllegalArgumentException;

	Vector<String> retarJugador(String retador, String retado) throws IllegalArgumentException;

	boolean respuestaAPeticionDeReto(String retador, String retado,
			boolean respuesta, int idPartida);

	Vector<String> getRetos(String login_name) throws Exception;

	Vector<String> getListaJugadores() throws Exception;
}
