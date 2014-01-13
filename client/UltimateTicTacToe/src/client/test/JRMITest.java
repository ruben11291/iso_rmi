package client.test;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import server.exportable.exceptions.JugadorYaExisteException;
import client.communications.Cliente;
import client.exceptions.ClienteNoDesconectadoException;

public class JRMITest {

	@Test
	public void ConexionTest() {
		Cliente c;
		try{
			c = new Cliente("emailprueba@gmail.com");
			assertNotNull(c);
			c.conectar();
			try{
				c.desconectar();
			}catch(ClienteNoDesconectadoException e){
				fail("Cliente no desconectado");
			}
		}catch(RemoteException e){
			fail("Excepcion remota");
		} catch (JugadorYaExisteException e) {
			fail("Jugador ya existe");
		} catch (Exception e) {
			fail("Excepcion ocurrida");
		}
		
		
	}

}
