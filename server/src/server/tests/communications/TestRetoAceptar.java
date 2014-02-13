package server.tests.communications;


import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.exceptions.JugadorNoExisteException;
import client.exceptions.JugadorYaExisteException;
import client.exceptions.JugadorYaRegistradoException;
import server.communications.Server;
import server.tests.FuncionesAuxiliaresTests;

public class TestRetoAceptar {

	static Server s;
	
	@Before
	public void setUp(){
		// Iniciar servidor. Se pone a escuchar en la ip de este host en el puerto 3001
		try {
			FuncionesAuxiliaresTests.borrarBD();
			s = Server.get();
			s.conectar();
		} catch (RemoteException | UnknownHostException  | MalformedURLException e) {
			fail("Esperaba conectar el servidor");
			//e.printStackTrace();
		}
		
		// Preparar reto
		try {
			ClienteParaTest retador = new ClienteParaTest();
			ClienteParaTest retado = new ClienteParaTest();
			s.register("keynes", "keynes");
			s.register("hayek", "hayek");
			
			s.add("keynes", "keynes", retador);
			s.add("hayek", "hayek", retado);

		} catch (RemoteException  e) {
			fail("No esperaba fallar aquI");
			e.printStackTrace();
		} catch(JugadorNoExisteException | JugadorYaExisteException | JugadorYaRegistradoException e){
			fail("No esperaba fallar al registrar y autenticar");
		}
	}
	
	@Test
	public void testRetarYAceptar() {

		try {
			s.retar("keynes", "hayek");
			assertTrue(s.getFachada().getRetosEnEspera().containsKey("keynes"));
			assertTrue(s.getFachada().getRetosEnEspera().containsValue("hayek"));
			assertTrue(s.getFachada().getTableros().isEmpty());
			s.respuestaAPeticionDeReto("keynes", "hayek", true);
			assertTrue(s.getFachada().getRetosEnEspera().isEmpty());
			assertFalse(s.getFachada().getTableros().isEmpty());
		
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	


}
