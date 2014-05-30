package terd.web;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class JugadorYaRegistrado {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://127.0.0.1:8888/");
		selenium.start();
		
	}

	@Test
	public void testJugadorYaRegistrado() throws Exception {
		selenium.open("/UltimateTicTacToeWeb.html");
		selenium.type("css=#emailRegistro > input.gwt-TextBox", "ruben");
		selenium.type("css=#passwordRegistro > input.gwt-PasswordTextBox", "ruben");
		selenium.type("css=#repPasswordRegistro > input.gwt-PasswordTextBox", "ruben");
		selenium.click("css=#registerButton > button.myButton");
	//	Thread.sleep(2000);
	//	assertEquals("El jugador ya está registrado", selenium.getAlert());
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
