package terd.web;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class RegisterAndLogin {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://127.0.0.1:8888/");
		selenium.start();
		FuncionesAuxiliaresTests.borrarBD();
	}

	@Test
	public void testRegisterAndLogin() throws Exception {
		selenium.open("/UltimateTicTacToeWeb.html");
		selenium.type("css=#emailRegistro > input.gwt-TextBox", "user2");
		selenium.type("css=#passwordRegistro > input.gwt-PasswordTextBox", "user2");
		selenium.type("css=#repPasswordRegistro > input.gwt-PasswordTextBox", "user2");
		selenium.click("css=#registerButton > button.myButton");
		//assertEquals("Usuario registrado", selenium.getAlert());
		selenium.type("css=input.gwt-TextBox", "user2");
		selenium.type("css=input.gwt-PasswordTextBox", "user");
		selenium.click("css=#sendButtonContainer > button.myButton");
		assertTrue(selenium.isElementPresent("css=select.lista"));
		assertTrue(selenium.isElementPresent("css=div.labelLista"));
		assertTrue(selenium.isElementPresent("css=button.myButton"));
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
