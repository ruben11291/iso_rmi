package terd.web.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import server.exportable.communications.IServer;
import terd.web.client.Server;
import terd.web.shared.FieldVerifier;
import terd.web.shared.WJugador;
import client.exceptions.JugadorNoExisteException;
import client.exceptions.JugadorYaExisteException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ServerImpl extends RemoteServiceServlet implements
		Server {

	private IServer servidorRMI;
	
	public ServerImpl() {
		try {
			this.servidorRMI=(IServer) Naming.lookup("rmi://localhost:3002/servidor");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vector<String> conectar(String jugador, String passwd) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(jugador)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		try {
			this.servidorRMI.add(jugador, passwd, null);
			System.out.println("login éxito");
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JugadorNoExisteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JugadorYaExisteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		WJugador j = new WJugador(jugador);
		this.getThreadLocalRequest().getSession().setAttribute("jugador", j);
		Vector listaJugadores = null;
		try {
			listaJugadores = new Vector(this.servidorRMI.getListaJugadores().keySet());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("fin conectar");
		return listaJugadores;
//		String serverInfo = getServletContext().getServerInfo();
//		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

//		// Escape data from the client to avoid cross-site script vulnerabilities.
//		jugador = escapeHtml(jugador);
//		userAgent = escapeHtml(userAgent);
//
//		return "Hello, " + jugador + "!<br><br>I am running " + serverInfo
//				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
