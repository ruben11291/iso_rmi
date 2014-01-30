package server.tests.communications;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import server.communications.Server;
import server.tests.FuncionesAuxiliaresTests;
import client.exceptions.JugadorNoExisteException;
import client.exceptions.JugadorYaExisteException;
import client.exportable.communications.ICliente;

public class TestCerrarSesion{

	Server s;
	
	@Before
	public void setUp(){
		
		// Iniciar servidor. Se pone a escuchar en la ip de este host en el puerto 3001
		try {
			FuncionesAuxiliaresTests.borrarBD();
			s = Server.get();
			s.conectar();
		} catch (RemoteException | UnknownHostException  | MalformedURLException e) {
			fail("Esperaba conectar el servidor");
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testCerrarSesion() {
		ClienteParaTest cliente1;
		try {
			cliente1 = new ClienteParaTest();
			
			s.register("camilo", "camilo");
			s.add("camilo", "camilo", cliente1);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (JugadorNoExisteException | JugadorYaExisteException e) {
			fail("No esperaba esta excepcion");
			e.printStackTrace();
		}
		
		try {
			s.delete("camilo");
			assertTrue(s.getJugadores().isEmpty());
		} catch (RemoteException e) {
			e.printStackTrace();
		}


	}

}
