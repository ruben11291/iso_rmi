package terd.web.client;

import java.util.Hashtable;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ServerAsync {
	void conectar(String jugador, String passwd, AsyncCallback<Vector<String>> callback)
			throws IllegalArgumentException;
}
