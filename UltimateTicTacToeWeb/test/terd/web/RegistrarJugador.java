package terd.web;

import com.thoughtworks.selenium.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

public class RegistrarJugador {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		FuncionesAuxiliaresTests.borrarBD();

		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://127.0.0.1:8888/");
		selenium.start();
	}

	@Test
	public void testRegistrarJugador() throws Exception {
		selenium.open("/UltimateTicTacToeWeb.html");
		selenium.type("css=#emailRegistro > input.gwt-TextBox", "macario2");
		selenium.type("css=#passwordRegistro > input.gwt-PasswordTextBox", "macario2");
		selenium.type("css=#repPasswordRegistro > input.gwt-PasswordTextBox", "macario2");
		selenium.click("css=#registerButton > button.myButton");
		Thread.sleep(30000);
		assertEquals("Usuario registrado", selenium.getAlert());
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
		FuncionesAuxiliaresTests.borrarBD();

	}
}
