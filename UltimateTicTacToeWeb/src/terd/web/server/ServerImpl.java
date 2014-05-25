package terd.web.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import server.exportable.communications.IServer;
import terd.web.client.Server;
import terd.web.shared.FieldVerifier;
import terd.web.shared.WJugador;
import client.exceptions.JugadorNoExisteException;
import client.exceptions.JugadorYaExisteException;
import client.exportable.communications.ICliente;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ServerImpl extends RemoteServiceServlet implements
		Server {

	private IServer servidorRMI;
	
	public ServerImpl() {
		try {
			this.servidorRMI=(IServer) Naming.lookup("rmi://localhost:3002/servidor");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public Vector<String> retarJugador(String retador, String retado) throws IllegalArgumentException {
		try {
			this.servidorRMI.retar(retador, retado);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
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
			System.out.println("login exito");
		} catch (RemoteException e1) {
			System.out.println("remote exception");
			e1.printStackTrace();
		} catch (JugadorNoExisteException e1) {
			System.out.println("jn exception");
			e1.printStackTrace();
		} catch (JugadorYaExisteException e1) {
			System.out.println("jy exception");
			e1.printStackTrace();
			
		}
		
		WJugador j = new WJugador(jugador);
		this.getThreadLocalRequest().getSession().setAttribute("jugador", j);
		Vector listaJugadores = null;
		try {
			listaJugadores = new Vector(this.servidorRMI.getListaJugadores().keySet());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fin conectar");
		return listaJugadores;
	}

	@Override
	public boolean respuestaAPeticionDeReto(String retador, String retado, boolean respuesta, int idPartida) {
		try {
			this.servidorRMI.respuestaAPeticionDeReto(retador, retado, respuesta);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return respuesta;
	}

	@Override
	public Vector<String> getRetos(String login_name) throws Exception {
		Hashtable<String, String> retos = this.servidorRMI.getRetosEnEspera();
		Vector<String> resultado = new Vector();
		Enumeration<String> e = retos.keys();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			String retado = retos.get(key);
			if (retado.equals(login_name))
				resultado.add(key);
		}
		return resultado;
	}

	@Override
	public Vector<String> getListaJugadores() throws Exception {
		Vector<String> lista = new Vector();
		Hashtable<String, Integer> retos;
		WJugador j = (WJugador) this.getThreadLocalRequest().getSession().getAttribute("jugador");
		String name_self= j.getName();
		System.out.println(name_self);
		retos = this.servidorRMI.getListaJugadores();
		Enumeration<String> e = retos.keys();
		while (e.hasMoreElements()) {
			String names = e.nextElement();
			if (!names.equals(name_self))
				lista.add(names);
		}
		return lista;
	}

	@Override
	public Boolean recibirRespuestaReto(String login_name) throws Exception {
		return this.servidorRMI.getRespuestaReto(login_name);
	}
	
	@Override
	public void poner(int idPartida, String email, int cT, int fT, int cC, int fC) throws Exception {
		this.servidorRMI.poner(idPartida, email, cT, fT, cC, fC);
	}

	@Override
	public Vector<Integer> getMovimiento(String oponente) throws Exception {
		return this.servidorRMI.getMovimientosHechos(oponente);
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

	@Override
	public void abandonarPartida(String login_name) throws Exception {
		this.servidorRMI.abandonoPartida(login_name);
	}

	@Override
	public void cerrarSesion(String login_name) throws Exception {
		this.servidorRMI.delete(login_name);
	}

}
