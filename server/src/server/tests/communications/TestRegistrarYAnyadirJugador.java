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
import client.exceptions.JugadorYaRegistradoException;
import client.exportable.communications.ICliente;

public class TestRegistrarYAnyadirJugador {

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
	public void testRegistrarYAnyadirJugador() {
		try {
			s.register("camilo", "camilo");
		} catch (RemoteException | JugadorYaRegistradoException e) {
			fail("No esperaba RemoteException al registrar jugador");
			e.printStackTrace();
		}
		
		try {
			ClienteParaTest cliente1 = new ClienteParaTest();
			s.add("camilo", "camilo", cliente1);
			assertTrue(s.getJugadores().containsKey("camilo"));
		} catch (JugadorNoExisteException	| JugadorYaExisteException e) {
			fail("No esperaba un error con el jugador "+ e.toString());
			e.printStackTrace();
		} catch (RemoteException e){
			fail("No esperaba fallar aqui" + e.toString());
		}
	}

}
