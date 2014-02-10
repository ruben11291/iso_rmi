package tests;

import static org.junit.Assert.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import client.communications.Cliente;
import client.controller.Controller;
import client.domain.FTERD;
import client.domain.Jugador;
import client.domain.Tablero9x9;
import client.exceptions.JugadorNoExisteException;
import client.exceptions.JugadorYaExisteException;
import client.exceptions.JugadorYaRegistradoException;
import client.presentation.PlayerListWindow;

public class FTERDTest {

	private Controller control1, control2;

	@Before
	public void setUp(){
		FuncionesAuxiliaresTests.borrarBD();
		this.control1 = Controller.get();
		this.control2 = Controller.get();
		
		try {
			this.control1.getModelo().registrarJugador("nobita", "");
			this.control2.getModelo().registrarJugador("doraemon", "");
		} catch (JugadorYaRegistradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.control1.getModelo().autenticar("nobita", "");
			this.control2.getModelo().autenticar("doraemon", "");
		} catch (JugadorNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JugadorYaExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.control1.setLista(new PlayerListWindow("nobita"));
		this.control1.setLista(new PlayerListWindow("doraemon"));
	}
	
	@Test
	public void testRetarYAceptado() {
		System.out.println("Test retar");
		try {
			this.control1.getModelo().retar("doraemon");
			assertTrue(this.control1.getModelo().getRetosSolicitados().contains("doraemon"));
			this.control2.enviarRespuestaReto(true, "nobita");
			assertFalse(this.control1.getModelo().getRetosSolicitados().contains("doraemon"));
			assertTrue(this.control1.getJuego() != null);
		} catch (RemoteException e) {
			assertFalse(this.control1.getModelo().getRetosSolicitados().contains("doraemon"));
		}
	}
	
	@Test
	public void testRetarYRechazado() {
		System.out.println("Test retar");
		try {
			this.control1.getModelo().retar("doraemon");
			assertTrue(this.control1.getModelo().getRetosSolicitados().contains("doraemon"));
			this.control2.enviarRespuestaReto(false, "nobita");
			assertFalse(this.control1.getModelo().getRetosSolicitados().contains("doraemon"));
			assertTrue(this.control1.getJuego() == null);
		} catch (RemoteException e) {
			assertFalse(this.control1.getModelo().getRetosSolicitados().contains("doraemon"));
		}
	}
}
