package terd.web.client;

import java.util.Vector;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet")
public interface Server extends RemoteService {
	Vector<String> conectar(String name, String passwd) throws Exception;

	Vector<String> retarJugador(String retador, String retado) throws IllegalArgumentException;

	Integer respuestaAPeticionDeReto(String retador, String retado, boolean respuesta);

	Vector<String> getRetos(String login_name) throws Exception;

	Vector<String> getListaJugadores() throws Exception;

	Integer recibirRespuestaReto(String login_name) throws Exception;

	void poner(int idPartida, String email, int cT, int fT, int cC, int fC) throws Exception;

	Vector<Integer> getMovimiento(String oponente) throws Exception;

	void abandonarPartida(String login_name) throws Exception;

	void cerrarSesion(String login_name) throws Exception;
	
	void registrar(String email, String pwd) throws Exception;
}
