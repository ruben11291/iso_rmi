package esi.uclm.iso.ultimate_tttoe.comunicaciones;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import server.exportable.communications.IServer;
public class Proxy {
	private static Proxy yo;
	private IServer server;
	  
	private Proxy() throws MalformedURLException, RemoteException, NotBoundException {
		this.server=(IServer) Naming.lookup("rmi://172.19.177.184:3001/servidor");
	}
	
	public static Proxy get() throws Exception {
		if (yo==null)
			yo=new Proxy();
		return yo;
	}
}
