package server.tests.communications;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.communications.Server;
import server.tests.FuncionesAuxiliaresTests;
import client.exceptions.JugadorNoExisteException;
import client.exceptions.JugadorYaExisteException;
import client.exceptions.JugadorYaRegistradoException;

public class TestRetoRechazar {
	
	Server s;
	@Before
	public void setUp(){
		// Iniciar servidor. Se pone a escuchar en la ip de este host en el puerto 3001
		try {
			FuncionesAuxiliaresTests.borrarBD();
			s = Server.get();
			s.conectar();
		} catch (RemoteException | UnknownHostException  | MalformedURLException e) {
			;
			//fail("Esperaba conectar el servidor");
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

		} catch (RemoteException | JugadorNoExisteException | JugadorYaExisteException |JugadorYaRegistradoException e) {
			fail("No esperaba fallar aquI");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRetarYRechazar() {

		System.out.print("rechazar");
		// retar y aceptar reto
		try {
			s.retar("keynes", "hayek");
			assertTrue(s.getFachada().getRetosEnEspera().containsKey("keynes"));
			assertTrue(s.getFachada().getRetosEnEspera().containsValue("hayek"));
			assertTrue(s.getFachada().getTableros().isEmpty());
			s.respuestaAPeticionDeReto("keynes", "hayek", false);
			assertTrue(s.getFachada().getRetosEnEspera().isEmpty());
			assertTrue(s.getFachada().getTableros().isEmpty());
		
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}

}
