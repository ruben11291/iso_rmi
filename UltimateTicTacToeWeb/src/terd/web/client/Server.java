package terd.web.client;

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
}
